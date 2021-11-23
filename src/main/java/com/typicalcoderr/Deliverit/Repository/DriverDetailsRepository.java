package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.DriverDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 1:00 AM
 */
@Repository
public interface DriverDetailsRepository extends JpaRepository<DriverDetails, String> {
    Optional <DriverDetails> findByDriverId (String driverId);
    Optional <DriverDetails> findByNIC (String NIC);
    Optional <DriverDetails> findByVehicleNumber(String vehicleNumber);
    List <DriverDetails> findAllByStatusIsLike(String status);
}
