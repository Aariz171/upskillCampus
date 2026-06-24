package com.hrms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[+]?[0-9]{7,15}$");
    private static final Pattern EMP_ID_PATTERN =
            Pattern.compile("^[A-Za-z0-9]{3,10}$");

    public static void validateEmployeeId(String id) throws HRMSException {
        if (isNullOrBlank(id)) throw new HRMSException("Employee ID cannot be empty.");
        if (!EMP_ID_PATTERN.matcher(id).matches())
            throw new HRMSException("Employee ID must be 3-10 alphanumeric characters.");
    }

    public static void validateName(String name) throws HRMSException {
        if (isNullOrBlank(name)) throw new HRMSException("Name cannot be empty.");
        if (name.length() < 2 || name.length() > 50)
            throw new HRMSException("Name must be 2-50 characters.");
    }

    public static void validateEmail(String email) throws HRMSException {
        if (isNullOrBlank(email)) throw new HRMSException("Email cannot be empty.");
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new HRMSException("Invalid email format (e.g. user@example.com).");
    }

    public static void validatePhone(String phone) throws HRMSException {
        if (isNullOrBlank(phone)) throw new HRMSException("Phone cannot be empty.");
        if (!PHONE_PATTERN.matcher(phone).matches())
            throw new HRMSException("Invalid phone number (7-15 digits).");
    }

    public static void validateDate(String date) throws HRMSException {
        if (isNullOrBlank(date)) throw new HRMSException("Date cannot be empty.");
        try { LocalDate.parse(date); }
        catch (DateTimeParseException e) {
            throw new HRMSException("Invalid date '" + date + "'. Use YYYY-MM-DD format.");
        }
    }

    public static void validateDateRange(String start, String end) throws HRMSException {
        validateDate(start);
        validateDate(end);
        if (LocalDate.parse(end).isBefore(LocalDate.parse(start)))
            throw new HRMSException("End date cannot be before start date.");
    }

    public static void validateNotEmpty(String value, String fieldName) throws HRMSException {
        if (isNullOrBlank(value)) throw new HRMSException(fieldName + " cannot be empty.");
    }

    public static boolean isNullOrBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
