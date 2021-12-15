package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.*;
import com.typicalcoderr.Deliverit.dto.*;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Mon
 * Time: 12:55 AM
 */
@Controller
@AllArgsConstructor
public class DriverWebController {

    private final UserService userService;
    private final DriverService driverService;
    private final ShipmentService shipmentService;
    private final TrackingService trackingService;
    private final WarehouseService warehouseService;


    @GetMapping("/drivers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView allDrivers() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("drivers");
        mv.addObject("driverList", driverService.getAllDrivers());
        return mv;
    }

    @GetMapping("/deliveries")
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ModelAndView getTodayDeliveries() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("deliveries");
        try {
            mv.addObject("pickupList", shipmentService.getAllPickupDeliveries());
            mv.addObject("inWarehouseList", shipmentService.getAllInWarehouseDeliveries());
            mv.addObject("onDeliveryList", shipmentService.getAllPackagesForDeliveries());

        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
            e.printStackTrace();
        }

        return mv;
    }

    @GetMapping("/driversForWarehouse")
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    public ModelAndView allDriversforWarehouse() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("drivers");
        try {
            mv.addObject("warehouseLocation", warehouseService.getWarehouseLocation());
            mv.addObject("driverListForWarehouse", warehouseService.getAllDriversForWareHouse());
        }catch (DeliveritException e){
            mv.addObject("error" ,new APIException(e.getMessage()));
        }
        return mv;
    }



    @GetMapping("/drivers/register-driver")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView registerDriverForm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("warehouses", warehouseService.getAllWarehouses());
        mv.setViewName("registerDriverForm");
        return mv;
    }

    @PostMapping("/add-driver")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addDriver(@RequestParam String firstName, String lastName, String email, String contactNumber, String driverId, String NIC, String vehicleNumber,String warehouseId, String city ,RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();


        try {

            String id = driverId;
            String nicNo = NIC;
            String vehicleNo = vehicleNumber;

            if (!(driverService.isExist(id, nicNo, vehicleNo))) {

                UserDto userDto = new UserDto();
                userDto.setFirstName(firstName);
                userDto.setLastName(lastName);
                userDto.setEmail(email);
                userDto.setContactNumber(contactNumber);
                userDto.setPassword(driverId.toUpperCase(Locale.ROOT));
                userDto.setUserRole("driver");
                userDto.setWarehouseNumber(warehouseId);
                userDto.setCity(city);
                System.out.println(userDto);

                DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
                driverDetailsDto.setDriverId(driverId);
                driverDetailsDto.setNIC(NIC);
                driverDetailsDto.setVehicleNumber(vehicleNumber);
                driverDetailsDto.setDriverEmail(email);


                userService.registerUser(userDto);
                driverService.addDriverDetails(driverDetailsDto);
                redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Driver added successfully!"));


            }


        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/drivers/register-driver");
        return mv;


    }


    @PostMapping("/assign-driver")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ModelAndView assignDriver(@RequestParam LocalDate pickupDate, LocalDate dropoffDate, String driverId, String shipmentId , RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();



        try {
            ShipmentDto shipmentDto = new ShipmentDto();
            shipmentDto.setShipmentId(Integer.parseInt(shipmentId));
            shipmentDto.setPickUpDate(pickupDate);
            shipmentDto.setDropOffDate(dropoffDate);

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

        mv.setViewName("redirect:/home-supervisor");

        return mv;




    }


}
