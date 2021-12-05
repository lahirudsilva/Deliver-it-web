package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
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
 * Date: Thu
 * Time: 12:35 AM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DriverDetailsController {

    private final DriverService driverService;

    @PreAuthorize("hasAnyRole('DRIVER', 'ADMIN')")
    @GetMapping("/driver/getDriverDetails")
    public ResponseEntity<Object> getDriverDetails(){
        try{
            DriverDetailsDto driverDetailsDto = driverService.getDriverDetails();
            return new ResponseEntity<>(driverDetailsDto, HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
