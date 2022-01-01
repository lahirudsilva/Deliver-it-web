package com.typicalcoderr.Deliverit.dto;

import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Wed
 * Time: 9:36 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InquiryDto {

    private Integer inquiryId;
    private String description;
    private String response;
    private String createdAt;
    private String inquiryStatus;
    private Integer shipmentId;
    private String userId;
    private Shipment shipment;
    private User user;
}
