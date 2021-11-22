package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,Integer> {
    List<Shipment> findAllByStatusIsLike(String status);
}
