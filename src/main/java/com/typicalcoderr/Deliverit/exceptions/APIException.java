package com.typicalcoderr.Deliverit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 10:16 PM
 */
@Data
@AllArgsConstructor
public class APIException {

    private String message;
    private HttpStatus status;

    public APIException(String message){
        this.message = message;
    }
}
