package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

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
    private final UserService userService;

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView allCustomers() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customers");
        try {
            mv.addObject("customerList", customerService.getAllCustomers());
        }catch (DeliveritException e){
            mv.addObject("error" ,new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/registerCustomer")
    public ModelAndView registerCustomer(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        UserDto dto = new UserDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setContactNumber(user.getContactNumber());
        dto.setCity(user.getCity());
        System.out.println(dto);
        if(bindingResult.hasErrors()){
            mv.setViewName("signUp");
            System.out.println("errors" + bindingResult.hasErrors() + bindingResult.getAllErrors());
        }else if(!Objects.equals(user.getPassword(), user.getMatchingPassword())){

            mv.addObject("passwordError", "Password not matching!");
            mv.setViewName("signUp");
        }else if(user.getPassword().length() < 5){
            mv.addObject("passwordError", "password must contain at least 5 characters!");
            mv.setViewName("signUp");
        } else if(!bindingResult.hasErrors()){
            try {
                customerService.registerCustomer(dto);
                redirectAttributes.addFlashAttribute("success", "Account created successfully.Please login! ");
                mv.setViewName("redirect:/login");

            } catch (DeliveritException e) {
                redirectAttributes.addFlashAttribute("errors", new APIException(e.getMessage()).getMessage());
                mv.setViewName("redirect:/register");
            }
        }

        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setVerified")
    public ModelAndView setVerified(@RequestParam String email, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        try {
            customerService.setVerified(email);

        }catch (DeliveritException e){
            e.printStackTrace();
        }

        mv.setViewName("redirect:/customers");
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setBlacklisted")
    public ModelAndView setBlacklisted(@RequestParam String email, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        try {
            customerService.setBlacklisted(email);

        }catch (DeliveritException e){
            e.printStackTrace();
        }

        mv.setViewName("redirect:/customers");
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeCustomer")
    public ModelAndView removeCustomer(@RequestParam String email, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        try {
            customerService.removeCustomer(email);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Customer removed Successfully"));
        }catch (DeliveritException e){
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }

        mv.setViewName("redirect:/customers");
        return mv;
    }

}
