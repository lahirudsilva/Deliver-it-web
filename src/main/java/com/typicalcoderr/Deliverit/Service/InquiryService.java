package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.InquiryRepository;
import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.Inquiry;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.InquiryDto;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.enums.ShipmentStatusType;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * Date: Wed
 * Time: 10:00 PM
 */
@Service
public class InquiryService {


    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;
    private final InquiryRepository inquiryRepository;

    @Autowired
    public InquiryService(UserRepository userRepository, ShipmentRepository shipmentRepository, InquiryRepository inquiryRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.inquiryRepository = inquiryRepository;
    }


//    public String getUsername() throws DeliveritException {
//        //User object from security context holder to obtain current user
//        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //If customer is not found
//        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
//
//        return _user.getEmail();
//    }
DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

    @Transactional
    public Inquiry addInquiries(InquiryDto inquiryDto) throws DeliveritException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User customer = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        Optional<Shipment> shipmentOptional = shipmentRepository.findById(inquiryDto.getShipmentId());
        Shipment shipment = shipmentOptional.orElseThrow(() -> new  DeliveritException("Shipment not found"));

        Inquiry inquiry = new Inquiry();
        inquiry.setDescription(inquiryDto.getDescription());
        inquiry.setUser(customer);
        inquiry.setShipment(shipment);
        inquiry.setInquiryStatus("pending");
        inquiry.setCreatedAt(Instant.now());


        return inquiryRepository.save(inquiry);

    }

    @Transactional
    public List<InquiryDto> getAllMyInquires() throws DeliveritException{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User customer = userOptional.orElseThrow(() -> new DeliveritException("User not found"));
        String userID = customer.getEmail();

        List<InquiryDto> list = new ArrayList<>();
        for (Inquiry inquiry : inquiryRepository.findInquiriesByUser_EmailOrderByCreatedAtDesc(userID)) {
            InquiryDto dto = new InquiryDto();

            dto.setShipmentId(inquiry.getShipment().getShipmentId());
            dto.setDescription(inquiry.getDescription());
            dto.setInquiryId(inquiry.getInquiryId());
            dto.setResponse(inquiry.getResponse());
            dto.setInquiryStatus(inquiry.getInquiryStatus());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(inquiry.getCreatedAt()));
            list.add(dto);
        }
        return list;
    }

    @Transactional
    public List<InquiryDto> getAllInquiresForWarehouse() throws DeliveritException{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User supervisor = userOptional.orElseThrow(() -> new DeliveritException("User not found"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();

        List<InquiryDto> list = new ArrayList<>();
        for (Inquiry inquiry : inquiryRepository.findInquiriesByInquiryStatusIsLikeAndShipmentWarehouseWarehouseNumberOrderByCreatedAtDesc("pending",warehouse)) {
            InquiryDto dto = new InquiryDto();

            dto.setShipmentId(inquiry.getShipment().getShipmentId());
            dto.setUserId(inquiry.getUser().getEmail());
            dto.setDescription(inquiry.getDescription());
            dto.setInquiryId(inquiry.getInquiryId());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(inquiry.getCreatedAt()));
            list.add(dto);
        }
        return list;


    }

    @Transactional
    public Inquiry addResponse(InquiryDto inquiryDto) throws DeliveritException {

        Inquiry inquiry = inquiryRepository.findById(inquiryDto.getInquiryId()).orElseThrow(()-> new DeliveritException("Inquiry not found!"));

        inquiry.setInquiryStatus("responded");
        inquiry.setResponse(inquiryDto.getResponse());

        return inquiryRepository.save(inquiry);
    }

    @Transactional
    public List<InquiryDto> getAllInquires() throws DeliveritException{
        List<InquiryDto> list = new ArrayList<>();
        for (Inquiry inquiry : inquiryRepository.findInquiriesByInquiryStatusIsLikeOrderByCreatedAtDesc("pending")) {
            InquiryDto dto = new InquiryDto();

            dto.setShipmentId(inquiry.getShipment().getShipmentId());
            dto.setUserId(inquiry.getUser().getEmail());
            dto.setDescription(inquiry.getDescription());
            dto.setInquiryId(inquiry.getInquiryId());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(inquiry.getCreatedAt()));
            list.add(dto);
        }
        return list;

    }

//    public List<InquiryDto> getAllInquires() {
//
//    }
}
