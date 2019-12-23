package ru.rgs.botmon.repository;

import ru.rgs.botmon.model.BotUser;

import java.util.List;

public interface UsersData {

    BotUser getUser(long chatId);
    Long getChatId(String phone);
    boolean createUser(String phone, String name, long chatid);
    List<BotUser> getAllUsers();
}
