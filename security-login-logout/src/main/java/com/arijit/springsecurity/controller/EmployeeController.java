package com.arijit.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping(value = {"/home"})
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @GetMapping(value = {"/manage-employee"})
    public ModelAndView manageEmployee(){
        return new ModelAndView("manage-employee");
    }

    @GetMapping(value = {"/access-denied"})
    public ModelAndView accessDenied(){
        return new ModelAndView("access-denied");
    }
}
