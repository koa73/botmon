package ru.rgs.botmon.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rgs.botmon.config.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;

/**
 *
 * Created by OAKutsenko on 13.12.2019.
 */

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    Messages messages;


//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String validationHandler( ConstraintViolationException ex, Model model) {
//
//        model.addAttribute("errorMsg", "Request validation error.");
//        return "error";
//    }


    @ExceptionHandler(WebApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String webHandler(WebApiException ex, Model model) {

        model.addAttribute("errorMsg", ex.getMessage());
        return "error";
    }


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

    // ???
    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }

}
