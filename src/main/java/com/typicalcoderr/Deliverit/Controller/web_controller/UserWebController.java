package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.*;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class UserWebController {

    private final UserService userService;
    private final ShipmentService shipmentService;
    private final DriverService driverService;
    private final TrackingService trackingService;
    private final CustomerService customerService;

    @GetMapping("/login")
    public ModelAndView login(String error) {
        ModelAndView mv = new ModelAndView();

        if (error != null) mv.addObject("error", "Invalid login credentials!");
        System.out.println(error);
        mv.setViewName("login");
        return mv;

    }

    @GetMapping("/home-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView homeAdmin() {
        // Direct admins to their home page.
        ModelAndView mv = new ModelAndView();



        mv.setViewName("adminHome");


        try {
            mv.addObject("pendingRequests", shipmentService.getAllPendingRequests());
            mv.addObject("availableDrivers", driverService.getAllAvailableDrivers());
            mv.addObject("name", userService.getName());
        } catch (DeliveritException e) {
            mv.addObject("error", new APIException(e.getMessage()));
            e.printStackTrace();
        }
        return mv;

    }

    @GetMapping("/home-customer")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ModelAndView homeCustomer() {
        // Direct customers to their home page.

        ModelAndView mv = new ModelAndView();
        mv.setViewName("customerHome");

        try {
            mv.addObject("packageList", customerService.getAllMyPackages());
            mv.addObject("name", userService.getName());
            System.out.println(mv);

        } catch (DeliveritException e) {
            e.printStackTrace();
        }
        return mv;

    }


    //driver home method here


    @PostMapping("/assign-driver")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView assignDriver(@RequestParam String pickupDate, String dropoffDate, String driverId, String shipmentId , RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

//        System.out.println(driverId);
//        System.out.println(shipmentId);

        try {
            ShipmentDto shipmentDto = new ShipmentDto();
            shipmentDto.setShipmentId(Integer.parseInt(shipmentId));
            shipmentDto.setPickUpDate(LocalDate.parse(pickupDate));
            shipmentDto.setDropOffDate(LocalDate.parse(dropoffDate));

            TrackingDto trackingDto = new TrackingDto();
            trackingDto.setDriverId(driverId);
            trackingDto.setShipmentId(Integer.parseInt(shipmentId));

            DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
            driverDetailsDto.setDriverId(driverId);

            shipmentService.updateDates(shipmentDto);
            trackingService.addTracking(trackingDto);
            driverService.toggleDriverAvailability(driverDetailsDto);

//            mv = homeAdmin(null);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Driver Assigned successfully!"));

        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }

        mv.setViewName("redirect:/home-admin");

        return mv;




    }


}
