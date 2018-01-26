package com.example.myspringsecurityboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main(Model model){
        return "index";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}
