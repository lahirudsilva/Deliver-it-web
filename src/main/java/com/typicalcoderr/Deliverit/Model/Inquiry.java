package com.typicalcoderr.Deliverit.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inquiry")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;
    private String description;
    @OneToOne
    @JoinColumn(name = "shipmentId", referencedColumnName = "shipmentId")
    private Shipment shipment;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "username")
    private User user;



}
