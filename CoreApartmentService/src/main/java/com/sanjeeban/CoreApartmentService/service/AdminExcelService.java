package com.sanjeeban.CoreApartmentService.service;

import com.sanjeeban.CoreApartmentService.dto.ApartmentExcelDto;
import com.sanjeeban.CoreApartmentService.dto.ApartmentUploadResponse;
import com.sanjeeban.CoreApartmentService.entity.TApartment;
import com.sanjeeban.CoreApartmentService.exceptions.CustomExcelDataValidationException;
import com.sanjeeban.CoreApartmentService.repository.TApartmentRepository;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminExcelService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    TApartmentRepository tApartmentRepository;

    public ApartmentUploadResponse uploadBulkDataForApartmentTable(MultipartFile file) {

        ApartmentUploadResponse response = new ApartmentUploadResponse();
        List<String> errors = new ArrayList<>();
        List<TApartment> validEntries = new ArrayList<>();

        try{
                        boolean isValidExcel = validExcelCheck(file);
            if(!isValidExcel) throw new CustomExcelDataValidationException("File Upload format is incorrect. Please upload files with .xlsx extension.");

            // Saving the file locally.
            String uploadDirectory = uploadDir;
            File dir = new File(uploadDirectory);
            if(!dir.exists()) dir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDirectory, fileName);
            Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Saving file to: " + path.toAbsolutePath());

            // Parse Excel.
            List<ApartmentExcelDto> rows = parseExcel(file.getInputStream());
            int rowNum = 1;

            for(ApartmentExcelDto row : rows){
                List<String> validationErrors = validateExcelData(row,rowNum);

                if(validationErrors.isEmpty()){
                    validEntries.add(convertToEntity(row));
                }else{
                    errors.addAll(validationErrors);
                }
                rowNum++;
            }
            if(errors.isEmpty()){
                tApartmentRepository.saveAll(validEntries);
                response.setTotalRows(rows.size());
                response.setSuccessRows(validEntries.size());
                response.setFailedRows(errors.size());
                response.setErrors(errors);
                response.setResponseCode("200");
                response.setResponseMessage("Data successfully saved");
            }else{
                response.setResponseCode("400");
                response.setResponseMessage("Entries could not be saved due to inconsistency in one or more data point.");
                response.setTotalRows(rows.size());
                response.setSuccessRows(validEntries.size());
                response.setFailedRows(errors.size());
                response.setErrors(errors);
            }


        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Excel upload failed for reason : "+e.getCause());
        }
        return response;
    }

    private boolean validExcelCheck(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.endsWith(".xlsx")) {
            return false;
        }

        if (!"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                .equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    private TApartment convertToEntity(ApartmentExcelDto row) {
        TApartment entity = new TApartment();
        entity.setAptId(row.getAptId());
        entity.setBlockNo(row.getBlockNo());
        entity.setFlatNo(row.getFlatNo());
        entity.setAvailability(row.getAvailability());
        entity.setCarpetArea(row.getCarpetArea());
        entity.setApartmentNo(row.getApartmentNo());
        entity.setBhkType(row.getBhkType());
        return entity;
    }

    private List<String> validateExcelData(ApartmentExcelDto row, int rowNum) {
        List<String> errors = new ArrayList<>();

        if (row.getCarpetArea() == null || row.getCarpetArea() <= 0) {
            errors.add("Row " + rowNum + " Invalid carpet area");
        }

        if (!"Y".equals(row.getAvailability()) && !"N".equals(row.getAvailability())) {
            errors.add("Row " + rowNum + " Invalid availability");
        }

        if (row.getAptId() == null) {
            errors.add("Row " + rowNum + " AptId missing");
        }

        return errors;
    }

    private List<ApartmentExcelDto> parseExcel(InputStream inputStream) throws IOException {

        List<ApartmentExcelDto> list = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for(Row row  : sheet){
            if(row.getRowNum()==0) continue;

            ApartmentExcelDto dto = new ApartmentExcelDto();

            DataFormatter formatter = new DataFormatter();

            dto.setAptId(Long.valueOf(formatter.formatCellValue(row.getCell(0))));
            dto.setBlockNo(formatter.formatCellValue(row.getCell(1)));
            dto.setFlatNo(formatter.formatCellValue(row.getCell(2)));
            dto.setAvailability(formatter.formatCellValue(row.getCell(3)));
            dto.setCarpetArea(Double.valueOf(formatter.formatCellValue(row.getCell(4))));
            dto.setApartmentNo(formatter.formatCellValue(row.getCell(5)));
            dto.setBhkType(formatter.formatCellValue(row.getCell(6)));

            list.add(dto);
        }
        return list;
    }


}
