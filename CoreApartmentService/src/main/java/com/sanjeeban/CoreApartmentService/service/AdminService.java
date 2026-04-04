package com.sanjeeban.CoreApartmentService.service;


import com.sanjeeban.CoreApartmentService.annotations.AuditLog;
import com.sanjeeban.CoreApartmentService.dataAccessLayer.AdminDataAccess;
import com.sanjeeban.CoreApartmentService.dto.*;
import com.sanjeeban.CoreApartmentService.entity.TResidentProfile;
import com.sanjeeban.CoreApartmentService.entity.TUserInformation;
import com.sanjeeban.CoreApartmentService.exceptions.CustomGenericException;
import com.sanjeeban.CoreApartmentService.exceptions.CustomInvalidCredentialsException;
import com.sanjeeban.CoreApartmentService.repository.TResidentProfileRepository;
import com.sanjeeban.CoreApartmentService.repository.TUserInformationRepository;
import com.sanjeeban.CoreApartmentService.util.CommonValidationUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Value("${kafka.topic.pdftopic}")
    private String KAFKA_PDF_TOPIC;

    private final JdbcTemplate jdbcTemplate;
    private final TUserInformationRepository tUserInformationRepository;
    private final ModelMapper modelMapper;
    private final TResidentProfileRepository tResidentProfileRepository;
    private final DataSource dataSource;
    private final AdminDataAccess adminDataAccess;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate,
                        TUserInformationRepository tUserInformationRepository,
                        ModelMapper modelMapper,
                        TResidentProfileRepository tResidentProfileRepository,
                        DataSource dataSource,
                        AdminDataAccess adminDataAccess,
                        KafkaTemplate<String, String> kafkaTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.tUserInformationRepository = tUserInformationRepository;
        this.modelMapper = modelMapper;
        this.tResidentProfileRepository = tResidentProfileRepository;
        this.dataSource = dataSource;
        this.adminDataAccess = adminDataAccess;
        this.kafkaTemplate = kafkaTemplate;
    }

    @AuditLog(action = "CREATE_USER")
    public CreateUserResponse createNewUser(CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        
        validateRequest(request);

        TUserInformation tUserInformation =  modelMapper.map(request, TUserInformation.class);
        Long userId = generateUserId();
        tUserInformation.setUserId(userId);

        // generate uniqueId.
        String uniqueId = generateCode(request);
        tUserInformation.setUniqueid(uniqueId);

        String str = saveUser(tUserInformation);
        if(!str.equals("200")){
            response.setUserStatus("Pending");
            response.setUniqueId(uniqueId);
            response.setMailStatus("Pending");
        }

        response.setUserStatus("User Created");

        if(request.getFlag().equals("R")){
            // save in tresident profile.
            TResidentProfile tResidentProfile = new TResidentProfile();
            Long residentId = generateUserId();
            tResidentProfile.setResidentId(residentId);
            tResidentProfile.setUserId(userId);
            tResidentProfile.setAptId(null);
            tResidentProfile.setStatus("Not Associated");
            String residentProfileStatus = saveResident(tResidentProfile);
            if(!residentProfileStatus.equals("200")){
                response.setUserStatus("User Created but Resident update failed.");
            }
        }
        response.setUniqueId(uniqueId);
        response.setMailStatus("N/A");

        return response;
    }

    private String saveResident(TResidentProfile tResidentProfile) {
        String response = "200";
        try{
            tResidentProfileRepository.save(tResidentProfile);
        }catch(Exception e){
            response = "Save Unsuccessful for Resident Profile for cause"+e.getCause();
        }
        return response;
    }

    private String saveUser(TUserInformation tUserInformation){
        String response = "200";
        try{
            tUserInformationRepository.saveAndFlush(tUserInformation);
        }catch (Exception e){
            response = "Save Unsuccessful for User Information for cause "+e.getCause();
        }
        return response;
    }

    private void validateRequest(CreateUserRequest request) {
        CommonValidationUtils.validateEmail(request.getEmail());
        //CommonValidationUtils.validateAadharNumber(request.getAadharno());
        //CommonValidationUtils.validatePanNumber(request.getPanno());
        CommonValidationUtils.validateMobileNumber(request.getMobile());
        CommonValidationUtils.validateCreateUserFlag(request.getFlag());
    }


    private Long generateUserId() {
        return jdbcTemplate.queryForObject(
                "SELECT generate_user_id()",
                Long.class
        );
    }


    public static String generateCode(CreateUserRequest jsonRequest) {
        // Step 1: Flag mapping
        String flag = jsonRequest.getFlag();
        String prefix;
        switch (flag) {
            case "R": prefix = "20"; break;
            case "A": prefix = "45"; break;
            case "T": prefix = "90"; break;
            default: prefix = "00";
        }

        // Step 2: PAN card extraction
        String pan = jsonRequest.getPanno();
        String panPart = pan.substring(0, 2) + pan.substring(pan.length() - 2);

        // Step 3: Current time in hh:mm
        String timePart = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm"));

        // Final 10-character string
        return prefix + panPart + timePart;
    }


    public RegisterResidentResponse registerNewResident(RegisterResidentRequest request) {
        RegisterResidentResponse response = new RegisterResidentResponse();

        String uniqueId = request.getUniqueId();

        adminDataAccess.validateUniqueId(uniqueId);
        adminDataAccess.validateIfResident(uniqueId);
        adminDataAccess.validateApartmentNumber(request.getApartmentNumber());


        // all validation is correct. Updating the resident profile table.

        adminDataAccess.updateResidentProfile(uniqueId,request.getApartmentNumber());

        // send to kafka for pdf generation.
        kafkaTemplate.send(KAFKA_PDF_TOPIC,String.valueOf(uniqueId));


        response.setResidentStatus("ACTIVE");
        response.setResponseCode("200");
        response.setResponseMessage("Resident activated.");


        return response;
    }

    public ResidentInfoDto fetchResidentDetails(FetchResident request) {

        ResidentInfoDto response = new ResidentInfoDto();

        if(request.getFetchFlag()==null){
            throw new CustomGenericException("Fetch Flag is a mandatory parameter.");
        }

        String strStartRow = request.getStartRow();
        String strEndRow = request.getEndRow();

        validatePaginationData(strStartRow,strEndRow);

        int startRow = 1;
        if(strStartRow!=null && !strStartRow.isBlank()) startRow = Integer.parseInt(strStartRow);


        int endRow = 10;
        if(strEndRow!=null && !strEndRow.isBlank()) endRow = Integer.parseInt(strEndRow);


        int pageSize = (endRow - startRow) + 1;
        int pageNumber = (startRow - 1) / pageSize;


        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);



        String fetchFlag = request.getFetchFlag();
        if(fetchFlag.equals("ALL")){
            response = adminDataAccess.getAllResidentDetails(pageRequest);
        }else{
            //response = adminDataAccess.getResidentByUniqueId();
        }




        return response;
    }

    private void validatePaginationData(String strStartRow, String strEndRow) {

        if(strStartRow==null || strStartRow.isBlank() || strEndRow==null || strEndRow.isBlank()){
            return;
        }

        if (!strStartRow.matches("\\d+")) {
            throw new CustomGenericException("Start row has to be a valid integer number");
        }

        int startRow = Integer.parseInt(strStartRow);

        if (!strEndRow.matches("\\d+")) {
            throw new CustomGenericException("End row has to be a valid integer number");
        }

        int endRow = Integer.parseInt(strEndRow);

        if(startRow<=0) throw new CustomGenericException("Start Row cannot be 0 or less than 0.");

        if(startRow>endRow) throw new CustomGenericException("Start Row cannot be larger than End Row.");

    }

    public ResidentDatasourceResponse residentDataSource(String uniqueId) {

        ResidentDatasourceResponse response = new ResidentDatasourceResponse();

        //UserInformationDtoResponse
        UserInformationDtoResponse userInformationDtoResponse;

        List<TUserInformation> userInfoList = Optional
                .ofNullable(tUserInformationRepository.findAllByUniqueid(uniqueId))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CustomInvalidCredentialsException("Invalid Unique Id"));

        TUserInformation userInfo = userInfoList.get(0);

        userInformationDtoResponse = modelMapper.map(userInfo, UserInformationDtoResponse.class);
        response.setUserInformationDtoResponse(userInformationDtoResponse);


        return response;
    }
}
