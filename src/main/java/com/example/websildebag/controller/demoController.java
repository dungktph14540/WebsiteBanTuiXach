package com.example.websildebag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

    @GetMapping("/api/vi/demo")
    public ResponseEntity<String> sayhhoho(){
        return ResponseEntity.ok("heelo from ");
    }
}
