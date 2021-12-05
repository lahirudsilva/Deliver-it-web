package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

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
    private String senderContactNumber;
    private String receiverContactNumber;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private String size;
    private String status;
    private Double weight;
    private Double estimatedPrice;
    private String createdAt;
    private String description;
    private String warehouseLocation;
    private String warehouseNumber;
    private String receiverName;
    private String senderFirstName;
    private String senderLastName;
    private User user;

    //mobile
    @NonNull
    private String arrival;
    @NonNull
    private String pickUp;




}
