package com.typicalcoderr.Deliverit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipmentId;
    private LocalDate pickUpDate;
    private String pickupLocation;
    private String dropOffLocation;
    private LocalDate dropOffDate;
    private String size;
    private Double estimatedPrice;
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "email" )
    private User user;


}
