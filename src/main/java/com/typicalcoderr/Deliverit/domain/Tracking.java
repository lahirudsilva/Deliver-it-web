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
@Entity(name = "tracking")
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trackingId;


    private String shipmentStatus;

    private Instant updatedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipmentId", referencedColumnName = "shipmentId")
    private Shipment shipment;
    //driveid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driverId", referencedColumnName = "driverId")
    private DriverDetails driverDetails;

}
