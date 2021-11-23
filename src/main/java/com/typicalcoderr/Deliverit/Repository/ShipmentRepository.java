package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,Integer> {
    List<Shipment> findAllByStatusIsLikeOrderByCreatedAtDesc(String status);
    List<Shipment> findAllByUserIsOrderByCreatedAtDesc(User user);
}
