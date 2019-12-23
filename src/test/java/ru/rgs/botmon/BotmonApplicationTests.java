package ru.rgs.botmon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rgs.botmon.config.DBConfig;
import ru.rgs.botmon.repository.UsersDataImpl;
import ru.rgs.botmon.service.bot.BotMonImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BotMonImpl.class, UsersDataImpl.class, DBConfig.class})
public class BotmonApplicationTests {

	@Test
	public void contextLoads() {
	}

}
