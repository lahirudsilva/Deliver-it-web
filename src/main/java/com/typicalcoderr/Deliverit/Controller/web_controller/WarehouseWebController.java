package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.UserService;
import com.typicalcoderr.Deliverit.Service.WarehouseService;
import com.typicalcoderr.Deliverit.domain.User;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.UserDto;
import com.typicalcoderr.Deliverit.dto.WarehouseDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 4:08 PM
 */
@Controller
public class WarehouseWebController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private UserService userService;


    @GetMapping("/warehouses")
    public ModelAndView warehouses() {
        ModelAndView mv = new ModelAndView();
//        User user = new User();
//        mv.addObject("user", user);
        mv.setViewName("warehouses");
        mv.addObject("warehouseList", warehouseService.getAllWarehouses());
        return mv;

    }

    @GetMapping("/warehouses/add-supervisor")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addSupervisorForm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("warehouses", warehouseService.getAllWarehouses());
        mv.setViewName("addSupervisors");
        return mv;
    }

    @PostMapping("/add-supervisor")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView addSupervisor(@RequestParam String firstName, String lastName, String email, String contactNumber,
                                      String warehouseId, String city, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();


        UserDto userDto = new UserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setContactNumber(contactNumber);
        userDto.setPassword(email);
        userDto.setUserRole("supervisor");
        userDto.setWarehouseNumber(warehouseId);
        userDto.setCity(city);
        System.out.println(userDto);

        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Supervisor added successfully!"));
        } catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/warehouses/add-supervisor");
        return mv;


    }

    @GetMapping("/getSupervisors")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAllSupervisor(@RequestParam String warehouseNumber) {
        ModelAndView mv = new ModelAndView();

        WarehouseDto dto = new WarehouseDto();
        dto.setWarehouseNumber(warehouseNumber);
        try {
            mv.addObject("supervisorList", warehouseService.getSupervisorsForWarehouse(dto));


        } catch (DeliveritException e) {
            e.printStackTrace();
        }
        mv.setViewName("supervisorsForWarehouse");
        System.out.println(mv);
        return mv;
    }

    @GetMapping("/getWarehouseDrivers")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView getAllDrivers(@RequestParam String warehouseNumber) {
        ModelAndView mv = new ModelAndView();

        WarehouseDto dto = new WarehouseDto();
        dto.setWarehouseNumber(warehouseNumber);
        try {
            mv.addObject("driversList", warehouseService.getWarehouseDrivers(dto));


        } catch (DeliveritException e) {
            e.printStackTrace();
        }
        mv.setViewName("driversForWarehouse");
        System.out.println(mv);
        return mv;
    }
}
