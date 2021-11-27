package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        mv.addObject("customerList", customerService.getAllCustomers());
        return mv;
    }

    @PostMapping("/registerCustomer")
    public ModelAndView registerCustomer(String firstName, String lastName, String email, String password, String contactNumber, String city, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        UserDto dto = new UserDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setContactNumber(contactNumber);
        dto.setCity(city);
        System.out.println(dto);

        try {
            customerService.registerCustomer(dto);
            redirectAttributes.addFlashAttribute("success", "Account created successfully.Please login! ");

        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("errors", new APIException(e.getMessage()).getMessage());
        }

        mv.setViewName("redirect:/register");


        return mv;
    }

}
