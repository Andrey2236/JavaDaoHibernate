package jm.task.core.jdbc.dao;

import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  lastname VARCHAR(45) NOT NULL,\n" +
                    "  age INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (id))");
            ResultSet resultSet = Util.getConnection()
                    .getMetaData()
                    .getTables(null, null, "users", null);
            if (resultSet.next()) {
                System.out.println("Таблица создана");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            ResultSet resultSet = connection
                    .getMetaData()
                    .getTables(null, null, "users", null);
            if (resultSet.next()) {
                System.out.println("Таблица удалена");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO users(name, lastname, age) values ('%s', '%s', %d)",
                    user.getName(), user.getLastName(), user.getAge()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try {
            Statement dell = connection.createStatement();
            dell.executeUpdate(String.format("DELETE FROM users WHERE id = %d", id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        List<User> users = getAllUsers();
        for (User user : users) {
            removeUserById(user.getId());
        }


    }
}
