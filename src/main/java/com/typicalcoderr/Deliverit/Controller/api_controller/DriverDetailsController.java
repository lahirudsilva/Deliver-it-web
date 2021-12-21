package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    private final ShipmentService shipmentService;
    private final TrackingService trackingService;

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

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/getAvailableDrivers")
    public ResponseEntity<Object> getAllAvailableDrivers(){
        try{
            List<DriverDetailsDto> driverDetailsDtoList = driverService.getAllAvailableDrivers();
            return new ResponseEntity<>(driverDetailsDtoList, HttpStatus.OK);

        }catch(DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/assignDriver")
    public ResponseEntity<Object> assignDriver(@RequestBody ShipmentDto shipmentDto){
        try {

            TrackingDto trackingDto = new TrackingDto();
            DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
            trackingDto.setDriverId(shipmentDto.getDriverID());
            trackingDto.setShipmentId(shipmentDto.getShipmentId());

            System.out.println(trackingDto);

            driverDetailsDto.setDriverId(shipmentDto.getDriverID());

            shipmentDto.setPickUpDate(LocalDate.parse(shipmentDto.getPickUp()));
            shipmentDto.setDropOffDate(LocalDate.parse(shipmentDto.getArrival()));
            shipmentService.updateDates(shipmentDto);
            trackingService.addTracking(trackingDto);
            driverService.toggleDriverAvailability(driverDetailsDto);

            return new ResponseEntity<>(HttpStatus.CREATED);




        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }
}
