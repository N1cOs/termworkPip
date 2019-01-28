package ru.ifmo.se.termwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @RequestMapping(value = "/test")
    public ResponseEntity test(){
        return ResponseEntity.status(200).body("test");
    }

    @RequestMapping("/login")
    public String test2(){
        return "permit all";
    }
}
