package com.sanjeeban.CoreApartmentService.repository;

import com.sanjeeban.CoreApartmentService.dto.ResidentDetailsDto;
import com.sanjeeban.CoreApartmentService.entity.TUserInformation;
import jakarta.persistence.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TUserInformationRepository extends JpaRepository<TUserInformation,Long> {

    @Query("""
        SELECT new com.sanjeeban.CoreApartmentService.dto.ResidentDetailsDto(
            tu.name,
            tu.email,
            tu.dob,
            tu.aadharno,
            tu.panno,
            tu.mobile,
            tu.username,
            tu.flag,
            tu.uniqueid,
            ta.apartmentNo
        )
        FROM TUserInformation tu
        JOIN TResidentProfile rf ON tu.userId = rf.userId
        JOIN TApartment ta ON rf.aptId = ta.aptId
    """)
    Page<ResidentDetailsDto> fetchAllResidents(Pageable pageable);


    @Query("SELECT t FROM TUserInformation t WHERE t.uniqueid = :uniqueid")
    List<TUserInformation> findAllByUniqueid(@Param("uniqueid") String uniqueid);
}





