package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 1:15 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto {

    private String warehouseNumber;
    private String location;
    private Warehouse warehouse;


}
