package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.Repository.WarehouseRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.domain.Warehouse;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.WarehouseDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sat
 * Time: 1:31 AM
 */
@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final DriverDetailsRepository driverDetailsRepository;

    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository, UserRepository userRepository, DriverDetailsRepository driverDetailsRepository) {
        this.warehouseRepository = warehouseRepository;
        this.userRepository = userRepository;
        this.driverDetailsRepository = driverDetailsRepository;
    }

    public List<WarehouseDto> getAllWarehouses() {

        List<WarehouseDto> list = new ArrayList<>();
        for (Warehouse warehouse : warehouseRepository.findAll()) {
            WarehouseDto dto = new WarehouseDto();
            dto.setWarehouseNumber(warehouse.getWarehouseNumber());
            dto.setLocation(warehouse.getLocation());
            list.add(dto);

        }
        return list;

    }


    public List<DriverDetailsDto> getAllDriversForWareHouse() throws DeliveritException {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());
        //get logged in user
        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        System.out.println("test 1 "+supervisor);
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();
        System.out.println("test 2 "+ warehouse);
//        User drivers = userRepository.findUserByUserRoleAndAndWarehouseIsLike("driver", warehouse);
//        System.out.println("test 3"+ drivers);


        List<DriverDetailsDto> list = new ArrayList<>();
        for (DriverDetails driver : driverDetailsRepository.findDriverDetailsByUser_Warehouse_WarehouseNumberLike(warehouse)) {

            DriverDetailsDto dto = new DriverDetailsDto();
            dto.setDriverFirstName(driver.getUser().getFirstName());
            dto.setDriverLastName(driver.getUser().getLastName());
            dto.setDriverEmail(driver.getUser().getEmail());
            dto.setNIC(driver.getNIC());
            dto.setVehicleNumber(driver.getVehicleNumber());
            dto.setContactNumber(driver.getUser().getContactNumber());
            dto.setDriverId(driver.getDriverId().toUpperCase(Locale.ROOT));
            dto.setStatus(driver.getStatus());
            dto.setWarehouseLocation(supervisor.getWarehouse().getLocation());
            dto.setRegisteredOn(DATE_TIME_FORMATTER.format(driver.getRegisteredOn()));
            list.add(dto);
        }
        return list;
    }

    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));

        return _user.getEmail();
    }

    //get warehouse location
    public String getWarehouseLocation() throws DeliveritException{
        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        return supervisor.getWarehouse().getLocation();
    }
}
