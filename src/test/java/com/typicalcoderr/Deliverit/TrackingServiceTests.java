package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.AuthService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.*;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
 * Time: 2:08 PM
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class TrackingServiceTests {

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TestUtil testUtil;

    private String assignDriver ;
    private Shipment createPackageWithUser;
    private LoginRequest loggedInDriver;

    @Test
    @WithMockUser(username = "createUserForViewRequests@email.com", password = "test1", roles = "CUSTOMER" )
    public void testAddTracking() throws DeliveritException {
        createPackageWithUser = testUtil.createPackageRequest();
        assignDriver = testUtil.createDriverForTracking();

        TrackingDto dto = new TrackingDto();
        dto.setDriverId(assignDriver);
        dto.setShipmentId(createPackageWithUser.getShipmentId());


        Tracking result = trackingService.addTracking(dto);

        assertNotNull(result);

        System.out.println("[TEST] add tacking [PASSED]");


    }

    @Test
    @WithMockUser(username = "createUserForViewRequests@email.com", password = "test1", roles = "CUSTOMER")
    public void testGetAllTracking() throws DeliveritException {

        List<TrackingDto> results = trackingService.getAllTrackingShipments();


        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all pending requests [PASSED]");
    }

    @Test
    public void TestToggleStatusToPickup()throws DeliveritException{

    }

//    @Test
//    @WithMockUser(username = "createDriverForTracking@email.com", password = "test1", roles = "DRIVER")
//    public void testGetAllTrackingStatusIsLikePickupInProgress() throws DeliveritException {
//
//
//        UserDto user = userService.getLoggedIn();
//
//        List<ShipmentDto> results = shipmentService.getAllPickupDeliveries();
//
//
//        boolean isTrue = results.size() > 0;
//
//        assertTrue(isTrue);
//
//        System.out.println("[TEST] Get all pick up in progress shipments [PASSED]");
//    }




}
