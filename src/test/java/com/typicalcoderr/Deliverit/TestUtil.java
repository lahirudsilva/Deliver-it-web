package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.*;
import com.typicalcoderr.Deliverit.domain.DriverDetails;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.domain.Warehouse;
import com.typicalcoderr.Deliverit.dto.*;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sun
 * Time: 7:51 PM
 */
@Service
@AllArgsConstructor
public class TestUtil {

    @Autowired
    private final CustomerService customerService;

    @Autowired
    private final WarehouseService warehouseService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final ShipmentService shipmentService;

    @Autowired
    private final DriverService driverService;

    public String createCustomerToBeDeleted() throws DeliveritException{
        UserDto customerDto = new UserDto();

        customerDto.setEmail("createCustomerToBeDeleted@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("1231231232");
        customerDto.setPassword("test");

        return customerService.registerCustomer(customerDto).getEmail();

    }

    public LoginRequest createUserForLogin() throws DeliveritException {
        UserDto customerDto = new UserDto();
        LoginRequest loginRequest = new LoginRequest();

        customerDto.setEmail("createCustomerToBelogin@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("1231231232");
        customerDto.setPassword("test");

        customerService.registerCustomer(customerDto);

        loginRequest.setEmail("createCustomerToBelogin@email.com");
        loginRequest.setPassword("test");
        return  loginRequest;


    }

    public Shipment createPackageRequest() throws DeliveritException {
        UserDto customerDto = new UserDto();
        LoginRequest loginRequest = new LoginRequest();

        customerDto.setEmail("createUserForLoginViewRequests@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("1231231232");
        customerDto.setPassword("test");

        customerService.registerCustomer(customerDto);

        ShipmentDto dto = new ShipmentDto();
        dto.setReceiverContactNumber("1232341232");
        dto.setPickupLocation("pickup");
        dto.setDropOffLocation("drop");
        dto.setReceiverEmail("receiver@AddPackage");
        dto.setSize("test");
        dto.setWeight(23.00);
        dto.setEstimatedPrice(100.00);
        dto.setDescription("this is description");
        dto.setWarehouseNumber(createWarehouse());
        dto.setReceiverName("receiver");






        loginRequest.setEmail("createUserForLoginViewRequests@email.com");
        loginRequest.setPassword("test");
        authService.login(loginRequest);
        return  shipmentService.addShipment(dto);


    }




    public String createCustomerWithSameEmail() throws DeliveritException{
        UserDto dto = new UserDto();
        dto.setEmail("createCustomerWithSameEmail@email.com");
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setCity("testCity");
        dto.setContactNumber("1231231232");
        dto.setPassword("test");

        return customerService.registerCustomer(dto).getEmail();

    }

    public String  createWarehouse() {
        WarehouseDto dto = new WarehouseDto();
        dto.setWarehouseNumber("TestWarehouse");
        dto.setLocation("testLocation");

        Warehouse result = warehouseService.addWarehouse(dto);

        return result.getWarehouseNumber();
    }

    public String createDriverWithSameID() throws DeliveritException{
        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("createDriverWithSameID@email");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("1231231232");
        userDto.setWarehouseNumber(createWarehouse());
        userDto.setPassword("test");

        driverDetailsDto.setDriverId("createDriverWithSameID");
        driverDetailsDto.setNIC("123345234v");
        driverDetailsDto.setVehicleNumber("VBN-1234");
        driverDetailsDto.setDriverEmail("createDriverWithSameID@email");


        UserDto result = userService.registerUser(userDto);
        return driverService.addDriverDetails(driverDetailsDto).getDriverId();
    }

    public String createDriverWithSameNIC() throws DeliveritException{
        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("createDriverWithSameNIC@email");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("1231231232");
        userDto.setWarehouseNumber(createWarehouse());
        userDto.setPassword("test");

        driverDetailsDto.setDriverId("createDriverWithSameNIC");
        driverDetailsDto.setNIC("111100002v");
        driverDetailsDto.setVehicleNumber("KLU-1234");
        driverDetailsDto.setDriverEmail("createDriverWithSameNIC@email");


        UserDto result = userService.registerUser(userDto);
        return driverService.addDriverDetails(driverDetailsDto).getNIC();
    }

    public String createDriverToBeDeleted() throws DeliveritException{
        UserDto driverDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();

        driverDto.setEmail("createDriverToBeDeleted@email");
        driverDto.setFirstName("First");
        driverDto.setLastName("Last");
        driverDto.setCity("testCity");
        driverDto.setContactNumber("1231231232");
        driverDto.setUserRole("driver");
        driverDto.setWarehouseNumber(createWarehouse());
        driverDto.setPassword("test");

        driverDetailsDto.setDriverId("createDriverToBeDeleted");
        driverDetailsDto.setNIC("193840023v");
        driverDetailsDto.setVehicleNumber("FFU-1234");
        driverDetailsDto.setDriverEmail("createDriverToBeDeleted@email");

         userService.registerUser(driverDto);
        return driverService.addDriverDetails(driverDetailsDto).getDriverId();

    }

}
