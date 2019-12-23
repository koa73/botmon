package ru.rgs.botmon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rgs.botmon.exception.RestApiException;
import ru.rgs.botmon.service.bot.BotMon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class BotServiceImpl implements BotService {

    @Autowired
    private BotMon bot;

    @Override
    public void sendMessage(String from, String phoneList, String message) throws RestApiException {

        Set<String> phones = new HashSet<>(Arrays.asList(
                phoneList.replaceAll("^,?","").split(",")));

        bot.sendAlert(phones,"@"+from + "\n\n"+message);
    }
}
