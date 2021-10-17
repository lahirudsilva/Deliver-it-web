package com.typicalcoderr.Deliverit.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "username" )
    private User user;


}
