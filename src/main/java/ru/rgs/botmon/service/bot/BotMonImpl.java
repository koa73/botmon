package ru.rgs.botmon.service.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.rgs.botmon.exception.RestApiException;
import ru.rgs.botmon.model.BotUser;
import ru.rgs.botmon.repository.UsersData;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BotMonImpl extends TelegramLongPollingBot implements BotMon {

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.user}")
    private String botUser;

    @Value("${bot.password}")
    private String password;

    @Autowired
    @Qualifier("usersData")
    private UsersData repository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void sendAlert(Set<String> phoneList, String text) throws RestApiException {

        Set<String> unknownPhones = new HashSet<>(phoneList);

        for (String phone : phoneList) {

            Long chatId = repository.getChatId(phone.substring(1));

            if (chatId != null) {
                try {

                    sendMessage(chatId, text);
                    unknownPhones.remove(phone);

                } catch (TelegramApiException tae) {
                    throw new RestApiException("Message wasn't sent to user(s) " + unknownPhones, tae.getMessage());
                }
            }
        }
        if (!unknownPhones.isEmpty())
           throw new RestApiException("User(s) "+unknownPhones+" not found.", this.getClass().getName());
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(!update.hasMessage())
            return;

        final Message message = update.getMessage();
        final  Long chatId = update.getMessage().getChatId();
        final  String text = update.getMessage().getText();

        try {

            if (message.hasText()) {                            // Processing input messages

                BotUser user = repository.getUser(chatId);

                switch (text) {

                    case "/start":

                        if (user != null)
                            sendMessage(chatId, user.getName() + " you are registered in this service.");
                        else
                            sendMessage(chatId, "You are not registered at this service. Enter password for start " +
                                    "registration :");
                        break;

                    case "/help":
                        sendMessage(chatId, "For subscribing on this service press /start\n " +
                                "For list all members press /list");

                        break;

                    case "/list":
                        if (user != null && user.getStateId() > 0) {
                            sendMessage(chatId, showUsers(repository.getAllUsers()));

                        } else {
                            sendMessage(chatId, "You are not registered at this service. Enter password for start " +
                                    "registration :");
                        }
                        break;

                    default:
                        if (user == null && text.matches(password)) {
                            getContacts(chatId);
                        }
                        else if (user != null && text.matches("^@.*")) {
                            sendPersonMessage(text, user);
                        }
                        break;

                }

            } else if (message.getContact() != null) {          // Processing user contact

                Contact contact = message.getContact();

                if (repository.createUser(
                        contact.getPhoneNumber().replaceAll("(.*)?(\\d{10})$", "$2"),
                        (contact.getLastName() == null) ? contact.getFirstName()
                                : contact.getFirstName() + " " + contact.getLastName(),
                        contact.getUserID()
                )) {

                    sendMessage(chatId, "Congratulation ! You are registered user now.\n " +
                            "You can get alarms if sysadmins will send it to your phone number.\n " +
                            "Recommend you to test it.");
                } else {
                    sendMessage(chatId, "Sorry, during registration has fault.\n Try again or ask our sysadmins.");
                }
            }
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return botUser;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    private void sendMessage(long chatId, String text) throws TelegramApiException {

        text = text.replaceAll("[\\\\~*'_/]", " ");
        SendMessage message = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText(text);

        execute(message);
    }

    private void getContacts(long chatId) throws TelegramApiException {

        SendMessage message = new SendMessage()
                .enableMarkdown(true)
                .setChatId(chatId)
                .setText("Send your phone number for complete registration.\n" +
                        "Press button in the bottom. ");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Send your phone").setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        execute(message).getContact();
    }


    private String showUsers(List<BotUser> listUser){
        if (listUser != null) {
            StringBuilder out = new StringBuilder("Users list :\n\n");

            for (BotUser user: listUser) {
                out.append(String.format("%s \t%s\n", user.getName(),
                        phoneFormat.apply(user.getPhone())));
            }
            return out.toString();
        } else
            return "No user registered yet.";
    }


    private  void  sendPersonMessage(String rawMessage, BotUser sender) throws TelegramApiException {

        Pattern msg = Pattern.compile("(\\+7(\\s\\()?\\d{3}(\\)\\s)?\\d{3}-?\\d{2}-?\\d{2})(.*)");
        Matcher matcher = msg.matcher(rawMessage);
        String phone = "", message = "";
        while (matcher.find()) {
            phone =  matcher.group(1).replaceAll("\\D", "").substring(1);
            message = matcher.group(4);
        }

        if (message == null)
            return;

        long chatId = repository.getChatId(phone);
        sendMessage(chatId, "@"+sender.getName()+"\n\n"+message);
    }

    private UnaryOperator<String> phoneFormat = s -> s.replaceAll("(\\d{3})(\\d{3})(\\d{2})(.*)",
            "+7 ($1) $2-$3-$4");
}
