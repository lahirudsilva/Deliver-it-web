package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking,Integer> {
    Tracking findTrackingsByShipment(Shipment shipment);
}
