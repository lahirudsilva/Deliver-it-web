package com.typicalcoderr.Deliverit.Controller.web_controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserWebController {

    @GetMapping("/login")
    public ModelAndView login(String error){
        ModelAndView mv = new ModelAndView();

        if (error != null) mv.addObject("error", "Invalid login credentials!");
        System.out.println(error);
        mv.setViewName("login");
        return mv;

    }

    @GetMapping("/home-admin")
    public ModelAndView homeAdmin(){
        // Direct admins to their home page.

        ModelAndView mv = new ModelAndView();


        mv.setViewName("adminHome");
        return mv;

    }

    @GetMapping("/home-customer")
    public ModelAndView homeCustomer(){
        // Direct customers to their home page.

        ModelAndView mv = new ModelAndView();


        mv.setViewName("customerHome");
        return mv;

    }
}
