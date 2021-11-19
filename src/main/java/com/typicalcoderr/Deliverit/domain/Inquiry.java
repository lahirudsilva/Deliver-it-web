package com.typicalcoderr.Deliverit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "inquiry")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;
    private String description;
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "shipmentId", referencedColumnName = "shipmentId")
    private Shipment shipment;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "email")
    private User user;



}
