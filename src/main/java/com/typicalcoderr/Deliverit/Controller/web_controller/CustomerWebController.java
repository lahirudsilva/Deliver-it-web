package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 3:03 PM
 */
@Controller
@AllArgsConstructor
public class CustomerWebController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView allCustomers(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customers");
        mv.addObject("customerList", customerService.getAllCustomers());
        return mv;
    }

}
