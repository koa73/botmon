package ru.rgs.botmon.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.rgs.botmon.model.BotUser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by OAKutsenko on 17.12.2019.
 */
public class UserRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        BotUser user = new BotUser();
        user.setChatId(Long.valueOf(resultSet.getString("chatid")));
        user.setPhone(resultSet.getString("phone"));
        user.setName(resultSet.getString("name"));
        user.setStateId(Integer.valueOf(resultSet.getString("state")));

        return user;
    }
}
