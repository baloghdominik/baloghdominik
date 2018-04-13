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
    public String register(Model model) {

        return "register";
    }

    @PostMapping(value = "/newuser")
    public String add(@ModelAttribute(value="username") String username, @ModelAttribute(value = "password") String password,
                      @ModelAttribute(value = "passwordConfirmation") String passwordConfirmation, HttpServletResponse response) {

        if (!username.equals("") && !password.equals("") && !passwordConfirmation.equals("")) {
            if (username.length() > 4 && username.length() < 20) {
                if (password.length() > 4 && password.length() < 50) {
                    if (password.equals(passwordConfirmation)){
                        UserModels newUser = new UserModels(username, password);
                        userModelsRepository.save(newUser);
                        Cookie cookie = new Cookie(username, password);
                        cookie.setPath("../");
                        cookie.setDomain("https://shrouded-plains-91045.herokuapp.com/");
                        cookie.setMaxAge(100000);
                        response.addCookie(cookie);
                    } else {
                        // A megadott jelszavak nem egyeznek
                    }
                } else {
                    //A jelszavad tul rovid
                }
            } else {
                // A felhasznalonev tul hosszu vagy tul rovid
            }
        } else {
            //Minden mezot tolts ki
        }
        return  "redirect:../todo";
    }
}
