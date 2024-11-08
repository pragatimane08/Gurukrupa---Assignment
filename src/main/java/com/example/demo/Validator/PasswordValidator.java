package com.example.demo.Validator;

import java.util.regex.Pattern;

public class PasswordValidator {
    // Define a regex pattern for password validation
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_PATTERN, password);
    }
}

