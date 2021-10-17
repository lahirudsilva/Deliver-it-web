package com.typicalcoderr.Deliverit.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tracking")
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trackingId;
    private String shipmentStatus;
    @OneToOne
    @JoinColumn(name = "shipmentId", referencedColumnName = "shipmentId")
    private Shipment shipment;
}
