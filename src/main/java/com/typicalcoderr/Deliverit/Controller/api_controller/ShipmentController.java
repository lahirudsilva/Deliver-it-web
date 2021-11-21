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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/add-package")
    public ResponseEntity<Object> addShipment(@RequestBody ShipmentDto dto){
        try {
            Shipment result = shipmentService.addShipment(dto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }


    }
}
