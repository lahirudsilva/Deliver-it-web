package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.Repository.WarehouseRepository;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.domain.Warehouse;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.enums.ShipmentStatusType;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private  final WarehouseRepository warehouseRepository;

    @Autowired
    public ShipmentService(UserRepository userRepository, ShipmentRepository shipmentRepository, WarehouseRepository warehouseRepository) {
        this.userRepository = userRepository;
        this.shipmentRepository = shipmentRepository;
        this.warehouseRepository = warehouseRepository;
    }


    public Shipment addShipment(ShipmentDto dto) throws DeliveritException{


        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseNumber()).orElseThrow(()-> new DeliveritException("warehouse not found!"));


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
        shipments.setStatus(ShipmentStatusType.PENDING.getType());
        shipments.setDescription(dto.getDescription());
        shipments.setUser(user);
        shipments.setWarehouse(warehouse);

        return shipmentRepository.save(shipments);



    }




    public List<ShipmentDto> getAllPendingRequests() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy  HH:mm:ss a").withZone(ZoneId.systemDefault());

        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();


        List<ShipmentDto> list = new ArrayList<>();

        for ( Shipment shipment :shipmentRepository.findAllByStatusAndWarehouseWarehouseNumberLikeOrderByCreatedAtDesc(ShipmentStatusType.PENDING.getType(), warehouse)){
            ShipmentDto dto = new ShipmentDto();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setSenderEmail(shipment.getUser().getEmail());
            dto.setReceiverEmail(shipment.getReceiverEmail());
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

        for ( Shipment shipment :shipmentRepository.findAllByStatusIsLikeOrderByCreatedAtDesc(ShipmentStatusType.PENDING.getType())){
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

    public Shipment updateDates(ShipmentDto shipmentDto) throws DeliveritException {



        Shipment shipment = shipmentRepository.findById(shipmentDto.getShipmentId()).orElseThrow(()-> new DeliveritException("Shipment not Found"));
        shipment.setPickUpDate(shipmentDto.getPickUpDate());
        shipment.setDropOffDate(shipmentDto.getDropOffDate());
        shipment.setStatus(ShipmentStatusType.ACCEPTED.getType());

        return shipmentRepository.save(shipment);


    }

    public String getUsername() throws DeliveritException{
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(()-> new DeliveritException("user not found!"));

        return _user.getEmail();
    }

    public Shipment changeShipmentStatusToReject(ShipmentDto dto) throws DeliveritException {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId()).orElseThrow(()-> new DeliveritException("Shipment not Found"));
        shipment.setStatus(ShipmentStatusType.REJECTED.getType());

        return shipmentRepository.save(shipment);
    }


}
