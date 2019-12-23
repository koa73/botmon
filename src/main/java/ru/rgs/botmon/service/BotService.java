package ru.rgs.botmon.service;

import ru.rgs.botmon.exception.RestApiException;

interface BotService {

    void sendMessage(String from, String phone, String message) throws RestApiException;

}
