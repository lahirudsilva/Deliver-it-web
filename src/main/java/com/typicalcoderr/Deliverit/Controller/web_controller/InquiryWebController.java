package com.typicalcoderr.Deliverit.Controller.web_controller;

import com.typicalcoderr.Deliverit.Service.InquiryService;
import com.typicalcoderr.Deliverit.dto.InquiryDto;
import com.typicalcoderr.Deliverit.dto.SimpleMessageDto;
import com.typicalcoderr.Deliverit.exceptions.APIException;
import com.typicalcoderr.Deliverit.exceptions.DeliveritException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Wed
 * Time: 9:28 PM
 */
@Controller
@AllArgsConstructor
public class InquiryWebController {

    private final InquiryService inquiryService;

    @GetMapping("/myInquires")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ModelAndView myInquires() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("inquires");
        try {
            mv.addObject("inquiryList", inquiryService.getAllMyInquires());
        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @GetMapping("/inquires")
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    public ModelAndView allInquiresForWarehouse() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("inquires");
        try {
            mv.addObject("inquiryList", inquiryService.getAllInquiresForWarehouse());
        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @GetMapping("/all-inquires")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView allInquires() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("inquires");
        try{
            mv.addObject("inquiryList", inquiryService.getAllInquires());

        }catch (DeliveritException e){
            mv.addObject("error", new APIException(e.getMessage()));
        }

        return mv;
    }

    @PostMapping("/makeInquiry")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ModelAndView makeInquiries(@RequestParam String message, String shipmentId, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();
        try {
            InquiryDto inquiryDto = new InquiryDto();
            inquiryDto.setDescription(message);
            inquiryDto.setShipmentId(Integer.parseInt(shipmentId));

            inquiryService.addInquiries(inquiryDto);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Inquiry added Successfully!"));


        }catch (DeliveritException e) {
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }
        mv.setViewName("redirect:/myInquires");
        return mv;

    }

    @PostMapping("/responseInq")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ModelAndView responseInquiries(@RequestParam String response, String inquiryID, RedirectAttributes redirectAttributes){
        ModelAndView mv = new ModelAndView();

        try {
            InquiryDto inquiryDto = new InquiryDto();
            inquiryDto.setInquiryId(Integer.parseInt(inquiryID));
            inquiryDto.setResponse(response);

            inquiryService.addResponse(inquiryDto);
            redirectAttributes.addFlashAttribute("success", new SimpleMessageDto("Response added Successfully!"));
        }catch (DeliveritException e){
            redirectAttributes.addFlashAttribute("error", new APIException(e.getMessage()));
        }

        mv.setViewName("redirect:/inquires");
        return mv;

    }
}
