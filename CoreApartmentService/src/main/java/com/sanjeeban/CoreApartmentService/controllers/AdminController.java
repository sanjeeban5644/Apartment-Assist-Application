package com.sanjeeban.CoreApartmentService.controllers;


import com.sanjeeban.CoreApartmentService.dto.*;

import com.sanjeeban.CoreApartmentService.service.AdminExcelService;
import com.sanjeeban.CoreApartmentService.service.AdminService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/1.0/admin")
@RestController
public class AdminController {

    private AdminService adminService;
    private AdminExcelService adminExcelService;

    @Autowired
    public AdminController(AdminService adminService,  AdminExcelService adminExcelService){
        this.adminService = adminService;
        this.adminExcelService = adminExcelService;
    }



    @PostMapping(value = "/createUser",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserResponse> createNewUser(@RequestBody CreateUserRequest request){
        CreateUserResponse response = new CreateUserResponse();
        response = adminService.createNewUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/uploadBulkApartmentData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApartmentUploadResponse> uploadBulkApartmentData(@RequestParam("file")  MultipartFile file){
        ApartmentUploadResponse response = new ApartmentUploadResponse();
        response = adminExcelService.uploadBulkDataForApartmentTable(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/registerResident", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResidentResponse> registerResident(@RequestBody RegisterResidentRequest request){
        RegisterResidentResponse response = new RegisterResidentResponse();
        response = adminService.registerNewResident(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value="/fetchResidents", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResidentInfoDto> fetchAllResidents(@RequestBody FetchResident request){
        ResidentInfoDto response = new ResidentInfoDto();
        response = adminService.fetchResidentDetails(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value="/residentDatasource/{uniqueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResidentDatasourceResponse> getResidentDatasource(@PathVariable String uniqueId){
        ResidentDatasourceResponse response = new ResidentDatasourceResponse();
        response = adminService.residentDataSource(uniqueId);
        return ResponseEntity.ok(response);
    }




}
