package com.greenfoxacademy.baloghdominik.mysql.services;

import com.greenfoxacademy.baloghdominik.mysql.models.UserModels;
import com.greenfoxacademy.baloghdominik.mysql.repositories.UserModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
public class Validation {
    private String validation;
    private UserModelsRepository userModelsRepository;

    public Validation(UserModelsRepository userModelsRepository) {
        this.userModelsRepository = userModelsRepository;
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

    public String getUserIdCookie(HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public String getUserValidationCookie(HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userValidation")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public boolean isLoggedIn(HttpServletRequest response) throws NoSuchAlgorithmException {
        boolean isLogged = false;
        if (!getUserIdCookie(response).equals("") && !getUserValidationCookie(response).equals("")) {
            String username = userModelsRepository.findOneById(Long.parseLong(getUserIdCookie(response))).getUsername();
            if (getUserValidationCookie(response).equals(toMD5(userModelsRepository.findByUsername(username).getPassword()))){
                isLogged = true;
            }
        } else {
            isLogged = false;
        }
        return isLogged;
    }

    public String getLoggedInUsername(HttpServletRequest response) throws NoSuchAlgorithmException {
        String username = "username";
        if (isLoggedIn(response)) {
            username = userModelsRepository.findOneById(Long.parseLong(getUserIdCookie(response))).getUsername();
        }
        return username;
    }

    public String toMD5(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes(),0, text.length());
        String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);
        if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
        }
        return  hashedPass;
    }

    public String getValidation() {
        return validation;
    }
}
