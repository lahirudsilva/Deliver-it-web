package com.typicalcoderr.Deliverit.Repository;

import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.User;
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
    List <DriverDetails> findAllByStatusIsLikeAndUser_WarehouseWarehouseNumberLike(String status, String warehouseNumber);
    List <DriverDetails> findDriverDetailsByUser_Warehouse_WarehouseNumberLike(String warehouseNumber);
//    List <DriverDetails> findAllByStatusIsLikeAndNoOfAssignedRidesLessThanEqualAndUser_WarehouseWarehouseNumberLike(String status, Integer noOfRides, String warehouseNumber);

}
