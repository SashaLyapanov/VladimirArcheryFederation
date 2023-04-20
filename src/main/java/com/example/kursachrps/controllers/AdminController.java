package com.example.kursachrps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

//    @GetMapping("/hello")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloPage() {
        return "admin/hello";
    }

}
