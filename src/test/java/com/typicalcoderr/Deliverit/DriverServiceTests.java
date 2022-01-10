package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.DriverService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.DriverDetailsDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest
public class DriverServiceTests {

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUtil testUtil;

    private String warehouseForDriver, driverWithSameID, driverWithSameNIC, driverToBeDeleted;


    @Test
    public void testAddDriver() throws DeliveritException {

        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("test@AddDriver");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("1231231232");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test");

        driverDetailsDto.setDriverId("TEST_ID");
        driverDetailsDto.setNIC("098745234v");
        driverDetailsDto.setVehicleNumber("VPO-1234");
        driverDetailsDto.setDriverEmail("test@AddDriver");


        UserDto result = userService.registerUser(userDto);
        driverService.isExist(driverDetailsDto.getDriverId(), driverDetailsDto.getNIC(), driverDetailsDto.getVehicleNumber());
        DriverDetails driverDetailsResult = driverService.addDriverDetails(driverDetailsDto);
        assertNotNull(result);
        assertNotNull(driverDetailsResult);

        System.out.println("[TEST] Adding a driver [PASSED]");


    }

    @Test
    public void testAddDriverWithExistingDriverID() throws DeliveritException {
        driverWithSameID = testUtil.createDriverWithSameID();
        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("testAddDriverWithExistingID@AddDriver");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("1231231232");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test");

        driverDetailsDto.setDriverId(driverWithSameID);
        driverDetailsDto.setNIC("993345234v");
        driverDetailsDto.setVehicleNumber("WER-1114");
        driverDetailsDto.setDriverEmail("testAddDriverWithExistingID@AddDriver");
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
    public void testAddDriverWithExistingNIC() throws DeliveritException {
        driverWithSameNIC = testUtil.createDriverWithSameNIC();
        warehouseForDriver = testUtil.createWarehouse();

        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("testAddDriverWithExistingNIC@AddDriver");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("1231231232");
        userDto.setWarehouseNumber(warehouseForDriver);
        userDto.setPassword("test");

        driverDetailsDto.setDriverId("testAddDriverWithExistingNIC_ID");
        driverDetailsDto.setNIC(driverWithSameNIC);
        driverDetailsDto.setVehicleNumber("AKH-1114");
        driverDetailsDto.setDriverEmail("testAddDriverWithExistingNIC@AddDriver");

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
    public void testGetAllDrivers() throws DeliveritException {
        List<DriverDetailsDto> results = driverService.getAllDrivers();
        System.out.println(results);

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all drivers [PASSED]");

    }




    @Test
    public void testDeleteCustomer() throws DeliveritException {
        driverToBeDeleted = testUtil.createDriverToBeDeleted();

        driverService.removeDriver(driverToBeDeleted);

        List<DriverDetailsDto> results = driverService.getAllDrivers();

        boolean isTrue = true;

        for (DriverDetailsDto dto : results) {
            if (dto.getDriverId() == driverToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete driver [PASSED]");
    }



}
