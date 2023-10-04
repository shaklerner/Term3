package com.example.travelexpertsadministrator;

public class ValidationUtils {

    // Check if a string is empty or null
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static boolean exceedsMaxLength(String str, int maxLength) {
        return str.length() > maxLength;
    }
    // Basic email validation
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

}
