package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.AuthService;
import com.typicalcoderr.Deliverit.Service.ShipmentService;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.dto.AuthResponse;
import com.typicalcoderr.Deliverit.dto.LoginRequest;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
 * Time: 12:27 PM
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class ShipmentServiceTests {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TestUtil testUtil;

    private String warehouseForPackage;
    private LoginRequest loggedUser;
    private Shipment createPackageWithUser;

    @Test
    public void testAddPackageRequest() throws DeliveritException {
        warehouseForPackage = testUtil.createWarehouse();
        loggedUser = testUtil.createUserForLogin();

        ShipmentDto dto = new ShipmentDto();
        dto.setReceiverContactNumber("1232341232");
        dto.setPickupLocation("pickup");
        dto.setDropOffLocation("drop");
        dto.setReceiverEmail("receiver@AddPackage");
        dto.setSize("test");
        dto.setWeight(23.00);
        dto.setEstimatedPrice(100.00);
        dto.setDescription("this is description");
        dto.setWarehouseNumber(warehouseForPackage);
        dto.setReceiverName("receiver");


        AuthResponse request = authService.login(loggedUser);

        Shipment result = shipmentService.addShipment(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a package request [PASSED]");


    }


    @Test
    public void testGetPendingPackageRequests() throws DeliveritException {
//        createPackageWithUser = testUtil.createPackageRequest();
        List<ShipmentDto> results = shipmentService.getAllPendingRequestsForAdmin();

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all pending requests [PASSED]");
    }


}
