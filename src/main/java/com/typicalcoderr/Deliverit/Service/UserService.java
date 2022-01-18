package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.Repository.WarehouseRepository;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.domain.Warehouse;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, WarehouseRepository warehouseRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public UserDto registerUser(UserDto dto) throws DeliveritException {

        System.out.println(dto);
        Optional existing = userRepository.findUserByEmail(dto.getEmail());
        Optional existingContact = userRepository.findUserByContactNumber(dto.getContactNumber());

        if(existing.isPresent()){
            throw new DeliveritException("Email already exists");
        }else if(existingContact.isPresent()){
            throw new DeliveritException("Contact number already exists");
        }

        Warehouse _warehouse = warehouseRepository.findById(dto.getWarehouseNumber()).orElseThrow(()-> new DeliveritException("warehouseID not found!"));


        //Save new user after mapping dto to entity class
        User customer = map(dto, _warehouse);

        dto.setWarehouse(_warehouse);

        userRepository.save(customer);
        return dto;

    }

    @Transactional
    public UserDto getUserDetais() throws DeliveritException{


        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If student is not found
        com.typicalcoderr.Deliverit.domain.User user = userRepository.findById(loggedUser.getUsername()).orElseThrow(()->new DeliveritException("Student not found"));





       UserDto dto = mapDto(user);
        System.out.println(dto);


        return dto;




    }




    public String getName() throws DeliveritException{
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getFirstName();
    }

    @Transactional
    public UserDto getLoggedIn() throws DeliveritException{
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));
        System.out.println(_user);
        UserDto userDto = mapDto(_user);
        return userDto;

    }





//    Method to map data transfer object to domain class
    private User map(UserDto dto, Warehouse _warehouse){
        return User.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userRole(dto.getUserRole())
                .joinedOn(Instant.now())
                .city(dto.getCity())
                .contactNumber(dto.getContactNumber())
                .isVerified(true)
                .isBlackListed(false)
                .warehouse(_warehouse)
                .password(passwordEncoder.encode(dto.getPassword())).build();

    }

//    Method to map domain class to data transfer object
    private UserDto mapDto(User customer){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.systemDefault());

        return new UserDto(customer.getEmail(),customer.getFirstName(), customer.getLastName(),customer.getContactNumber(),
                customer.getUserRole(),DATE_TIME_FORMATTER.format(customer.getJoinedOn()),  customer.getCity(), customer.getIsVerified(), customer.getIsBlackListed());
    }



}
