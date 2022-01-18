package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.*;
import com.typicalcoderr.Deliverit.domain.*;
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

    @Autowired
    private final TrackingService trackingService;

    public String createCustomerToBeDeleted() throws DeliveritException{
        UserDto customerDto = new UserDto();

        customerDto.setEmail("createCustomerToBeDeleted@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("0771177737");
        customerDto.setPassword("test1");

        return customerService.registerCustomer(customerDto).getEmail();

    }

    public LoginRequest createUserForLogin() throws DeliveritException {
        UserDto customerDto = new UserDto();
        LoginRequest loginRequest = new LoginRequest();

        customerDto.setEmail("createCustomerToBelogin@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("0777744437");
        customerDto.setPassword("test1");

        customerService.registerCustomer(customerDto);

        loginRequest.setEmail("createCustomerToBelogin@email.com");
        loginRequest.setPassword("test1");
        return  loginRequest;


    }

    public Shipment createPackageRequest() throws DeliveritException {
        UserDto customerDto = new UserDto();

        customerDto.setEmail("createUserForViewRequests@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("0777367737");
        customerDto.setPassword("test1");

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

        return  shipmentService.addShipment(dto);
    }


    public Shipment createPackageRequestForInquiry() throws DeliveritException {
        UserDto customerDto = new UserDto();
        LoginRequest loginRequest = new LoginRequest();

        customerDto.setEmail("createPackageRequestForInquiry@email.com");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("0777977737");
        customerDto.setPassword("test1");

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






        loginRequest.setEmail("createPackageRequestForInquiry@email.com");
        loginRequest.setPassword("test1");
        authService.login(loginRequest);
        return  shipmentService.addShipment(dto);


    }


    public String createCustomerWithSameEmail() throws DeliveritException{
        UserDto dto = new UserDto();
        dto.setEmail("createCustomerWithSameEmail@email.com");
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setCity("testCity");
        dto.setContactNumber("0777755737");
        dto.setPassword("test1");

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
        userDto.setEmail("createDriverWithSameID@email.com");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("0777775537");
        userDto.setWarehouseNumber(createWarehouse());
        userDto.setPassword("test1");

        driverDetailsDto.setDriverId("createDriverWithSameID");
        driverDetailsDto.setNIC("123345234v");
        driverDetailsDto.setVehicleNumber("VBN-1234");
        driverDetailsDto.setDriverEmail("createDriverWithSameID@email.com");


        UserDto result = userService.registerUser(userDto);
        return driverService.addDriverDetails(driverDetailsDto).getDriverId();
    }

    public String createDriverWithSameNIC() throws DeliveritException{
        UserDto userDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
        userDto.setEmail("createDriverWithSameNIC@email.com");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setCity("testCity");
        userDto.setUserRole("driver");
        userDto.setContactNumber("0777637737");
        userDto.setWarehouseNumber(createWarehouse());
        userDto.setPassword("test1");

        driverDetailsDto.setDriverId("createDriverWithSameNIC");
        driverDetailsDto.setNIC("111100002v");
        driverDetailsDto.setVehicleNumber("KLU-1234");
        driverDetailsDto.setDriverEmail("createDriverWithSameNIC@email.com");


        UserDto result = userService.registerUser(userDto);
        return driverService.addDriverDetails(driverDetailsDto).getNIC();
    }

    public String createDriverToBeDeleted() throws DeliveritException{
        UserDto driverDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();

        driverDto.setEmail("createDriverToBeDeleted@email.com");
        driverDto.setFirstName("First");
        driverDto.setLastName("Last");
        driverDto.setCity("testCity");
        driverDto.setContactNumber("0777727737");
        driverDto.setUserRole("driver");
        driverDto.setWarehouseNumber(createWarehouse());
        driverDto.setPassword("test1");

        driverDetailsDto.setDriverId("createDriverToBeDeleted");
        driverDetailsDto.setNIC("193840023v");
        driverDetailsDto.setVehicleNumber("FFU-1234");
        driverDetailsDto.setDriverEmail("createDriverToBeDeleted@email.com");

         userService.registerUser(driverDto);
        return driverService.addDriverDetails(driverDetailsDto).getDriverId();

    }

    public String createInActiveDriverToBeDeleted() throws DeliveritException{
        UserDto driverDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();

        driverDto.setEmail("inActiveDriverToBeDeleted@email.com");
        driverDto.setFirstName("First");
        driverDto.setLastName("Last");
        driverDto.setCity("testCity");
        driverDto.setContactNumber("0777725437");
        driverDto.setUserRole("driver");
        driverDto.setWarehouseNumber(createWarehouse());
        driverDto.setPassword("test1");

        driverDetailsDto.setDriverId("inActiveDriverToBeDeleted");
        driverDetailsDto.setNIC("553840023v");
        driverDetailsDto.setVehicleNumber("LLU-1234");
        driverDetailsDto.setDriverEmail("inActiveDriverToBeDeleted@email.com");

        userService.registerUser(driverDto);
        return driverService.addDriverDetails(driverDetailsDto).getDriverId();

    }

    public String createDriverForTracking() throws DeliveritException{
        UserDto driverDto = new UserDto();
        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();

        driverDto.setEmail("createDriverForTracking@email.com");
        driverDto.setFirstName("First");
        driverDto.setLastName("Last");
        driverDto.setCity("testCity");
        driverDto.setContactNumber("0777887737");
        driverDto.setUserRole("driver");
        driverDto.setWarehouseNumber(createWarehouse());
        driverDto.setPassword("test1");

        driverDetailsDto.setDriverId("createDriverToBeDeleted");
        driverDetailsDto.setNIC("193840023v");
        driverDetailsDto.setVehicleNumber("FFU-1234");
        driverDetailsDto.setDriverEmail("createDriverForTracking@email.com");

        userService.registerUser(driverDto);

        return driverService.addDriverDetails(driverDetailsDto).getDriverId();

    }

//    public LoginRequest createLoggedInDriver() throws DeliveritException {
//        LoginRequest loginRequest = new LoginRequest();
//        UserDto driverDto = new UserDto();
//        DriverDetailsDto driverDetailsDto = new DriverDetailsDto();
//
//        driverDto.setEmail("createLoggedInDriver@email");
//        driverDto.setFirstName("First");
//        driverDto.setLastName("Last");
//        driverDto.setCity("testCity");
//        driverDto.setContactNumber("3537869632");
//        driverDto.setUserRole("driver");
//        driverDto.setWarehouseNumber(createWarehouse());
//        driverDto.setPassword("test");
//
//        driverDetailsDto.setDriverId("createLoggedInDriver");
//        driverDetailsDto.setNIC("193840223v");
//        driverDetailsDto.setVehicleNumber("OOP-1234");
//        driverDetailsDto.setDriverEmail("createLoggedInDriver@email");
//
//        userService.registerUser(driverDto);
//      String driverId =    driverService.addDriverDetails(driverDetailsDto).getDriverId();
//
//        TrackingDto dto = new TrackingDto();
//        dto.setDriverId(driverId);
//        dto.setShipmentId(createPackageRequest().getShipmentId());
//
//
//        Tracking result = trackingService.addTracking(dto);
//
//        loginRequest.setEmail("createLoggedInDriver@email");
//        loginRequest.setPassword("test");
//        return  loginRequest;
//
//
//    }

}
