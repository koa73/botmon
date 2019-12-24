package ru.rgs.botmon.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * Created by OAKutsenko on 13.12.2019.
 */

@ControllerAdvice
@Component
public class GlobalExceptionHandler {


    @ExceptionHandler(RestApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String restHandler(final HttpServletRequest request, RestApiException ex, Model model) {

        return ex.toString();
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String exception(final Throwable throwable) {
        final String error = (throwable.getMessage() != null)?throwable.getMessage():"Unknown error";
        return new RestApiException(error, throwable.getClass().getName()).toString();
    }

}
