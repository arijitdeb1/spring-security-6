package com.arijit.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlayerController {

    @GetMapping(value = {"/contact-us"})
    public ModelAndView login(){
        return new ModelAndView("contact-us");
    }

    @GetMapping(value = {"/home"})
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @GetMapping(value = {"/manage-player"})
    public ModelAndView manageEmployee(){
        return new ModelAndView("manage-player");
    }

    @GetMapping(value = {"/access-denied"})
    public ModelAndView accessDenied(){
        return new ModelAndView("access-denied");
    }

    @GetMapping(value = {"/"})
    public ModelAndView ch(){
        return new ModelAndView("chelsea-player");
    }
}
