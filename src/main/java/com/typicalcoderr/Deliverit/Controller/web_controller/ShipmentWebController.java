package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 1:48 PM
 */
@Controller
@AllArgsConstructor
public class ShipmentWebController {

    private final ShipmentService shipmentService;


    @GetMapping("/createPackage")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView sendPackageForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("createPackageForm");


        return mv;
    }

    @PostMapping("/add-package")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView addPackage(@RequestParam String senderAddress, String receiverAddress, String receiverEmail, String receiverContactNumber, String packageSize, Double packageWeight, double estimatedCost) {
        ModelAndView mv = new ModelAndView();
        try {

            ShipmentDto dto = new ShipmentDto();
            dto.setPickupLocation(senderAddress);
            dto.setDropOffLocation(receiverAddress);
            dto.setReceiverEmail(receiverEmail);
            dto.setReceiverContactNumber(receiverContactNumber);
            dto.setSize(packageSize);
            dto.setWeight(packageWeight);
            dto.setEstimatedPrice(estimatedCost);


            shipmentService.addShipment(dto);
            mv.addObject("success", new SimpleMessageDto("Package request added Successfully!"));


        } catch (DeliveritException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }
        mv.setViewName("createPackageForm");
        return mv;
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView viewAllPendingRequests(){
        return getPendingRequests();
    }

    //method to all the newly added pending requests from customers
    private ModelAndView getPendingRequests(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminHome");
        mv.addObject("requests", shipmentService.getAllPendingRequests());

        return mv;
    }
}
