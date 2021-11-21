package com.typicalcoderr.Deliverit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sun
 * Time: 11:53 AM
 */
@Data
@AllArgsConstructor
public class SimpleMessageDto {

    private String message;
    private HttpStatus type;

    public SimpleMessageDto(String message) {
        this.message = message;
    }
}
