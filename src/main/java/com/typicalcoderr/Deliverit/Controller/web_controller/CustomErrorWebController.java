package com.typicalcoderr.Deliverit.Controller.web_controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Wed
 * Time: 5:43 PM
 */
@Controller
public class CustomErrorWebController implements ErrorController {

    /* Custom error controller to handle common Http errors */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        System.out.println(url);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            System.out.println(statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/404_error.jsp";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/500_error.jsp";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value() || statusCode == HttpStatus.FORBIDDEN.value()) {
                return "redirect:/";
            }
        }
        return "error";
    }

//    @Override
//    public String getErrorPath() {
//        return null;
//    }
}
