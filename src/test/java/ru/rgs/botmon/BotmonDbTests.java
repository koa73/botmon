package ru.rgs.botmon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rgs.botmon.config.DBConfig;
import ru.rgs.botmon.repository.UsersData;
import ru.rgs.botmon.repository.UsersDataImpl;
import ru.rgs.botmon.service.bot.BotMonImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations="classpath:application.yml")
@ContextConfiguration(classes = { BotMonImpl.class, UsersDataImpl.class, DBConfig.class})
public class BotmonDbTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("usersData")
	private UsersData repository;

	@Test
	public void checkConnectionClassName() {

		assert (dataSource.getClass().getName().equals("org.sqlite.SQLiteDataSource"));
	}

	@Test
	public void checkDbFileAndTableUsersExist()  {

		try {

			assert (dataSource.getConnection()
					.createStatement()
					.executeQuery("select * from users;")
					.getMetaData().getColumnLabel(1).equals("phone"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void getUserDataFromTable(){
		// Should return null on request with unknown chatId
		assert (repository.getUser(10) == null);
	}

	@Test
	public void getUserChatIdbyPhoneNumber(){
		// Should return null on request with unknown phone
		assert (repository.getChatId("000000000") == null);
	}

	@Test
	public void phoneTest(){
		String strarg = "9162341423,9162341423,9162341423";
		Set<String> setString = new HashSet<>();

		for (String str: strarg.replaceAll("^,?","").split(",")){

			setString.add(str);
			System.out.println("-- "+str);
		}

		System.out.println(setString.size());
		for (String str: setString){
			System.out.println("-- "+str);
		}
	}
}
