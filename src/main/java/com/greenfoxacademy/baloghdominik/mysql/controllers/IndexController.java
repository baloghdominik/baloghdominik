package com.greenfoxacademy.baloghdominik.mysql.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value={"/", ""})
    public String list() {
        return "redirect:/todo";
    }

}
