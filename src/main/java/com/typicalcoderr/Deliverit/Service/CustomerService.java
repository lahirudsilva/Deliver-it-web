package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 3:16 PM
 */
@Service
public class CustomerService {

    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;

    @Autowired
    public CustomerService(UserRepository userRepository, ShipmentRepository shipmentRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
    }


    public List<UserDto> getAllCustomers() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());



        List<UserDto> list = new ArrayList<>();
        for (User customer : userRepository.findUserByUserRole("customer")){
            UserDto dto= new UserDto();
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setEmail(customer.getEmail());
            dto.setContactNumber(customer.getContactNumber());
            dto.setJoinedOn(DATE_TIME_FORMATTER.format(customer.getJoinedOn()));
            list.add(dto);
        }
        return list;
    }

    public List<ShipmentDto> getAllMyPackages() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        User user = userRepository.findUserByEmail(getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));



        List<ShipmentDto>  list = new ArrayList<>();
        for (Shipment shipment : shipmentRepository.findAllByUserIsOrderByCreatedAtDesc(user)){
            ShipmentDto dto = new ShipmentDto();
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            dto.setPickUpDate(shipment.getPickUpDate());
            dto.setDropOffDate(shipment.getDropOffDate());
            dto.setSize(shipment.getSize());
            dto.setWeight(shipment.getWeight());
            dto.setStatus(shipment.getStatus());
            list.add(dto);
        }
        return list;


    }

    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getEmail();
    }
}
