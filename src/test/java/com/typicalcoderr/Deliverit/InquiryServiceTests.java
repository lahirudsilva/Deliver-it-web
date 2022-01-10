package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.InquiryService;
import com.typicalcoderr.Deliverit.domain.Inquiry;
import com.typicalcoderr.Deliverit.domain.Shipment;
import com.typicalcoderr.Deliverit.dto.InquiryDto;
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
 * Time: 2:07 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InquiryServiceTests {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private TestUtil testUtil;

    private Shipment createShipmentForInquiry;


    @Test
    public void testAddInquiry() throws DeliveritException {

        createShipmentForInquiry = testUtil.createPackageRequest();

        InquiryDto dto = new InquiryDto();
        dto.setDescription("this is a description");
        dto.setInquiryStatus("pending");
        dto.setShipmentId(createShipmentForInquiry.getShipmentId());


        Inquiry result = inquiryService.addInquiries(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a inquiry [PASSED]");


    }

    @Test
    public void testGetAllInquiriesPendingForAdmin() throws DeliveritException {
        List<InquiryDto> results = inquiryService.getAllInquires();
        System.out.println(results);

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all students [PASSED]");

    }

    @Test
    public void testGetAllInquiriesOfCustomer() throws DeliveritException {
        List<InquiryDto> results = inquiryService.getAllMyInquires();
        System.out.println(results);

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all students [PASSED]");

    }


}
