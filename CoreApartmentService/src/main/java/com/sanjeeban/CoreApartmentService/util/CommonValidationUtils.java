package com.sanjeeban.CoreApartmentService.util;

import com.sanjeeban.CoreApartmentService.exceptions.CustomInvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CommonValidationUtils {



    private CommonValidationUtils(){}

    private static final Pattern AADHAR_PATTERN = Pattern.compile("^[2-9]{1}[0-9]{11}$");
    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]{1}[0-9]{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static void validateAadharNumber(String aadharNo) {

        if (aadharNo == null || !AADHAR_PATTERN.matcher(aadharNo).matches()) {
            throw new CustomInvalidCredentialsException(CommonConstants.INVALID_AADHAR);
        }
    }

    public static void validatePanNumber(String panNo) {

        if (panNo == null || !PAN_PATTERN.matcher(panNo).matches()) {
            throw new CustomInvalidCredentialsException(CommonConstants.INVALID_PAN);
        }
    }

    public static void validateMobileNumber(String mobileNo) {

        if (mobileNo == null || !MOBILE_PATTERN.matcher(mobileNo).matches()) {
            throw new CustomInvalidCredentialsException(CommonConstants.INVALID_MOBILE);
        }
    }

    public static void validateEmail(String email) {

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new CustomInvalidCredentialsException(CommonConstants.INVALID_EMAIL);
        }
    }

    public static void validateCreateUserFlag(String flag) {

        Set<String> validValues = new HashSet<>();
        validValues.add("R");
        validValues.add("A");
        validValues.add("T");

        if(!validValues.contains(flag)){
            throw new CustomInvalidCredentialsException("Flag value is not correct.");
        }

    }



}