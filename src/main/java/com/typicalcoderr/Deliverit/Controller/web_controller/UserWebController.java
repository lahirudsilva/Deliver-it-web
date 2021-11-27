package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.*;
import com.typicalcoderr.Deliverit.dto.*;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import com.typicalcoderr.Deliverit.util.UserUtilities;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class UserWebController {

    private final UserService userService;
    private final ShipmentService shipmentService;
    private final DriverService driverService;
    //    private final TrackingService trackingService;
    private final CustomerService customerService;
    private final AuthService authService;
    private final WarehouseService warehouseService;

    @GetMapping("/login")
    public ModelAndView login(String error) {
        ModelAndView mv = new ModelAndView();

        if (error != null) mv.addObject("error", "Invalid login credentials!");
        System.out.println(error);
        mv.setViewName("login");
        return mv;

    }

    @GetMapping("/register")
    public ModelAndView createAccount() {
        ModelAndView mv = new ModelAndView();


        mv.setViewName("signUp");
        return mv;

    }

    @GetMapping("/home-supervisor")
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    public ModelAndView homeAdmin() {
        // Direct admins to their home page.
        ModelAndView mv = new ModelAndView();

        mv.setViewName("adminHome");

        try {
            mv.addObject("warehouseLocation", warehouseService.getWarehouseLocation());
            mv.addObject("pendingRequests", shipmentService.getAllPendingRequests());
            mv.addObject("availableDrivers", driverService.getAllAvailableDrivers());
            mv.addObject("name", userService.getName());
        } catch (DeliveritException e) {
            mv.addObject("error", new APIException(e.getMessage()));
            e.printStackTrace();
        }
        return mv;

    }

    @GetMapping("/home-admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView getAllPendingRequestsForAdmin(){
        // Direct admins to their home page.
        ModelAndView mv = new ModelAndView();

        mv.setViewName("adminHome");
        try {

            mv.addObject("pendingRequests", shipmentService.getAllPendingRequestsForAdmin());
            mv.addObject("name", userService.getName());
        } catch (DeliveritException e) {
            mv.addObject("error", new APIException(e.getMessage()));
            e.printStackTrace();
        }
        return mv;
    }

    @GetMapping("/home-customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView homeCustomer() {
        // Direct customers to their home page.

        ModelAndView mv = new ModelAndView();
        mv.setViewName("customerHome");

        try {
            mv.addObject("packageList", customerService.getAllMyPackages());
            mv.addObject("name", userService.getName());


        } catch (DeliveritException e) {
            e.printStackTrace();
        }
        return mv;

    }



    @GetMapping("/home-driver")
    @PreAuthorize("hasRole('DRIVER')")
    public ModelAndView homeDriver() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("driverHome");

        try {
            mv.addObject("name", userService.getName());

        } catch (DeliveritException e) {
            e.printStackTrace();
        }
        return mv;

    }


    //driver home method here


    @PostMapping("/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'SUPERVISOR')")
    public ModelAndView changePassword(ChangePasswordRequest dto, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        boolean isAdmin = UserUtilities.hasRole("ROLE_ADMIN");
        boolean isSupervisor = UserUtilities.hasRole("ROLE_SUPERVISOR");


        if (isAdmin || isSupervisor) {
            mv.setViewName("redirect:/home-admin");

        } else {
            mv.setViewName("redirect:/home-customer");
        }

        try {
            authService.changePassword(dto);
            redirectAttributes.addFlashAttribute("successSetting", new SimpleMessageDto("Successfully changed password!"));
        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("errorSetting", new APIException(e.getMessage()));
        }

        return mv;

    }


}
