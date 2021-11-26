package com.typicalcoderr.Deliverit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 11:58 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity (name = "warehouse")
public class Warehouse {

    @Id
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "warehouseNumber is required")
    private String warehouseNumber;

    @NotEmpty(message = "location is required")
    private String location;


}
