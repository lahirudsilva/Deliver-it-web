package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 8:56 PM
 */
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto registerCustomer(UserDto dto) throws DeliveritException {

        Optional existing = userRepository.findUserByEmail(dto.getEmail());

        if(existing.isPresent()){
            throw new DeliveritException("Email already exists");
        }

        //Save new user after mapping dto to entity class
        User customer = map(dto);
        userRepository.save(customer);
        return dto;

    }

    public String getName() throws DeliveritException{
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getFirstName();
    }





//    Method to map data transfer object to domain class
    private User map(UserDto dto){
        return User.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole(dto.getUserRole())
                .joinedOn(Instant.now())
                .contactNumber(dto.getContactNumber())
                .password(passwordEncoder.encode(dto.getPassword())).build();
    }

//    Method to map domain class to data transfer object
    private UserDto mapDto(User customer){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());

        return new UserDto(customer.getEmail(), customer.getFirstName(), customer.getLastName(), customer.getUserRole(), customer.getContactNumber(), DATE_TIME_FORMATTER.format(customer.getJoinedOn()));
    }


}
