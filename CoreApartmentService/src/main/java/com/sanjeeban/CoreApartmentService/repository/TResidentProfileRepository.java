package com.sanjeeban.CoreApartmentService.repository;

import com.sanjeeban.CoreApartmentService.entity.TResidentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TResidentProfileRepository extends JpaRepository<TResidentProfile,Long> {

}
