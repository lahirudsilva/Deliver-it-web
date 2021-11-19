package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.CustomerDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CustomerDto registerCustomer(CustomerDto dto) throws DeliveritException {

        Optional existing = userRepository.findUserByEmail(dto.getEmail());

        if(existing.isPresent()){
            throw new DeliveritException("Email already exists");
        }

        //Save new lecturer after mapping dto to entity class
        User customer = map(dto);
        userRepository.save(customer);
        return dto;

    }

//    Method to map data transfer object to domain class
    private User map(CustomerDto dto){
        return User.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole("customer")
                .joinedOn(Instant.now())
                .contactNumber(dto.getContactNumber())
                .password(passwordEncoder.encode(dto.getPassword())).build();
    }

//    Method to map domain class to data transfer object
    private CustomerDto mapDto(User customer){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());

        return new CustomerDto(customer.getEmail(), customer.getFirstName(), customer.getLastName(), customer.getUserRole(), customer.getContactNumber(), DATE_TIME_FORMATTER.format(customer.getJoinedOn()));
    }


}
