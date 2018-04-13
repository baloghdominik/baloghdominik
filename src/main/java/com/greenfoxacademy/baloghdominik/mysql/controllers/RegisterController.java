package com.greenfoxacademy.baloghdominik.mysql.controllers;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.models.UserModels;
import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import com.greenfoxacademy.baloghdominik.mysql.repositories.UserModelsRepository;
import com.greenfoxacademy.baloghdominik.mysql.services.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserModelsRepository userModelsRepository;
    private Validation validation;

    @Autowired
    public RegisterController(Validation validation, UserModelsRepository userModelsRepository) {
        this.userModelsRepository = userModelsRepository;
        this.validation = validation;
    }

    @GetMapping(value={"", "/"})
    public String register() {
        return "register";
    }

    @PostMapping(value = "/newuser")
    public String add(@ModelAttribute(value="username") String username, @ModelAttribute(value = "password") String password,
                      @ModelAttribute(value = "passwordConfirmation") String passwordConfirmation, HttpServletResponse response) throws NoSuchAlgorithmException {

        if(validation.checkRegister(username, password, passwordConfirmation)) {
            Cookie cookie = new Cookie("userValidation", validation.toMD5(userModelsRepository.findByUsername(username).getPassword()));
            cookie.setPath("/");
            cookie.setMaxAge(100000);
            response.addCookie(cookie);

            Cookie cookieID = new Cookie("userID", userModelsRepository.findByUsername(username).getId().toString());
            cookieID.setPath("/");
            cookieID.setMaxAge(100000);
            response.addCookie(cookieID);

            return  "redirect:../todo";
        }
        return  "redirect:../login";
    }
}
