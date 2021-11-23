package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 7:53 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingDto {

    private Integer trackingId;
    private String shipmentStatus;
    private String driverId;
    private Integer shipmentId;
    private String updatedAt;
    private Shipment shipment;
    private DriverDetails driverDetails;
}
