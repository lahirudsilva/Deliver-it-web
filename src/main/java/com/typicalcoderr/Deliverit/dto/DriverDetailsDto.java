package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 12:44 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverDetailsDto {

    private String driverFirstName;
    private String driverLastName;
    private String driverId;
    private String driverEmail;
    private String NIC;
    private String status;
    private Integer noOfRidesToGo;
    private String vehicleNumber;
    private String contactNumber;
    private String registeredOn;
    private String warehouseLocation;
    private User user;
}
