package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,Integer> {
    Tracking findTrackingsByShipment(Shipment shipment);
    List <Tracking> findTrackingsByDriverDetails_DriverId(String driverId);
    List <Tracking> findTrackingsByShipmentStatusIsLikeAndDriverDetails_DriverId(String status, String driverId);
    Tracking findTrackingsByShipment_ShipmentId(Integer shipmentId);
    List <Tracking> findTrackingsByDriverDetails_DriverIdAndShipmentStatusNotLike(String driverId, String shipmentStatus);
    List <Tracking> findTrackingsByShipmentStatusIsLikeAndShipment_UserEmail(String shipmentStatus, String email);
    List <Tracking> findTrackingsByShipmentUserEmailOrderByUpdatedAtDesc(String email );
    List <Tracking> findTrackingsByShipmentStatusIsNotLikeAndShipment_WarehouseWarehouseNumberOrderByUpdatedAtDesc(String shipmentStatus, String warehouseNumber);
    List <Tracking> findTrackingsByShipmentStatusIsNotLikeOrderByUpdatedAtDesc(String shipmentStatus);


}
