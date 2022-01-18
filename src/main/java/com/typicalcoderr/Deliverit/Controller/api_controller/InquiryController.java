package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.InquiryService;
import com.typicalcoderr.Deliverit.domain.Inquiry;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.dto.InquiryDto;
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
 * Date: Thu
 * Time: 12:31 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/getAllInquiriesForWarehouse")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<Object> getAllInquiriesForWarehouse() {
        try {
            List<InquiryDto> inquiryDto = inquiryService.getAllInquiresForWarehouse();
            return new ResponseEntity<>(inquiryDto, HttpStatus.OK);


        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllInquiries")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> getAllInquiries() {
        try {
            List<InquiryDto> inquiryDto = inquiryService.getAllInquires();
            return new ResponseEntity<>(inquiryDto, HttpStatus.OK);


        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllMyInquiries")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Object> getAllMyInquiries(){
        try {
            List<InquiryDto> inquiryDto = inquiryService.getAllMyInquires();
            return new ResponseEntity<>(inquiryDto, HttpStatus.OK);


        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/sendInquiryResponse")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<Object> sendResponse(@RequestBody InquiryDto dto){


        try {
            Inquiry result = inquiryService.addResponse(dto);;
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DeliveritException e) {
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/makeInquiry")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Object> makeInquiry(@RequestBody InquiryDto dto){
        System.out.println("sdsdsddddsdsd"+dto);
        try{
            Inquiry result = inquiryService.addInquiries(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
