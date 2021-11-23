package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.ShipmentRepository;
import com.typicalcoderr.Deliverit.Repository.TrackingRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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


    public Tracking addTracking(TrackingDto trackingDto) throws DeliveritException {


        DriverDetails driver = driverDetailsRepository.findById(trackingDto.getDriverId()).orElseThrow(()-> new DeliveritException("Driver not Found!"));;
        Shipment shipment = shipmentRepository.findById(trackingDto.getShipmentId()).orElseThrow(()-> new DeliveritException("Shipment not found!"));

        Tracking tracking = new Tracking();
        tracking.setShipmentStatus("Driver is on the way to pickup");
        tracking.setUpdatedAt(Instant.now());
        tracking.setDriverDetails(driver);
        tracking.setShipment(shipment);


        return trackingRepository.save(tracking);


    }
}
