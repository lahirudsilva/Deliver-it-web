package com.typicalcoderr.Deliverit;

import com.typicalcoderr.Deliverit.Service.CustomerService;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.validation.Valid;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Sun
 * Time: 7:48 PM
 */


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTests{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TestUtil testUtil;



    private String customerToBeDeleted, customerWithSameEmail;

//    @BeforeAll
//    public void init() throws DeliveritException{
//        System.out.println("hrllo");
//        customerToBeDeleted = testUtil.createCustomerToBeDeleted();
//        customerWithSameEmail = testUtil.createCustomerWithSameEmail();
//        System.out.println("sd"+customerWithSameEmail);
//    }





    @Test
    public void testRegisterCustomer() throws DeliveritException{
        UserDto dto = new UserDto();
        dto.setEmail("test@AddCustomer.com");
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setCity("testCity");
        dto.setContactNumber("0777777737");
        dto.setPassword("Tesstsssss");


        User result = customerService.registerCustomer(dto);

        assertNotNull(result);

        System.out.println("[TEST] Adding a customer [PASSED]");


    }

    @Test
    public void testAddCustomerWithExistingEmail() throws DeliveritException {

        customerWithSameEmail = testUtil.createCustomerWithSameEmail();

//        customerWithSameEmail = testUtil.createCustomerWithSameEmail();
//        System.out.println(customerWithSameEmail);
        UserDto customerDto = new UserDto();
        customerDto.setEmail(customerWithSameEmail);
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        customerDto.setCity("testCity");
        customerDto.setContactNumber("0777737737");
        customerDto.setPassword("tests");

        boolean isTrue = false;

        try {
            customerService.registerCustomer(customerDto);

        } catch (DeliveritException e) {
            if (e.getMessage().equals("Email already exists!")) isTrue = true;
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Attempt to register customer with existing email [PASSED]");

    }

    @Test
    public void testGetAllCustomers() throws DeliveritException {
        List<UserDto> results = customerService.getAllCustomers();
        System.out.println(results);

        boolean isTrue = results.size() > 0;

        assertTrue(isTrue);

        System.out.println("[TEST] Get all customers [PASSED]");

    }

    @Test
    public void testDeleteCustomer() throws DeliveritException {
        customerToBeDeleted = testUtil.createCustomerToBeDeleted();

        customerService.removeCustomer(customerToBeDeleted);

        List<UserDto> results = customerService.getAllCustomers();

        boolean isTrue = true;

        for (UserDto dto : results) {
            if (dto.getEmail() == customerToBeDeleted) {
                isTrue = false;
                break;
            }
        }

        assertTrue(isTrue);

        System.out.println("[TEST] Delete Customer [PASSED]");
    }

}
