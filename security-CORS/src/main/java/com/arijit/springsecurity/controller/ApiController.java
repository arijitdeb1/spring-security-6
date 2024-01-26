package com.arijit.springsecurity.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    //@CrossOrigin(origins = "http://localhost:8090")
    @GetMapping("/car")
    public String car(){
       return "This is a Car!";
    }

    @GetMapping("/animal")
    public String animal(){
        return "This is an Animal!";
    }

    @GetMapping("/plant")
    public String plant(){
        return "This is a Plant!";
    }

    @GetMapping("/bird")
    public String bird(){
        return "This is a Bird!";
    }

    @GetMapping("/insect")
    public String insect(){
        return "These are Insects!";
    }

}
