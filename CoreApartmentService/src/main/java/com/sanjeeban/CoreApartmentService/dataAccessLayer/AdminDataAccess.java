package com.sanjeeban.CoreApartmentService.dataAccessLayer;

import com.netflix.discovery.converters.Auto;
import com.sanjeeban.CoreApartmentService.dto.ResidentDetailsDto;
import com.sanjeeban.CoreApartmentService.dto.ResidentInfoDto;
import com.sanjeeban.CoreApartmentService.exceptions.CustomGenericException;
import com.sanjeeban.CoreApartmentService.exceptions.CustomInvalidCredentialsException;
import com.sanjeeban.CoreApartmentService.repository.TUserInformationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.awt.print.Pageable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class AdminDataAccess {

    @Autowired
    private DataSource dataSource;

    @Autowired
    TUserInformationRepository tUserInformationRepository;

    public void validateUniqueId(String uniqueId) {
        if(uniqueId==null || uniqueId.isBlank() || uniqueId.equals("string") || uniqueId.equals("null")) throw new CustomInvalidCredentialsException("Invalid Unique Id");

        final String sql = "select count(*) from apt_core.t_user_information t where t.unique_id = ?";

        int count = 0;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,uniqueId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(count!=1){
            throw new CustomInvalidCredentialsException("Unique Id '"+uniqueId+"' is not valid");
        }
    }


    public void validateIfResident(String uniqueId) {

        final String sql = "select t.flag from apt_core.t_user_information t where t.unique_id = ?";
        String flag = "N";

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,uniqueId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    flag = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(!flag.equals("R")) throw new CustomInvalidCredentialsException("User is not registered as a Resident.");
    }

    public void validateApartmentNumber(String apartmentNumber) {

        if(apartmentNumber==null || apartmentNumber.isBlank() || apartmentNumber.equals("null") || apartmentNumber.equals("string")) {
            throw  new CustomInvalidCredentialsException("Apartment Number is not valid. Please enter a valid Apartment Number.");
        }

        final String sql = "select t.availability from apt_core.t_apartment t where t.apartment_number = ?";
        String isApartmentAvailable = "N";

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,apartmentNumber);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    isApartmentAvailable = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(isApartmentAvailable==null) throw new CustomInvalidCredentialsException("Apartment Number "+String.valueOf(apartmentNumber)+" is invalid.");
        if(isApartmentAvailable.equals("N")) throw new CustomInvalidCredentialsException("Apartment Number "+String.valueOf(apartmentNumber)+" is not available.");

    }

    public void updateResidentProfile(String uniqueId, String apartmentNumber) {

        // get the userid from unique id.

        Long userId = getUserIdFromUniqueId(uniqueId);
        Long apartmentId = getApartmentIdFromApartmentNumber(apartmentNumber);

        // update the t_resident profile table.
        final String sql = """
                update apt_core.t_resident_profile
                set apartment_id = ?, status = 'Associated'
                where user_id = ?;
                                
                """;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1,apartmentId);
            ps.setLong(2,userId);

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new CustomGenericException("Unable to update resident data in resident profile. Please contact Administrator");
        }
    }

    private Long getApartmentIdFromApartmentNumber(String apartmentNumber) {
        Long aptId = 0L;

        final String sql = "select t.apartment_id from apt_core.t_apartment t where t.apartment_number = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,apartmentNumber);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    aptId = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return aptId;
    }

    private Long getUserIdFromUniqueId(String uniqueId) {

        Long userId = 0L;

        final String sql = "select t.user_id from apt_core.t_user_information t where t.unique_id = ?";


        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,uniqueId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    userId = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userId;
    }
    public ResidentInfoDto getAllResidentDetails(PageRequest pageRequest) {
        ResidentInfoDto response = new ResidentInfoDto();

        try{
            Page<ResidentDetailsDto> details = tUserInformationRepository.fetchAllResidents(pageRequest);
            List<ResidentDetailsDto> residentDetails = details.getContent();
            response.setResidentDetailsList(residentDetails);
            int totalPages = details.getTotalPages();
            response.setTotalPages(totalPages);
            response.setResponseCode("200");
            response.setResponseMessage("Data Fetched.");
        }catch (Exception e){
            e.getCause();
        }
        return response;
    }
}
