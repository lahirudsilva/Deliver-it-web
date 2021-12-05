package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 9:56 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TrackingController {

    private TrackingService trackingService;
    private DriverService driverService;
    private ShipmentRepository shipmentRepository;

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/confirmPickupDelivery/{shipmentId}")
    public ResponseEntity<Object> updatePickupStatus (@PathVariable int shipmentId){

        try {
            trackingService.ToggleStatustoInWarehouse(shipmentId);
            return new ResponseEntity<>(new SimpleMessageDto("tracking status Updated successfully", HttpStatus.OK), HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

        }

    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/confirmOutForDelivery/{shipmentId}")
    public ResponseEntity<Object> updateOutForDeliveryStatus (@PathVariable int shipmentId){
        try {
            trackingService.ToggleStatustoOutForDelivery(shipmentId);
            return new ResponseEntity<>(new SimpleMessageDto("tracking status Updated successfully", HttpStatus.OK), HttpStatus.OK);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/confirmPackageDelivered/{shipmentId}")
    public ResponseEntity<Object> updateDeliveredStatus (@PathVariable int shipmentId){
        try{
            trackingService.ToggleStatustoDelivered(shipmentId);
            driverService.updatedriverAvailablity();

            return new ResponseEntity<>(new SimpleMessageDto("tracking status Updated successfully", HttpStatus.OK), HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
