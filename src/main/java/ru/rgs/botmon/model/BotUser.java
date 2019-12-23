package ru.rgs.botmon.model;



public class BotUser {

    private Long chatId;
    private Integer stateId;
    private String phone;
    private String name;


    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", stateId=" + stateId +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
