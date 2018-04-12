package com.greenfoxacademy.baloghdominik.mysql.controllers;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private String validation;

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
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

    public void generateRandom() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 50) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        validation = saltStr;
    }

    @GetMapping(value={"/", "", "/list"})
    public String list(@RequestParam(value = "isActive", required = false) String isActive, Model model) {
        if (isActive == null) {
            model.addAttribute("todo", todoRepository.findAll());
        } else if (isActive.equals("true") || isActive.equals("false")) {
            model.addAttribute("todo", todoRepository.findBydone(!Boolean.valueOf(isActive)));
        } else {
            model.addAttribute("todo", todoRepository.findAll());
        }
        model.addAttribute("percentage", getPercentage());
        generateRandom();
        model.addAttribute("validationCode", validation);
        return "todolist";
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam(value = "id", required = false) Long deleteId) {
        if (deleteId != null) {
            todoRepository.deleteById(deleteId);
        }
        return  "redirect:../todo";
    }

    @GetMapping(value = "/add")
    public String add(@RequestParam(value = "code", required = true) String code, @ModelAttribute(value="title") String title, @ModelAttribute(value = "urgent") Boolean urgent) {
        if (title != null && code != null && validation.equals(code)) {
            Todo newTodo = new Todo(title);
            newTodo.setUrgent(urgent);
            todoRepository.save(newTodo);
        }
        return  "redirect:../todo";
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
}