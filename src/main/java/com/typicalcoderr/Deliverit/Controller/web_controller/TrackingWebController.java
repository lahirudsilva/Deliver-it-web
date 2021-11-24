package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Repository.TrackingRepository;
import com.typicalcoderr.Deliverit.Service.TrackingService;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.dto.TrackingDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Wed
 * Time: 11:20 AM
 */
@Controller
@AllArgsConstructor
public class TrackingWebController {

    private final TrackingService trackingService;

    @GetMapping("/trackPackage")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ModelAndView trackingPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("shipmentTrackingDetails");
        return mv;

    }



    @GetMapping("/track-package")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ModelAndView getTrackingDetails(@RequestParam String shipmentId, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();
        System.out.println(shipmentId + "dssd");

        TrackingDto trackingDto = new TrackingDto();
        trackingDto.setShipmentId(Integer.parseInt(shipmentId));

        try {

            mv.addObject("trackingDetailsList", trackingService.getTrackingDetails(trackingDto));

        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
        }
        mv.setViewName("shipmentTrackingDetails");
        return mv;

    }
}
