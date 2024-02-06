package com.shrineoflostsecrets.channel.util;

import java.security.SecureRandom;

public class OTPGenerator {

    // Define the characters that can be used in the password
    private static final String NUMBERS = "0123456789";
    private static final String UPPER_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*_=+-/.?<>)";

    // Method to generate OTP
    public static String generateOTP(int length, boolean useSpecialChars) {
        // Create a StringBuilder to store the OTP
        StringBuilder password = new StringBuilder(length);
        
        // Create a SecureRandom instance for secure password generation
        SecureRandom random = new SecureRandom();
        
        // All possible characters that can be used
        String chars = NUMBERS + UPPER_ALPHABETS;
        
        // Add special characters if required
        if (useSpecialChars) {
            chars += SPECIAL_CHARACTERS;
        }
        
        // Generate the OTP of given length
        for (int i = 0; i < length; i++) {
            // Select a random character from the string
            int index = random.nextInt(chars.length());
            // Append the character to the password
            password.append(chars.charAt(index));
        }
        
        return password.toString();
    }

    // Main method to test the OTP generator
    public static void main(String[] args) {
        // Generate an 8-character OTP without special characters
        String otp1 = generateOTP(8, false);
        System.out.println("OTP without special characters: " + otp1);
        
        // Generate an 8-character OTP with special characters
        String otp2 = generateOTP(8, true);
        System.out.println("OTP with special characters: " + otp2);
    }
}