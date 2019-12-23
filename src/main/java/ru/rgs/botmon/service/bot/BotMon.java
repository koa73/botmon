package ru.rgs.botmon.service.bot;

import ru.rgs.botmon.exception.RestApiException;

import java.util.Set;

public interface BotMon {

    void sendAlert(Set<String> phone, String text) throws RestApiException;
}
