package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.Model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment,Integer> {
}
