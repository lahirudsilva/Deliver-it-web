package com.typicalcoderr.Deliverit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
