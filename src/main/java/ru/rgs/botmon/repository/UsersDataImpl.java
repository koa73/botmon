package ru.rgs.botmon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.rgs.botmon.model.BotUser;
import ru.rgs.botmon.repository.mappers.UserRowMapper;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;


@Repository("usersData")
public class UsersDataImpl implements UsersData {

    private JdbcTemplate template;

    private static final String SELECT_CHATID = "SELECT chatid FROM  users WHERE phone = ?;";

    private static final String SELECT_USER = "SELECT * FROM  users WHERE chatid = ?;";

    private static final String INSERT_NEW_USER = "INSERT INTO users(phone,name,chatid, state) " +
            "VALUES (?,?,?,?) ON CONFLICT (phone) DO UPDATE SET name=excluded.name, chatid= excluded.chatid, " +
            "state = excluded.state WHERE phone = excluded.phone;";

    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";

    @Autowired
    void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public List<BotUser> getAllUsers() {

        List<BotUser> userList = template.query(SELECT_ALL_USERS, new UserRowMapper());
        if (!userList.isEmpty()) {
            return userList;
        }
        return null;

    }

    @Override
    public boolean createUser(String phone, String name, long chatId) {

        return template.update(INSERT_NEW_USER,
                new Object[] { phone, name, chatId, 1 },
                new int[] {Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER }) > 0;
    }

    @Override
    public Long getChatId(String phone) {

        try {
            return template.queryForObject(SELECT_CHATID, new Object[]{phone}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public BotUser getUser(long chatId) {
        try {
            return (BotUser) template.queryForObject(
                   SELECT_USER, new Object[]{chatId}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
