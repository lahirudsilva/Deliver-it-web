package com.typicalcoderr.Deliverit.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 1:54 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "driverDetails")
public class DriverDetails {


//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Integer id;

    @Id
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "driverId is required")
    private String driverId;

    //    ^([0-9]{9}[x|X|v|V]|[0-9]{12})$
    @Column(nullable = false, unique = true)
    @Pattern(regexp="(^([0-9]{9}[x|X|v|V]|[0-9]{12}))",message = "Incorrect NIC Number")
    @NotEmpty(message = "driverId is required")
    private String NIC;

    @NotEmpty(message = "driverId is required")
    private String status;

    //    ^([a-zA-Z]{1,3}|((?!0*-)[0-9]{1,3}))-[0-9]{4}(?<!0{4})$   $valid = '64-8681'; // BAC-1488
    @NotEmpty(message = "vehicle number is required")
    @Pattern(regexp="^([a-zA-Z]{1,3}|((?!0*-)[0-9]{1,3}))-[0-9]{4}(?<!0{4})",message = "Incorrect Vehicle Number")
    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    private Instant registeredOn;

    private Integer noOfAssignedRides;

    //user
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "email")
    private User user;



//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "customerId", referencedColumnName = "email" )
//    private User user;

}
