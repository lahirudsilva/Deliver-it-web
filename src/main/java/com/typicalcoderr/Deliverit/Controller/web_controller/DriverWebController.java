package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/drivers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView alldrivers(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("drivers");
        mv.addObject("driverList", driverService.getAllDrivers());
        return mv;
    }

    @GetMapping("/drivers/register-driver")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView registerDriverForm(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registerDriverForm");
        return mv;
    }

    @PostMapping("/add-driver")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addDriver(@RequestParam String firstName, String lastName, String email, String contactNumber, String driverId, String NIC, String vehicleNumber   ){
        ModelAndView mv = new ModelAndView();



        try {

            String id = driverId;
            String nicNo = NIC;
            String vehicleNo = vehicleNumber;

            if(!(driverService.isExist(id, nicNo,vehicleNo))){

                UserDto userDto = new UserDto();
                userDto.setFirstName(firstName);
                userDto.setLastName(lastName);
                userDto.setEmail(email);
                userDto.setContactNumber(contactNumber);
                userDto.setPassword(driverId.toUpperCase(Locale.ROOT));
                userDto.setUserRole("driver");


                DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
                driverDetailsDto.setDriverId(driverId);
                driverDetailsDto.setNIC(NIC);
                driverDetailsDto.setVehicleNumber(vehicleNumber);
                driverDetailsDto.setDriverEmail(email);


                userService.registerCustomer(userDto);
                driverService.addDriverDetails(driverDetailsDto);
                mv.addObject("success", new SimpleMessageDto("Driver added successfully!"));


            }


        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
        }
        mv.setViewName("registerDriverForm");
        return mv;







    }
}
