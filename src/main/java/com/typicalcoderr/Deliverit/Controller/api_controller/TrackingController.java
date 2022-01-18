package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
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
 * Date: Fri
 * Time: 9:56 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TrackingController {

    private TrackingService trackingService;
    private DriverService driverService;

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

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/getAllTracking")
    public ResponseEntity<Object> getTrackingShipments(){
        try{
            List<TrackingDto> dto = trackingService.getAllTrackingShipments();
            return new ResponseEntity<>(dto, HttpStatus.OK);


        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @GetMapping("/getAllOnGoingShipments")
    public ResponseEntity<Object> getAllOnGoingShipments(){
        try{

            List<TrackingDto> dto = trackingService.getAllOnGoingTrackingShipments();
            return new ResponseEntity<>(dto, HttpStatus.OK);


        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/getAllOnGoingShipmentsForAdmin")
    public ResponseEntity<Object> getAllOnGoingShipmentsForAdmin(){
        try{

            List<TrackingDto> dto = trackingService.getAllOnGoingShipmentsForAdmin();
            return new ResponseEntity<>(dto, HttpStatus.OK);


        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }


}
