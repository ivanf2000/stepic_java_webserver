package dbService.dao;

import accounts.UserProfile;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet getByID(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1),
                    result.getString(2),
                    result.getString(3));
        });
    }

    public UserProfile getByLogin(String login) throws SQLException {
        UsersDataSet usersDataSet = executor.execQuery("select * from users where login='"
                + login + "'", result -> {
            if (!result.next()) {
                return null;
            }
            return new UsersDataSet(result.getLong(1),
                    result.getString(2),
                    result.getString(3));
        });
        if (usersDataSet == null) {
            return null;
        }
        return new UserProfile(usersDataSet.getLogin(), usersDataSet.getPassword());
    }

    public long getUserId(String name) throws SQLException {
        return executor.execQuery("select * from users where login='" + name + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public void insertUser(UserProfile userProfile) throws SQLException {
        executor.execUpdate("insert into users (login, password) values " +
                "('" + userProfile.getLogin()+ "', '" + userProfile.getPassword() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users " +
                "(id bigint auto_increment, login varchar(256), " +
                "password varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
