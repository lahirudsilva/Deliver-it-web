package com.typicalcoderr.Deliverit.Controller.api_controller;

import com.typicalcoderr.Deliverit.Service.AuthService;
import com.typicalcoderr.Deliverit.dto.ChangePasswordRequest;
import com.typicalcoderr.Deliverit.dto.LoginRequest;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Fri
 * Time: 4:06 PM
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request){
        try{
            return new ResponseEntity<>(authService.login(request), HttpStatus.OK);

        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request){
        try {
            return new ResponseEntity<>(authService.changePassword(request), HttpStatus.OK);
        }catch (DeliveritException e){
            return new ResponseEntity<>(new APIException(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
