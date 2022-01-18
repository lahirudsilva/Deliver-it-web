package com.typicalcoderr.Deliverit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity (name = "user")
public class User {


    @Id
    @NotEmpty(message = "Email is required")
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "(\\S+@\\S+\\.\\S+)?$",   message = "Invalid Email format")
    private String email;

    @NotEmpty(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @NotEmpty(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Transient
    private String matchingPassword;


    @NotEmpty(message = "Phone number is required")
    @Column(nullable = false, unique = true)
//    @Pattern(regexp="(^$|[0-9]{10})",message = "Incorrect Mobile Number") //^(?:0|94|\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\d)\d{6}$
    @Pattern(regexp="(^(?:0|94|\\+94)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|912)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6})?$",message = "Invalid Mobile Number format")
    private String contactNumber;

    @NotEmpty(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotEmpty(message = "Role is required")
    @Column(nullable = false)
    private String userRole;

    @Column(columnDefinition="bit default 1")
    private Boolean isVerified;

    @Column(columnDefinition="bit default 0")
    private Boolean isBlackListed;

    private Instant joinedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseId", referencedColumnName = "warehouseNumber")
    private Warehouse warehouse;




}
