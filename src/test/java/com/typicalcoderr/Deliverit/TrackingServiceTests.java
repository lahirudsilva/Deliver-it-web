package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.domain.Tracking;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.ShipmentDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
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
 * Time: 2:08 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackingServiceTests {

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private TestUtil testUtil;

    private String assignDriver;
    private Shipment createPackageWithUser;

    @Test
    public void testAddTracking() throws DeliveritException {
        createPackageWithUser = testUtil.createPackageRequest();
        assignDriver = testUtil.createDriverToBeDeleted();

        TrackingDto dto = new TrackingDto();
        dto.setDriverId(assignDriver);
        dto.setShipmentId(createPackageWithUser.getShipmentId());


        Tracking result = trackingService.addTracking(dto);

        assertNotNull(result);

        System.out.println("[TEST] add tacking [PASSED]");


    }

    @Test
    public void testGetAllTracking() throws DeliveritException {

        List<TrackingDto> results = trackingService.getAllTrackingShipments();


        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all pending requests [PASSED]");
    }



}
