package ru.rgs.botmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BotmonApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(BotmonApplication.class, args);
	}
}
