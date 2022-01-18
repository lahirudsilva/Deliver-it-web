package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.enums.ShipmentStatusType;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.Instant;
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
    private final PasswordEncoder passwordEncoder;
    private final ShipmentRepository shipmentRepository;

    @Autowired
    public CustomerService(UserRepository userRepository, PasswordEncoder passwordEncoder, ShipmentRepository shipmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public User registerCustomer(UserDto dto) throws DeliveritException {

        Optional existing = userRepository.findById(dto.getEmail());
        Optional existingContact = userRepository.findUserByContactNumber(dto.getContactNumber());

        if(existing.isPresent()){
            throw new DeliveritException("Email already exists!");
        }else if(existingContact.isPresent()){
            throw new DeliveritException("Contact number already exists!");
        }

        User customer = new User();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customer.setUserRole("customer");
        customer.setCity(dto.getCity());
        customer.setContactNumber(dto.getContactNumber());
        customer.setIsVerified(true);
        customer.setIsBlackListed(false);
        customer.setJoinedOn(Instant.now());

        return userRepository.save(customer);

    }


    @Transactional
    public List<UserDto> getAllCustomers()  throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());



        List<UserDto> list = new ArrayList<>();
        for (User customer : userRepository.findUserByUserRole("customer")){
            UserDto dto= new UserDto();
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setEmail(customer.getEmail());
            dto.setContactNumber("+" +customer.getContactNumber());
            dto.setCity(customer.getCity());
            dto.setJoinedOn(DATE_TIME_FORMATTER.format(customer.getJoinedOn()));
            dto.setVerified(customer.getIsVerified());
            dto.setBlacklisted(customer.getIsBlackListed());
            list.add(dto);
        }
        System.out.println(list);
        return list;
    }

    public List<ShipmentDto> getAllMyPackages() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        User user = userRepository.findUserByEmail(getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));



        List<ShipmentDto>  list = new ArrayList<>();
        for (Shipment shipment : shipmentRepository.findAllByUserIsOrderByCreatedAtDesc(user)){
            ShipmentDto dto = new ShipmentDto();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setEstimatedPrice(shipment.getEstimatedPrice());
            dto.setPickupLocation(shipment.getPickupLocation());
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setReceiverName(shipment.getReceiverName());
            dto.setPickUpDate(shipment.getPickUpDate());
            dto.setDropOffDate(shipment.getDropOffDate());
            dto.setWarehouseLocation(shipment.getWarehouse().getLocation());
            dto.setDescription(shipment.getDescription());
            dto.setSize(shipment.getSize());
            dto.setWeight(shipment.getWeight());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            dto.setStatus(shipment.getStatus());
            list.add(dto);
        }
        return list;


    }

    public List<ShipmentDto> getMyRecentPackages() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        User user = userRepository.findUserByEmail(getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));



        List<ShipmentDto>  list = new ArrayList<>();
        List<ShipmentDto> recent = new ArrayList<>();

        for (Shipment shipment : shipmentRepository.findAllByUserIsOrderByCreatedAtDesc(user)){

            ShipmentDto dto = new ShipmentDto();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            dto.setPickUpDate(shipment.getPickUpDate());
            dto.setDropOffDate(shipment.getDropOffDate());
            dto.setSize(shipment.getSize());
            dto.setWeight(shipment.getWeight());
            dto.setStatus(shipment.getStatus());
            list.add(dto);



        }
        //Find 10 most recent packages
        for(int i=0; i<list.size(); i++) {
            if(i==10) break;
            recent.add(list.get(i));
        }


        return recent;


    }

    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getEmail();
    }

    public UserDto getLoggedInUser() throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        UserDto dto = new UserDto();
        dto.setFirstName(_user.getFirstName());
        dto.setLastName(_user.getLastName());
        dto.setEmail(_user.getEmail());
        dto.setContactNumber(_user.getContactNumber());
        dto.setUserRole(_user.getUserRole());
        dto.setCity(_user.getCity());
        dto.setJoinedOn(DATE_TIME_FORMATTER.format(_user.getJoinedOn()));

        return dto;


    }

    public User setVerified(String email) throws DeliveritException {

        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new DeliveritException("User not found!"));

        user.setIsBlackListed(false);
        return userRepository.save(user);




    }

    public User setBlacklisted(String email) throws DeliveritException {

        User user = userRepository.findUserByEmail(email).orElseThrow(()-> new DeliveritException("User not found!"));
        System.out.println(user);
        user.setIsBlackListed(true);
        return userRepository.save(user);
    }

    public void removeCustomer(String email) throws DeliveritException {
        User customer = userRepository.findUserByEmail(email).orElseThrow(()-> new DeliveritException("User not found"));

        List <Shipment> shipments = shipmentRepository.findAllByUserIsOrderByCreatedAtDesc(customer);

        if(customer.getUserRole().equals("admin")) throw new DeliveritException("You cannot delete an administrator");

        if(shipments.size()>0) throw  new DeliveritException("Deletion failed! customer has on going shipments!");

        userRepository.deleteById(email);
    }
}
