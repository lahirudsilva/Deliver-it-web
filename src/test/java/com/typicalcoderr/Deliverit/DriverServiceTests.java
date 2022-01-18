package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Mon
 * Time: 10:39 AM
 */
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class DriverServiceTests {

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUtil testUtil;

    private String warehouseForDriver, driverWithSameID, driverWithSameNIC, driverToBeDeleted, inActiveDriverToBeDeleted;


    @Test
    @Order(1)
    public void testAddDriver() throws DeliveritException {

        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("test@AddDriver.com");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("0777788737");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test1");

        driverDetailsDto.setDriverId("TEST_ID");
        driverDetailsDto.setNIC("098745234v");
        driverDetailsDto.setVehicleNumber("VPO-1234");
        driverDetailsDto.setDriverEmail("test@AddDriver.com");


        UserDto result = userService.registerUser(userDto);
        driverService.isExist(driverDetailsDto.getDriverId(), driverDetailsDto.getNIC(), driverDetailsDto.getVehicleNumber());
        DriverDetails driverDetailsResult = driverService.addDriverDetails(driverDetailsDto);
        assertNotNull(result);
        assertNotNull(driverDetailsResult);

        System.out.println("[TEST] Adding a driver [PASSED]");


    }

    @Test
    @Order(2)
    public void testAddDriverWithExistingDriverID() throws DeliveritException {
        driverWithSameID = testUtil.createDriverWithSameID();
        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("testAddDriverWithExistingID@AddDriver.com");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("0777767737");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test1");

        driverDetailsDto.setDriverId(driverWithSameID);
        driverDetailsDto.setNIC("993345234v");
        driverDetailsDto.setVehicleNumber("WER-1114");
        driverDetailsDto.setDriverEmail("testAddDriverWithExistingID@AddDriver.com");
        System.out.println(driverWithSameID);
        boolean isTrue = false;
        userService.registerUser(userDto);
        try{
            driverService.isExist(driverDetailsDto.getDriverId(), driverDetailsDto.getNIC(), driverDetailsDto.getVehicleNumber());
            driverService.addDriverDetails(driverDetailsDto);
        }catch (DeliveritException e){
            if (e.getMessage().equals("DriverId already exists")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add driver with existing driverID [PASSED]");




    }


    @Test
    @Order(3)
    public void testAddDriverWithExistingNIC() throws DeliveritException {
        driverWithSameNIC = testUtil.createDriverWithSameNIC();
        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("testAddDriverWithExistingNIC@AddDriver.com");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("0777733737");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test1");

        driverDetailsDto.setDriverId("testAddDriverWithExistingNIC_ID");
        driverDetailsDto.setNIC(driverWithSameNIC);
        driverDetailsDto.setVehicleNumber("AKH-1114");
        driverDetailsDto.setDriverEmail("testAddDriverWithExistingNIC@AddDriver.com");

        boolean isTrue = false;
        userService.registerUser(userDto);
        try{
            driverService.isExist(driverDetailsDto.getDriverId(), driverDetailsDto.getNIC(), driverDetailsDto.getVehicleNumber());
            driverService.addDriverDetails(driverDetailsDto);
        }catch (DeliveritException e){
            if (e.getMessage().equals("NIC already exists")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to add driver with existing NIC [PASSED]");




    }

    @Test
    @Order(4)
    public void testGetAllDrivers() throws DeliveritException {
        List<DriverDetailsDto> results = driverService.getAllDrivers();
        System.out.println(results);

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all drivers [PASSED]");

    }

//    @Test
//    @Order(5)
//    @WithMockUser(username = "testAddDriverWithExistingID@AddDriver.com" , password = "test1", roles = "DRIVER")
//    public void TestGetDriverDetails() throws DeliveritException{
//        DriverDetailsDto results = driverService.getDriverDetails();
//        System.out.println(results);
//
//        assertNotNull(results);
//
//        System.out.println("[TEST] Get driver details [PASSED]");
//    }


    @Test
    @Order(6)
    public void testDeleteDriverAssignedForDeliveries() throws DeliveritException {
        driverToBeDeleted = testUtil.createDriverToBeDeleted();
        boolean isTrue = false;
        try {
            driverService.removeDriver(driverToBeDeleted);
        }catch (DeliveritException e){
            if (e.getMessage().equals("Deletion failed! driver has been assigned for deliveries")) isTrue = true;
        }


        List<DriverDetailsDto> results = driverService.getAllDrivers();



        for (DriverDetailsDto dto : results) {
            if (dto.getDriverId() == driverToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to Delete driver who assigned to deliveries [PASSED]");
    }

    @Test
    @Order(7)
    public void testDeleteDriver() throws DeliveritException{
        inActiveDriverToBeDeleted = testUtil.createInActiveDriverToBeDeleted();

        driverService.removeDriver(inActiveDriverToBeDeleted);

        List<DriverDetailsDto> results = driverService.getAllDrivers();

        boolean isTrue = true;

        for (DriverDetailsDto dto : results) {
            if (dto.getDriverId() == inActiveDriverToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete driver [PASSED]");
    }

}
