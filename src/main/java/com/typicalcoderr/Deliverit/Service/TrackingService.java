package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.TrackingRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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


    public Tracking addTracking(TrackingDto trackingDto) throws DeliveritException {


        DriverDetails driver = driverDetailsRepository.findById(trackingDto.getDriverId()).orElseThrow(()-> new DeliveritException("Driver not Found!"));
        Shipment shipment = shipmentRepository.findById(trackingDto.getShipmentId()).orElseThrow(()-> new DeliveritException("Shipment not found!"));

        Tracking tracking = new Tracking();
        tracking.setShipmentStatus("Driver is on the way to pickup");
        tracking.setUpdatedAt(Instant.now());
        tracking.setDriverDetails(driver);
        tracking.setShipment(shipment);


        return trackingRepository.save(tracking);


    }

    public Shipment getShipmentById(int id) throws DeliveritException{
        Shipment shipmentObj = shipmentRepository.findById(id).orElseThrow(()-> new DeliveritException("Shipment not found!"));
        return shipmentObj;

    }

    public List<TrackingDto> getTrackingDetails(TrackingDto trackingDto) throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());

//        Shipment shipment = trackingRepository.findTrackingsByShipment(trackingDto.getShipmentId()).orElseThrow(()->new DeliveritException("Shipment not found!"));
        Shipment shipment = getShipmentById(trackingDto.getShipmentId());

        List<TrackingDto> list = new ArrayList<>();
        for (Tracking tracking: trackingRepository.findTrackingsByShipment(shipment)){
            TrackingDto dto = new TrackingDto();
            dto.setTrackingId(tracking.getTrackingId());
            dto.setShipmentId(tracking.getShipment().getShipmentId());
            dto.setShipmentStatus(tracking.getShipmentStatus());
            dto.setUpdatedAt(DATE_TIME_FORMATTER.format(tracking.getUpdatedAt()));
            dto.setDropOffDate(tracking.getShipment().getDropOffDate());
            dto.setDriverContactNumber(tracking.getDriverDetails().getUser().getContactNumber());
            dto.setDriverFirstName(tracking.getDriverDetails().getUser().getFirstName());
            dto.setDriverLastName(tracking.getDriverDetails().getUser().getLastName());
            dto.setDriverVehicleNumber(tracking.getDriverDetails().getVehicleNumber());
            list.add(dto);
        }
        return list;

    }
}
