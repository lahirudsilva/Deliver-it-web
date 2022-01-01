package com.typicalcoderr.Deliverit.Service;

import com.typicalcoderr.Deliverit.Repository.DriverDetailsRepository;
import com.typicalcoderr.Deliverit.Repository.TrackingRepository;
import com.typicalcoderr.Deliverit.Repository.UserRepository;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.enums.DriverStatusType;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private final TrackingRepository trackingRepository;

    @Autowired
    public DriverService(DriverDetailsRepository driverDetailsRepository, UserRepository userRepository, TrackingRepository trackingRepository) {
        this.driverDetailsRepository = driverDetailsRepository;
        this.userRepository = userRepository;
        this.trackingRepository = trackingRepository;
    }


    @Transactional
    public DriverDetails addDriverDetails(DriverDetailsDto dto) throws DeliveritException {


        User driver = userRepository.findUserByEmail(dto.getDriverEmail()).orElseThrow(() -> new DeliveritException("user not found!"));

        DriverDetails driverDetails = new DriverDetails();
        driverDetails.setDriverId(dto.getDriverId());
        driverDetails.setNIC(dto.getNIC());
        driverDetails.setVehicleNumber(dto.getVehicleNumber());
        driverDetails.setStatus(DriverStatusType.AVAILABLE.getType());
        driverDetails.setUser(driver);
        driverDetails.setRegisteredOn(Instant.now());
        driverDetails.setNoOfAssignedRides(0);

        return driverDetailsRepository.save(driverDetails);
    }

    public Boolean isExist(String id, String nicNo, String vehicleNo) throws DeliveritException {
        Optional idExisting = driverDetailsRepository.findByDriverId(id);
        Optional NicExisting = driverDetailsRepository.findByNIC(nicNo);
        Optional vehicleNoExisting = driverDetailsRepository.findByVehicleNumber(vehicleNo);

        if (idExisting.isPresent()) {
            throw new DeliveritException("DriverId already exists");
        } else if (NicExisting.isPresent()) {
            throw new DeliveritException("NIC already exists");
        } else if (vehicleNoExisting.isPresent()) {
            throw new DeliveritException("Vehicle Number already existing");
        } else {
            return false;
        }
    }


    @Transactional
    public List<DriverDetailsDto> getAllDrivers() throws DeliveritException{
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());



        List<DriverDetailsDto> list = new ArrayList<>();
        for (DriverDetails driver : driverDetailsRepository.findAll()) {
            DriverDetailsDto dto = new DriverDetailsDto();
            dto.setDriverEmail(driver.getUser().getEmail());
            dto.setDriverFirstName(driver.getUser().getFirstName());
            dto.setDriverLastName(driver.getUser().getLastName());
            dto.setVehicleNumber(driver.getVehicleNumber());
            dto.setNIC(driver.getNIC());
            dto.setContactNumber(driver.getUser().getContactNumber());
            dto.setDriverId(driver.getDriverId().toUpperCase(Locale.ROOT));
            dto.setStatus(driver.getStatus());
            dto.setRegisteredOn(DATE_TIME_FORMATTER.format(driver.getRegisteredOn()));
            list.add(dto);
        }
        return list;
    }

//    public List<DriverDetailsDto> getAllAvailableDrivers() throws DeliveritException {
//
//        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
//        String warehouse = supervisor.getWarehouse().getWarehouseNumber();
//
//
//        List<DriverDetailsDto> list = new ArrayList<>();
//        for (DriverDetails driver : driverDetailsRepository.findAllByStatusIsLikeAndNoOfAssignedRidesLessThanEqualAndUser_WarehouseWarehouseNumberLike(DriverStatusType.AVAILABLE.getType(), 3,warehouse)) {
//            DriverDetailsDto dto = new DriverDetailsDto();
//            dto.setDriverId(driver.getDriverId());
//            dto.setDriverFirstName(driver.getUser().getFirstName());
//            dto.setDriverLastName(driver.getUser().getLastName());
//            list.add(dto);
//        }
//        return list;
//    }




    @Transactional
    public List<DriverDetailsDto> getAllAvailableDrivers() throws DeliveritException {

        User supervisor = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        String warehouse = supervisor.getWarehouse().getWarehouseNumber();


        List<DriverDetailsDto> list = new ArrayList<>();
        for (DriverDetails driver : driverDetailsRepository.findAllByStatusIsLikeAndUser_WarehouseWarehouseNumberLike(DriverStatusType.AVAILABLE.getType(),warehouse)) {
            DriverDetailsDto dto = new DriverDetailsDto();
            dto.setDriverId(driver.getDriverId());
            dto.setDriverFirstName(driver.getUser().getFirstName());
            dto.setDriverLastName(driver.getUser().getLastName());
            list.add(dto);
        }
        return list;
    }

    @Transactional
    public DriverDetails toggleDriverAvailability(DriverDetailsDto driverDetailsDto) throws DeliveritException {

        DriverDetails driverDetails = driverDetailsRepository.findByDriverId(driverDetailsDto.getDriverId()).orElseThrow(() -> new DeliveritException("driver not found"));
        int num = driverDetails.getNoOfAssignedRides();

        driverDetails.setNoOfAssignedRides(num + 1);
        if (num == 10) {
            driverDetails.setStatus(DriverStatusType.ASSIGN_SLOTS_FULL.getType());
        } else {
            driverDetails.setStatus(DriverStatusType.AVAILABLE.getType());
        }

        return driverDetailsRepository.save(driverDetails);
    }


    public String getUsername() throws DeliveritException {
        //User object from security context holder to obtain current user
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //If customer is not found
        com.typicalcoderr.Deliverit.domain.User _user = userRepository.findUserByEmail(user.getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));

        return _user.getEmail();
    }


    public DriverDetailsDto getDriverDetails() throws DeliveritException {

        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a").withZone(ZoneId.systemDefault());
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        //Find user from database
//        Optional<User> userOptional = userRepository.findById(auth.getName());
//        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));
        User driver = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));


        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);

        DriverDetailsDto dto = new DriverDetailsDto();
        dto.setDriverId(driverDetails.getDriverId());
        dto.setWarehouseLocation(driverDetails.getUser().getWarehouse().getLocation());
        dto.setDriverFirstName(driverDetails.getUser().getFirstName());
        dto.setDriverLastName(driverDetails.getUser().getLastName());
        dto.setStatus(driverDetails.getStatus());
        dto.setDriverEmail(driverDetails.getUser().getEmail());
        dto.setContactNumber(driverDetails.getUser().getContactNumber());
        dto.setVehicleNumber(driverDetails.getVehicleNumber());
        dto.setNIC(driverDetails.getNIC());
        dto.setRegisteredOn(DATE_TIME_FORMATTER.format(driverDetails.getRegisteredOn()));
        dto.setNoOfRidesToGo(driverDetails.getNoOfAssignedRides());
        return dto;


    }

    public DriverDetails updatedriverAvailablity() throws DeliveritException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Find user from database
        Optional<User> userOptional = userRepository.findById(auth.getName());
        User driver = userOptional.orElseThrow(() -> new DeliveritException("User not found"));

//        User driver = userRepository.findUserByEmail(getUsername()).orElseThrow(() -> new DeliveritException("user not found!"));
        DriverDetails driverDetails = driverDetailsRepository.findDriverDetailsByUser(driver);

        int num = driverDetails.getNoOfAssignedRides();
        driverDetails.setNoOfAssignedRides(num - 1);
        driverDetails.setStatus(DriverStatusType.AVAILABLE.getType());

        return driverDetailsRepository.save(driverDetails);

    }

    public void removeDriver(String driverId) throws DeliveritException{
        DriverDetails driverDetails = driverDetailsRepository.findById(driverId).orElseThrow(()-> new DeliveritException("Driver Details not found!"));
//        User user = driverDetails.getUser();

//        Optional driver = userRepository.findUserByEmail(user.getEmail());

        List <Tracking> tracking = trackingRepository.findTrackingsByDriverDetails_DriverId(driverId);

        if(tracking.size()>0) throw new DeliveritException("Deletion failed! driver has been assigned for deliveries");

        driverDetailsRepository.deleteById(driverId);
        userRepository.deleteById(driverDetails.getUser().getEmail());

    }
}
