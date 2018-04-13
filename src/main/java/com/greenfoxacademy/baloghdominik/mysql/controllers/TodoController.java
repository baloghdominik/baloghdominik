package com.greenfoxacademy.baloghdominik.mysql.controllers;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import com.greenfoxacademy.baloghdominik.mysql.services.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OrderBy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private TodoRepository todoRepository;
    private Validation validation;

    @Autowired
    public TodoController(Validation validation, TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.validation = validation;
    }

    public int getPercentage(){
        List<Todo> allTodo = new ArrayList<>();
        List<Todo> notActiveTodo = new ArrayList<>();
        todoRepository.findAll().forEach(allTodo::add);
        allTodo
                .stream()
                .filter(o -> o.isDone())
                .forEach(notActiveTodo::add);

        return (int)(notActiveTodo.size() / (double)allTodo.size() * 100);
    }

    @GetMapping(value={"/", "", "/list"})
    public String list(@RequestParam(value = "isActive", required = false) String isActive, Model model, HttpServletRequest response) throws NoSuchAlgorithmException {
        if (validation.isLoggedIn(response)) {
            if (isActive == null) {
                validation.isLoggedIn(response);
                model.addAttribute("todo", todoRepository.findAll());
            } else if (isActive.equals("true") || isActive.equals("false")) {
                model.addAttribute("todo", todoRepository.findBydone(!Boolean.valueOf(isActive)));
            } else {
                model.addAttribute("todo", todoRepository.findAll());
            }
            model.addAttribute("userName", validation.getLoggedInUsername(response));
            model.addAttribute("percentage", getPercentage());
            validation.generateValidationCode();
            model.addAttribute("validationCode", validation.getValidation());
            return "todolist";
        } else {
            return "redirect:login";
        }
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value = "id", required = false) Long deleteId) {
        if (deleteId != null) {
            todoRepository.deleteById(deleteId);
        }
        return  "redirect:../todo";
    }

    @GetMapping(value = "/{validation}/add")
    public String add(@PathVariable("validation") String code, @ModelAttribute(value="title") String title, @ModelAttribute(value = "urgent") Boolean urgent) {
        if (!title.equals("") && code != null && validation.getValidation().equals(code)) {
            Todo newTodo = new Todo(title);
            newTodo.setUrgent(urgent);
            todoRepository.save(newTodo);
            validation.generateValidationCode();
        }
        return  "redirect:../../todo";
    }

    @GetMapping(value = "/complete")
    public String complete(@RequestParam(value = "id", required = false) Long complete) {
        if (complete != null) {
            Todo todo = todoRepository.findById(complete).orElse(null);
            if (todo != null){
                todo.setDone(true);
                todoRepository.save(todo);
            }
        }
        return  "redirect:../todo";
    }

    @GetMapping(value = "empty")
    public String empty() {
        List<Todo> allTodo = new ArrayList<>();
        todoRepository.findAll().forEach(allTodo::add);
        for (int i = 0; i < allTodo.size(); i++) {
            todoRepository.deleteById(allTodo.get(i).getId());
        }
        return  "redirect:../todo";
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletResponse response) {
        Cookie cookieUser = new Cookie("userValidation", null);
        cookieUser.setPath("/");
        cookieUser.setHttpOnly(true);
        cookieUser.setMaxAge(0);
        response.addCookie(cookieUser);

        Cookie cookieID = new Cookie("userID", null);
        cookieID.setPath("/");
        cookieID.setHttpOnly(true);
        cookieID.setMaxAge(0);
        response.addCookie(cookieID);
        return  "redirect:../login";
    }
}