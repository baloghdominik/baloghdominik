package com.greenfoxacademy.baloghdominik.mysql.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Validation {
    private String validation;

    public Validation() {

    }

    public void generateValidationCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 50) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        validation = saltStr;
    }

    public String getValidation() {
        return validation;
    }
}
