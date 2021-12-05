package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 4:36 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final UserService userService;
    private final ShipmentService shipmentService;

    @PostMapping("/register")
    public ResponseEntity<Object> addCustomer(@RequestBody UserDto dto) throws DeliveritException {

        try {
            userService.registerUser(dto);
            return new ResponseEntity<>("User Successfully Registered! Please login.", HttpStatus.CREATED);

        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getCustomerShipments")
    public ResponseEntity<Object> getAllShipmentsForCustomer(){
        try {
            List<ShipmentDto>  dto=  shipmentService.getCustomerRecentShipments();
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }




}
