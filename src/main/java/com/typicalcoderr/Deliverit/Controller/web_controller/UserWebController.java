package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserWebController {

    private final CustomerService customerService;
    private final ShipmentService shipmentService;

    @GetMapping("/login")
    public ModelAndView login(String error){
        ModelAndView mv = new ModelAndView();

        if (error != null) mv.addObject("error", "Invalid login credentials!");
        System.out.println(error);
        mv.setViewName("login");
        return mv;

    }

    @GetMapping("/home-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView homeAdmin(){
        // Direct admins to their home page.

        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminHome");
        mv.addObject("pendingRequests", shipmentService.getAllPendingRequests());

        try{
            mv.addObject("name",customerService.getName());
        }catch (DeliveritException e){
            e.printStackTrace();
        }
        return mv;

    }

    @GetMapping("/home-customer")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ModelAndView homeCustomer(){
        // Direct customers to their home page.

        ModelAndView mv = new ModelAndView();
        mv.setViewName("customerHome");

        try{
            mv.addObject("name", customerService.getName() );

        }catch (DeliveritException e){
            e.printStackTrace();
        }
        return mv;

    }



    //driver home method here


}
