package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.*;
import com.typicalcoderr.Deliverit.domain.*;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.enums.ShipmentStatusType;
import com.typicalcoderr.Deliverit.enums.TrackingStatusType;
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
 * Date: Sat
 * Time: 9:22 PM
 */
@Service
public class ShipmentService {

    private final UserRepository userRepository;
    private final ShipmentRepository shipmentRepository;
    private final WarehouseRepository warehouseRepository;
    private final DriverDetailsRepository driverDetailsRepository;
    private final TrackingRepository trackingRepository;

    @Autowired
    public ShipmentService(UserRepository userRepository, ShipmentRepository shipmentRepository, WarehouseRepository warehouseRepository, DriverDetailsRepository driverDetailsRepository, TrackingRepository trackingRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.warehouseRepository = warehouseRepository;
        this.driverDetailsRepository = driverDetailsRepository;
        this.trackingRepository = trackingRepository;
    }


    @Transactional
    public Shipment addShipment(ShipmentDto dto) throws DeliveritException {

        Optional existing = userRepository.findUserByEmailAndIsBlackListed(getUsername(), true );
        System.out.println(existing);
        if(existing.isPresent()){
            throw new DeliveritException("You are blacklisted, you cannot create packages. Please contact Administration!");
        }

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseNumber()).orElseThrow(() -> new DeliveritException("warehouse not found!"));



        User user = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));


        Shipment shipments = new Shipment();
        shipments.setPickupLocation(dto.getPickupLocation());
        shipments.setDropOffLocation(dto.getDropOffLocation());
        shipments.setReceiverEmail(dto.getReceiverEmail());
        shipments.setContactNumber(dto.getReceiverContactNumber());
        shipments.setSize(dto.getSize());
        shipments.setWeight(dto.getWeight());
        shipments.setEstimatedPrice(dto.getEstimatedPrice());
        shipments.setCreatedAt(Instant.now());
        shipments.setStatus(ShipmentStatusType.PENDING.getType());
        shipments.setDescription(dto.getDescription());
        shipments.setUser(user);
        shipments.setWarehouse(warehouse);
        shipments.setReceiverName(dto.getReceiverName());

        return shipmentRepository.save(shipments);


    }


    public List<ShipmentDto> getAllPendingRequests() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy  HH:mm:ss a").withZone(ZoneId.systemDefault());

        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();


        List<ShipmentDto> list = new ArrayList<>();

        for (Shipment shipment : shipmentRepository.findAllByStatusAndWarehouseWarehouseNumberLikeOrderByCreatedAtDesc(ShipmentStatusType.PENDING.getType(), warehouse)) {
            ShipmentDto dto = new ShipmentDto();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setSenderEmail(shipment.getUser().getEmail());
            dto.setReceiverEmail(shipment.getReceiverEmail());
            dto.setSenderFirstName(shipment.getUser().getFirstName());
            dto.setSenderLastName(shipment.getUser().getLastName());
            dto.setReceiverName(shipment.getReceiverName());
            dto.setEstimatedPrice(shipment.getEstimatedPrice());
            dto.setReceiverContactNumber(shipment.getContactNumber());
            dto.setPickupLocation(shipment.getPickupLocation());
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setSize(shipment.getSize());
            dto.setWeight(shipment.getWeight());
            dto.setDescription(shipment.getDescription());
            dto.setWarehouseLocation(shipment.getWarehouse().getLocation());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            list.add(dto);
        }
        return list;


    }

    //for admin to get all pending requests
    public List<ShipmentDto> getAllPendingRequestsForAdmin() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy  HH:mm:ss a").withZone(ZoneId.systemDefault());
        List<ShipmentDto> list = new ArrayList<>();

        for (Shipment shipment : shipmentRepository.findAllByStatusIsLikeOrderByCreatedAtDesc(ShipmentStatusType.PENDING.getType())) {
            ShipmentDto dto = new ShipmentDto();
            dto.setSenderEmail(shipment.getUser().getEmail());
            dto.setShipmentId(shipment.getShipmentId());
            dto.setReceiverContactNumber(shipment.getContactNumber());
            dto.setReceiverEmail(shipment.getReceiverEmail());
            dto.setPickupLocation(shipment.getPickupLocation());
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setSize(shipment.getSize());
            dto.setWeight(shipment.getWeight());
            dto.setDescription(shipment.getDescription());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            list.add(dto);
        }
        return list;

    }

    @Transactional
    public Shipment updateDates(ShipmentDto shipmentDto) throws DeliveritException {


        Shipment shipment = shipmentRepository.findById(shipmentDto.getShipmentId()).orElseThrow(() -> new DeliveritException("Shipment not Found"));
        shipment.setPickUpDate(shipmentDto.getPickUpDate());
        shipment.setDropOffDate(shipmentDto.getDropOffDate());
        shipment.setStatus(ShipmentStatusType.ACCEPTED.getType());

        return shipmentRepository.save(shipment);


    }

    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));

        return _user.getEmail();
    }

    public Shipment changeShipmentStatusToReject(ShipmentDto dto) throws DeliveritException {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId()).orElseThrow(() -> new DeliveritException("Shipment not Found"));
        shipment.setStatus(ShipmentStatusType.REJECTED.getType());

        return shipmentRepository.save(shipment);
    }


    @Transactional
    public List<ShipmentDto> getAllShipmentsForDiver() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

//        User driver = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);
        String _driverId = driverDetails.getDriverId();


        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByDriverDetails_DriverIdAndShipmentStatusNotLike(_driverId, TrackingStatusType.DELIVERED.getType())) {
            ShipmentDto dto = new ShipmentDto();


            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setDriverEmail(tracking.getDriverDetails().getUser().getEmail());
            dto.setArrival(DATE_TIME_FORMATTER.format(tracking.getShipment().getDropOffDate()));
            dto.setPickupLocation(tracking.getShipment().getPickupLocation());
            dto.setDropOffLocation(tracking.getShipment().getDropOffLocation());
            dto.setPickUp(DATE_TIME_FORMATTER.format(tracking.getShipment().getPickUpDate()));
            dto.setReceiverContactNumber(tracking.getShipment().getContactNumber());
            dto.setSenderContactNumber(tracking.getShipment().getUser().getContactNumber());
            dto.setSenderFirstName(tracking.getShipment().getUser().getFirstName());
            dto.setSenderLastName(tracking.getShipment().getUser().getLastName());
            dto.setReceiverName(tracking.getShipment().getReceiverName());
            dto.setDescription(tracking.getShipment().getDescription());
            list.add(dto);

        }
        return list;

    }

    public List<ShipmentDto> getAllPickupDeliveries() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);
        String _driverId = driverDetails.getDriverId();

        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsLikeAndDriverDetails_DriverId(TrackingStatusType.PICKUP_IN_PROGRESS.getType(), _driverId)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setSenderFirstName(tracking.getShipment().getUser().getFirstName());
            dto.setSenderLastName(tracking.getShipment().getUser().getLastName());
            dto.setPickupLocation(tracking.getShipment().getPickupLocation());
            dto.setSenderContactNumber(tracking.getShipment().getUser().getContactNumber());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setDescription(tracking.getShipment().getDescription());
            dto.setEstimatedPrice(tracking.getShipment().getEstimatedPrice());
            dto.setPickUp(DATE_TIME_FORMATTER.format(tracking.getShipment().getPickUpDate()));
            list.add(dto);
        }
        return list;


    }

    public List<ShipmentDto> getAllInWarehouseDeliveries() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);
        String _driverId = driverDetails.getDriverId();

        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsLikeAndDriverDetails_DriverId(TrackingStatusType.IN_WAREHOUSE.getType(), _driverId)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setWarehouseLocation(tracking.getShipment().getWarehouse().getLocation());
            dto.setDropOffLocation(tracking.getShipment().getDropOffLocation());
            dto.setDescription(tracking.getShipment().getDescription());
            dto.setEstimatedPrice(tracking.getShipment().getEstimatedPrice());
            dto.setArrival(DATE_TIME_FORMATTER.format(tracking.getShipment().getDropOffDate()));
            list.add(dto);


        }
        return list;
    }

    public List<ShipmentDto> getAllPackagesForDeliveries() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);
        String _driverId = driverDetails.getDriverId();

        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsLikeAndDriverDetails_DriverId(TrackingStatusType.OUT_FOR_DELIVERY.getType(), _driverId)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setReceiverName(tracking.getShipment().getReceiverName());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setReceiverContactNumber(tracking.getShipment().getContactNumber());
            dto.setDropOffLocation(tracking.getShipment().getDropOffLocation());
            dto.setDescription(tracking.getShipment().getDescription());
            dto.setEstimatedPrice(tracking.getShipment().getEstimatedPrice());
            dto.setArrival(DATE_TIME_FORMATTER.format(tracking.getShipment().getDropOffDate()));
            list.add(dto);
        }
        return list;

    }

    public List<ShipmentDto> getPastDeliveries() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);
        String _driverId = driverDetails.getDriverId();

        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsLikeAndDriverDetails_DriverId(TrackingStatusType.DELIVERED.getType(), _driverId)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setSenderFirstName(tracking.getShipment().getUser().getFirstName());
            dto.setSenderLastName(tracking.getShipment().getUser().getLastName());
            dto.setReceiverName(tracking.getShipment().getReceiverName());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setReceiverContactNumber(tracking.getShipment().getContactNumber());
            dto.setDropOffLocation(tracking.getShipment().getDropOffLocation());
            dto.setPickupLocation(tracking.getShipment().getPickupLocation());
            dto.setDescription(tracking.getShipment().getDescription());
            dto.setEstimatedPrice(tracking.getShipment().getEstimatedPrice());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            dto.setWeight(tracking.getShipment().getWeight());
            dto.setSize(tracking.getShipment().getSize());
            dto.setWarehouseLocation(tracking.getShipment().getWarehouse().getLocation());
            list.add(dto);
        }
        return list;


    }

    public List<ShipmentDto> getCustomerRecentShipments() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User customer = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

        List<ShipmentDto> list = new ArrayList<>();
        for (Shipment shipment : shipmentRepository.findAllByUserIsOrderByCreatedAtDesc(customer)) {
            ShipmentDto dto = new ShipmentDto();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setStatus(shipment.getStatus());
            list.add(dto);
        }
        return list;


    }

    public List<ShipmentDto> getCustomerPastShipments() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy").withZone(ZoneId.systemDefault());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User customer = userOptional.orElseThrow(() -> new DeliveritException("User not found"));
        String customerId = customer.getEmail();

        List<ShipmentDto> list = new ArrayList<>();
        for (Tracking tracking : trackingRepository.findTrackingsByShipmentStatusIsLikeAndShipment_UserEmail(TrackingStatusType.DELIVERED.getType(), customerId)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setSenderFirstName(tracking.getShipment().getUser().getFirstName());
            dto.setSenderLastName(tracking.getShipment().getUser().getLastName());
            dto.setReceiverName(tracking.getShipment().getReceiverName());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setReceiverContactNumber(tracking.getShipment().getContactNumber());
            dto.setDropOffLocation(tracking.getShipment().getDropOffLocation());
            dto.setPickupLocation(tracking.getShipment().getPickupLocation());
            dto.setDescription(tracking.getShipment().getDescription());
            dto.setEstimatedPrice(tracking.getShipment().getEstimatedPrice());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            list.add(dto);
        }
        return list;

    }

    public List<ShipmentDto> getAllOnGoingShipments() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        List<ShipmentDto> list = new ArrayList<>();
        for (Shipment shipment : shipmentRepository.findAllByStatusIsLikeOrderByCreatedAtDesc(ShipmentStatusType.ACCEPTED.getType())) {
            ShipmentDto dto = new ShipmentDto();

            dto.setSenderFirstName(shipment.getUser().getFirstName());
            dto.setSenderLastName(shipment.getUser().getLastName());
            dto.setReceiverName(shipment.getReceiverName());
            dto.setShipmentId(shipment.getShipmentId());
            dto.setPickupLocation(shipment.getPickupLocation());
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setDescription(shipment.getDescription());
            dto.setEstimatedPrice(shipment.getEstimatedPrice());
            dto.setStatus(shipment.getStatus());
            dto.setWarehouseLocation(shipment.getWarehouse().getLocation());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            dto.setWeight(shipment.getWeight());
            dto.setSize(shipment.getSize());
            dto.setDescription(shipment.getDescription());
            list.add(dto);
        }
        return list;
    }

    public List<ShipmentDto> getAllOnGoingShipmentsForWarehouse() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

        //get logged in user
        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();

        List<ShipmentDto> list = new ArrayList<>();
        for (Shipment shipment : shipmentRepository.findAllByStatusIsLikeAndWarehouseWarehouseNumberOrderByCreatedAtDesc(ShipmentStatusType.ACCEPTED.getType(), warehouse)) {
            ShipmentDto dto = new ShipmentDto();

            dto.setSenderFirstName(shipment.getUser().getFirstName());
            dto.setSenderLastName(shipment.getUser().getLastName());
            dto.setReceiverName(shipment.getReceiverName());
            dto.setShipmentId(shipment.getShipmentId());
            dto.setPickupLocation(shipment.getPickupLocation());
            dto.setDropOffLocation(shipment.getDropOffLocation());
            dto.setDescription(shipment.getDescription());
            dto.setWeight(shipment.getWeight());
            dto.setSize(shipment.getSize());
            dto.setEstimatedPrice(shipment.getEstimatedPrice());
            dto.setWarehouseLocation(shipment.getWarehouse().getLocation());
            dto.setCreatedAt(DATE_TIME_FORMATTER.format(shipment.getCreatedAt()));
            list.add(dto);
        }
        return list;
    }


    //get warehouse location
    public String getWarehouseLocation() throws DeliveritException {
        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        return supervisor.getWarehouse().getLocation();
    }

    public Shipment cancelPendingPackage(Integer shipmentId) throws DeliveritException{

        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(()->new DeliveritException("Shipment not found!"));
        shipment.setStatus(ShipmentStatusType.CANCELED.getType());

        return shipmentRepository.save(shipment);

    }
}
