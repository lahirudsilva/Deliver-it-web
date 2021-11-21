package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 9:22 PM
 */
@Service
@AllArgsConstructor
public class ShipmentService {

    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;

    @Transactional
    public Shipment addShipment(ShipmentDto dto) throws DeliveritException{



        User user = userRepository.findUserByEmail(getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        Shipment shipments = new Shipment();
        shipments.setPickupLocation(dto.getPickupLocation());
        shipments.setDropOffLocation(dto.getDropOffLocation());
        shipments.setReceiverEmail(dto.getReceiverEmail());
        shipments.setContactNumber(dto.getReceiverContactNumber());
        shipments.setSize(dto.getSize());
        shipments.setWeight(dto.getWeight());
        shipments.setEstimatedPrice(dto.getEstimatedPrice());
        shipments.setCreatedAt(Instant.now());
        shipments.setStatus("pending");
        shipments.setUser(user);

        return shipmentRepository.save(shipments);



    }

    public String getUsername() throws DeliveritException{
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getEmail();
    }
}
