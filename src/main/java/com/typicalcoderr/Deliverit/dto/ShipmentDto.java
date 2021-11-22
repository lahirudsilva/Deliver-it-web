package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 8:40 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDto {

    private Integer shipmentId;
    private String pickupLocation;
    private String dropOffLocation;
    private String senderEmail;
    private String receiverEmail;
    private String receiverContactNumber;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private String size;
    private String status;
    private Double weight;
    private Double estimatedPrice;
    private String createdAt;
    private User user;




}
