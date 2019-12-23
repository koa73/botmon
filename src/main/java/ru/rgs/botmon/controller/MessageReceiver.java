package ru.rgs.botmon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rgs.botmon.exception.RestApiException;
import ru.rgs.botmon.service.BotServiceImpl;
import ru.rgs.botmon.validation.Key;
import ru.rgs.botmon.validation.Message;
import ru.rgs.botmon.validation.Phone;

import javax.validation.constraints.Pattern;


@RestController
@Validated
@RequestMapping(path = "/rest")
public class MessageReceiver {

    private final Logger log = LoggerFactory.getLogger(getClass());

   @Autowired
   private BotServiceImpl service;


    /* Echo test */
    @RequestMapping(path = "/test/{id}", method = RequestMethod.GET)
    public String testPoint (@Pattern(regexp = "\\d+",message="Invalid request value ID. " +
            "Send integer value.") @PathVariable String id) {


        return id;
    }

    @RequestMapping(path = "/send")
    public String getMessage(
            @Key @RequestParam(value = "key") String key,
            @Message(max = 15) @RequestParam(value = "from") String from,
            @Phone @RequestParam(value = "phone") String phone,
            @Message(max = 607) @RequestParam(value = "message") String message ) throws RestApiException {

        service.sendMessage(from, phone, message);
        return "Ok";
    }
}
