package com.greenfoxacademy.baloghdominik.mysql.controllers;

import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private String validation;

    private TodoRepository todoRepository;

    @Autowired
    public RegisterController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping(value={"", "/"})
    public String register(Model model) {
        TodoController todoController = new TodoController(todoRepository);
        todoController.generateRandom();
        model.addAttribute("validationCode", validation);
        return "register";
    }
}
