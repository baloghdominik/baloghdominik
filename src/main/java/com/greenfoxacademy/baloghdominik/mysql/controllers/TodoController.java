package com.greenfoxacademy.baloghdominik.mysql.controllers;

import com.greenfoxacademy.baloghdominik.mysql.models.Todo;
import com.greenfoxacademy.baloghdominik.mysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
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
    public String add(@ModelAttribute(value="title") String title, @ModelAttribute(value = "urgent") Boolean urgent) {
        if (title != null) {
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