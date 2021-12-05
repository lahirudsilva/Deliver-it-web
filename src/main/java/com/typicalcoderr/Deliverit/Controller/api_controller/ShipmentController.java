package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
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
 * Date: Sat
 * Time: 8:35 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/addPackage")
    public ResponseEntity<Object> addShipment(@RequestBody ShipmentDto dto){
        try {
            Shipment result = shipmentService.addShipment(dto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }


    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/shipmentsDetails")
    public ResponseEntity<Object> getAcceptedShipmentsForDriver(){
        try{
//            System.out.println(shipmentService.getAllShipmentsForDiver().size());
            List<ShipmentDto> shipmentDto = shipmentService.getAllShipmentsForDiver();
            return new ResponseEntity<>(shipmentDto, HttpStatus.OK);


        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/getPickupDeliveries")
    public ResponseEntity<Object> getAllPickupDeliveries(){
        try {
            List<ShipmentDto> shipmentDto = shipmentService.getAllPickupDeliveries();
            return new ResponseEntity<>(shipmentDto, HttpStatus.OK);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/getInWarehouseDeliveries")
    public ResponseEntity<Object> getInWarehouseDeliveries(){
        try{

            List<ShipmentDto> shipmentDto = shipmentService.getAllInWarehouseDeliveries();
            return new ResponseEntity<>(shipmentDto, HttpStatus.OK);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/getPackagesForDelivery")
    public ResponseEntity<Object> getPackagesForDelivery(){
        try{
            List<ShipmentDto> shipmentDto = shipmentService.getAllPackagesForDeliveries();
            return new ResponseEntity<>(shipmentDto, HttpStatus.OK);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/getAllPastRides")
    public ResponseEntity<Object> getPastDeliveriesForDriver(){
        try{
            List<ShipmentDto> shipmentDto = shipmentService.getPastDeliveries();
            return new ResponseEntity<>(shipmentDto, HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }



//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/home-admin")
//    public ResponseEntity<Object> getAllPendingRequests(){
//        return new ResponseEntity<>(shipmentService.getAllPendingRequests(), HttpStatus.OK);
//    }
}
