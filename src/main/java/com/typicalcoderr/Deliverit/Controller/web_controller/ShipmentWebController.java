package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.EmailService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.Service.WarehouseService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

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
    private final EmailService emailService;
    private final WarehouseService warehouseService;
    private final CustomerService customerService;


    @GetMapping("/shipments")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView getAllShipments() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shipments");
        mv.addObject("shipmentList", shipmentService.getAllOnGoingShipments());
        return mv;

    }

    @GetMapping("/myPackages")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView getAllMyShipments() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("myPackages");
        try {
            mv.addObject("myShipments", customerService.getAllMyPackages());
        }catch (DeliveritException e){
            e.printStackTrace();
        }

        return mv;

    }

    @GetMapping("/shipmentsForWarehouse")
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    public ModelAndView getAllShipmentsForWarehouse() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shipments");
        try {
            mv.addObject("warehouseLocation", warehouseService.getWarehouseLocation());
            mv.addObject("shipmentListForWarehouse", shipmentService.getAllOnGoingShipmentsForWarehouse());
        } catch (DeliveritException e) {
            mv.addObject("error", new APIException(e.getMessage()));
        }
        return mv;

    }


    @GetMapping("/createPackage")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView sendPackageForm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("warehouses", warehouseService.getAllWarehouses());
        mv.setViewName("createPackageForm");


        return mv;
    }

    @PostMapping("/add-package")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView addPackage(@RequestParam String senderAddress, String receiverAddress, String receiverEmail, String receiverContactNumber, String packageSize, Double packageWeight, double estimatedCost, String warehouseNumber, String description, String receiverName, RedirectAttributes redirectAttributes) {
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
            dto.setWarehouseNumber(warehouseNumber);
            dto.setDescription(description);
            dto.setReceiverName(receiverName);


            shipmentService.addShipment(dto);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Package request added Successfully!"));


        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/createPackage");
        return mv;
    }

//    @GetMapping("/requests")
//    @PreAuthorize("hasRole('SUPERVISOR')")
//    public ModelAndView viewAllPendingRequests(){
//        return getPendingRequests();
//    }
//
//    //method to all the newly added pending requests from customers
//    private ModelAndView getPendingRequests(){
//        ModelAndView mv = new ModelAndView();
//
//        try {
//            mv.setViewName("adminHome");
//
//            System.out.println(mv);
//            mv.addObject("requests", shipmentService.getAllPendingRequests());
//        }catch (DeliveritException e){
//            e.printStackTrace();
//        }
//
//        return mv;
//    }


    @PostMapping("/rejectShipment")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ModelAndView rejectShipments(@RequestParam Integer shipmentId, String senderEmail, String receiverEmail, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        System.out.println(senderEmail + receiverEmail);

        ShipmentDto dto = new ShipmentDto();
        dto.setShipmentId(shipmentId);
        dto.setSenderEmail(senderEmail);
        dto.setReceiverEmail(receiverEmail);
        try {
            emailService.sendSimpleMessage(dto);
            shipmentService.changeShipmentStatusToReject(dto);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Successfully delivery request has been Rejected!"));
        } catch (DeliveritException | MessagingException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/home-admin");
        return mv;

    }

    @PostMapping("/cancelPackage")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView cancelPackage(@RequestParam Integer shipmentId,  RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        try {
            shipmentService.cancelPendingPackage(shipmentId);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Successfully delivery request has been canceled!"));
        }catch (DeliveritException e){
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/home-customer");
        return mv;
    }


}
