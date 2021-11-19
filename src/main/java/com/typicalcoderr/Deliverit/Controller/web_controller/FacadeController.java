package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.util.UserUtilities;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 7:04 PM
 */
@Controller
public class FacadeController {

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ModelAndView sendUserHome(){
        boolean isAdmin = UserUtilities.hasRole("ROLE_ADMIN");

        ModelAndView mv = new ModelAndView();

        if(isAdmin){
            mv.setViewName("redirect:/home-admin");
        }else{
            mv.setViewName("redirect:/home-customer");
        }
        return mv;
    }
}
