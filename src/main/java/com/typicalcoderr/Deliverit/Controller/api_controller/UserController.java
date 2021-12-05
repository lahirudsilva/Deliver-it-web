package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sun
 * Time: 11:34 AM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private CustomerService customerService;

    @PreAuthorize("hasAnyRole('DRIVER','CUSTOMER')")
    @GetMapping("/getProfile")
    public ResponseEntity<Object> getProfile(){
        try {
            UserDto user = userService.getUserDetais();
            return new ResponseEntity<>(user, HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

        }
    }

    @PreAuthorize("hasAnyRole('DRIVER', 'CUSTOMER', 'ADMIN')")
    @GetMapping("getLoggedInUser")
    public ResponseEntity<Object> getLoggedInUser(){
        try {
            UserDto user = customerService.getLoggedInUser();
            return new ResponseEntity<>(user, HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
