package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.TrackingRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.enums.ShipmentStatusType;
import com.typicalcoderr.Deliverit.enums.TrackingStatusType;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
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
 * Date: Tue
 * Time: 8:03 PM
 */
@Service
@AllArgsConstructor
public class TrackingService {

    DriverDetailsRepository driverDetailsRepository;
    ShipmentRepository shipmentRepository;
    TrackingRepository trackingRepository;
    UserRepository userRepository;


    @Transactional
    public Tracking addTracking(TrackingDto trackingDto) throws DeliveritException {


        DriverDetails driver = driverDetailsRepository.findById(trackingDto.getDriverId()).orElseThrow(() -> new DeliveritException("Driver not Found!"));
        Shipment shipment = shipmentRepository.findById(trackingDto.getShipmentId()).orElseThrow(() -> new DeliveritException("Shipment not found!"));

        Tracking tracking = new Tracking();
        tracking.setShipmentStatus(TrackingStatusType.PICKUP_IN_PROGRESS.getType());
        tracking.setUpdatedAt(Instant.now());
        tracking.setDriverDetails(driver);
        tracking.setShipment(shipment);


        return trackingRepository.save(tracking);


    }

    public Shipment getShipmentById(int id) throws DeliveritException {
        Shipment shipmentObj = shipmentRepository.findById(id).orElseThrow(() -> new DeliveritException("Shipment not found!"));
        return shipmentObj;

    }

    public TrackingDto getTrackingDetails(Integer shipmentId) throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        Shipment shipment = getShipmentById(shipmentId);
        Tracking tracking = trackingRepository.findTrackingsByShipment(shipment);


        TrackingDto dto = new TrackingDto();
        dto.setTrackingId(tracking.getTrackingId());
        dto.setShipmentId(tracking.getShipment().getShipmentId());
        dto.setShipmentStatus(tracking.getShipmentStatus());
        dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
        dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
        dto.setDropOffDate(tracking.getShipment().getDropOffDate());
        dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
        dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
        dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());


        return dto;

    }

    public TrackingDto getShipmentDetails(Integer trackIDKey) throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        Tracking tracking = trackingRepository.findById(trackIDKey).orElseThrow(() -> new DeliveritException("Track Number not Found!"));
        TrackingDto dto = new TrackingDto();
        dto.setShipmentId(tracking.getShipment().getShipmentId());
        dto.setTrackingId(tracking.getTrackingId());
        dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
        dto.setShipmentStatus(tracking.getShipmentStatus());
        dto.setDropOffDate(tracking.getShipment().getDropOffDate());
        dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
        dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
        dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
        dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());
        return dto;


    }

    @Transactional
    public Tracking ToggleStatustoInWarehouse(int shipmentId) throws DeliveritException {

         Tracking tracking =  trackingRepository.findTrackingsByShipment_ShipmentId(shipmentId);


        tracking.setShipmentStatus(TrackingStatusType.IN_WAREHOUSE.getType());
        tracking.setUpdatedAt(Instant.now());
        return trackingRepository.save(tracking);

    }

    @Transactional
    public Tracking ToggleStatustoOutForDelivery(int shipmentId) throws DeliveritException {
        Tracking tracking =  trackingRepository.findTrackingsByShipment_ShipmentId(shipmentId);

        tracking.setShipmentStatus(TrackingStatusType.OUT_FOR_DELIVERY.getType());
        tracking.setUpdatedAt(Instant.now());
        return trackingRepository.save(tracking);

    }

    public Tracking ToggleStatustoDelivered(int shipmentId) throws  DeliveritException{

        Tracking tracking =  trackingRepository.findTrackingsByShipment_ShipmentId(shipmentId);

        tracking.setShipmentStatus(TrackingStatusType.DELIVERED.getType());
        tracking.setUpdatedAt(Instant.now());
        return trackingRepository.save(tracking);
    }

    @Transactional
    public List<TrackingDto> getAllTrackingShipments() throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User customer = userOptional.orElseThrow(() -> new DeliveritException("User not found"));
        String customerId = customer.getEmail();

        List<TrackingDto> list = new ArrayList<>();
        for(Tracking tracking : trackingRepository.findTrackingsByShipmentUserEmailOrderByUpdatedAtDesc(customerId)){
            TrackingDto dto = new TrackingDto();

            dto.setTrackingId(tracking.getTrackingId());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setPick(tracking.getShipment().getPickupLocation());
            dto.setDrop(tracking.getShipment().getDropOffLocation());
            dto.setShipmentStatus(tracking.getShipmentStatus());
            dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
            dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
            dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
            dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());
            dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            list.add(dto);
        }
        return list;

    }

    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));

        return _user.getEmail();
    }

    @Transactional
    public List<TrackingDto> getAllOnGoingTrackingShipments() throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        //get logged in user
        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();

        List<TrackingDto> list = new ArrayList<>();
        for(Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsNotLikeAndShipment_WarehouseWarehouseNumberOrderByUpdatedAtDesc(ShipmentStatusType.PENDING.getType(), warehouse)) {
            TrackingDto dto = new TrackingDto();

            dto.setTrackingId(tracking.getTrackingId());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setPick(tracking.getShipment().getPickupLocation());
            dto.setDrop(tracking.getShipment().getDropOffLocation());
            dto.setShipmentStatus(tracking.getShipmentStatus());
            dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
            dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
            dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
            dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());
            dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            list.add(dto);


        }
        return list;





    }


    public List<TrackingDto> getAllOnGoingShipmentsForAdmin() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        //get logged in user
//        User admin = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
//        String warehouse = supervisor.getWarehouse().getWarehouseNumber();

        List<TrackingDto> list = new ArrayList<>();
        for(Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsNotLikeOrderByUpdatedAtDesc(ShipmentStatusType.PENDING.getType())) {
            TrackingDto dto = new TrackingDto();

            dto.setTrackingId(tracking.getTrackingId());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setPick(tracking.getShipment().getPickupLocation());
            dto.setDrop(tracking.getShipment().getDropOffLocation());
            dto.setShipmentStatus(tracking.getShipmentStatus());
            dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
            dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
            dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
            dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());
            dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            list.add(dto);


        }
        return list;
    }
}
