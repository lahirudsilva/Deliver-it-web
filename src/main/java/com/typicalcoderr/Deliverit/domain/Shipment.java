package com.typicalcoderr.Deliverit.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotEmpty(message = "Pickup location is required")
    @Column(nullable = false)
    private String pickupLocation;

    @NotEmpty(message = "DropOff location is required")
    @Column(nullable = false)
    private String dropOffLocation;

    @Email
    @NotEmpty(message = "receiver email is required")
    private String receiverEmail;

    @NotEmpty(message = "receiver name is required")
    @Column(nullable = false)
    private String receiverName;

    @NotEmpty(message = "Phone number is required")
    @Column(nullable = false)
    @Pattern(regexp="(^$|[0-9]{10})",message = "Incorrect Mobile Number")
    private String contactNumber;

    @NotEmpty(message = "Size is required")
    @Column(nullable = false)
    private String size;

    @NotEmpty(message = "status is required")
    @Column(nullable = false)
    private String status;

    private LocalDate pickUpDate;

    private LocalDate dropOffDate;


    @NotNull(message = "weight is required")
    private Double weight;

    @Column( nullable = true)
    private String description;

    @NotNull(message = "estimate cost is required")
    private Double estimatedPrice;

    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", referencedColumnName = "email" )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseId", referencedColumnName = "warehouseNumber")
    private Warehouse warehouse;


}
