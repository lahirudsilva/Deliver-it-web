package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Tue
 * Time: 12:51 AM
 */
@Service
public class DriverService {


    private final DriverDetailsRepository driverDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public DriverService(DriverDetailsRepository driverDetailsRepository, UserRepository userRepository) {
        this.driverDetailsRepository = driverDetailsRepository;
        this.userRepository = userRepository;
    }



    @Transactional
    public DriverDetails addDriverDetails(DriverDetailsDto dto) throws DeliveritException {



       User driver = userRepository.findUserByEmail(dto.getDriverEmail()).orElseThrow(()-> new DeliveritException("user not found!"));

        DriverDetails driverDetails = new DriverDetails();
        driverDetails.setDriverId(dto.getDriverId());
        driverDetails.setNIC(dto.getNIC());
        driverDetails.setVehicleNumber(dto.getVehicleNumber());
        driverDetails.setStatus("available");
        driverDetails.setUser(driver);
        driverDetails.setRegisteredOn(Instant.now());

        return driverDetailsRepository.save(driverDetails);
    }

    public Boolean isExist (String id, String nicNo, String vehicleNo ) throws DeliveritException{
        Optional idExisting = driverDetailsRepository.findByDriverId(id);
        Optional NicExisting = driverDetailsRepository.findByNIC(nicNo);
        Optional vehicleNoExisting = driverDetailsRepository.findByVehicleNumber(vehicleNo);

        if(idExisting.isPresent()){
            throw new DeliveritException("DriverId already exists");
        }else if(NicExisting.isPresent()){
            throw new DeliveritException("NIC already exists");
        }else if(vehicleNoExisting.isPresent()){
            throw new DeliveritException("Vehicle Number already existing");
        }else{
            return false;
        }
    }


    public List<DriverDetailsDto> getAllDrivers() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());
        
        List<DriverDetailsDto> list = new ArrayList<>();
        for (DriverDetails driver : driverDetailsRepository.findAll()){
            DriverDetailsDto dto =new DriverDetailsDto();
            dto.setDriverFirstName(driver.getUser().getFirstName());
            dto.setDriverLastName(driver.getUser().getLastName());
            dto.setDriverEmail(driver.getUser().getEmail());
            dto.setNIC(driver.getNIC());
            dto.setVehicleNumber(driver.getVehicleNumber());
            dto.setContactNumber(driver.getUser().getContactNumber());
            dto.setDriverId(driver.getDriverId());
            dto.setStatus(driver.getStatus());
            dto.setRegisteredOn(DATE_TIME_FORMATTER.format(driver.getRegisteredOn()));
            list.add(dto);
        }
        return list;
    }

    public List<DriverDetailsDto> getAllAvailableDrivers() {
        List<DriverDetailsDto> list = new ArrayList<>();
        for (DriverDetails driver : driverDetailsRepository.findAllByStatusIsLike("available")){
            DriverDetailsDto dto = new DriverDetailsDto();
            dto.setDriverId(driver.getDriverId());
            dto.setDriverFirstName(driver.getUser().getFirstName());
            dto.setDriverLastName(driver.getUser().getLastName());
            list.add(dto);
        }
        return list;
    }

    public DriverDetails toggleDriverAvailability(DriverDetailsDto driverDetailsDto) throws DeliveritException {

        DriverDetails driverDetails = driverDetailsRepository.findByDriverId(driverDetailsDto.getDriverId()).orElseThrow(()-> new DeliveritException("driver not found"));

        driverDetails.setStatus("unavailable");

        return driverDetailsRepository.save(driverDetails);
    }
}
