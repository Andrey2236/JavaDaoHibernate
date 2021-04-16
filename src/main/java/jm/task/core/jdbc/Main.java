package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;
import jm.task.core.jdbc.service.*;

import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
//        UserServiceImpl user = new UserServiceImpl();
//        user.createUsersTable();
//        user.saveUser("das","sheremetjev", (byte) 23);
//        user.saveUser("ewe","sheremetjev", (byte) 23);
//        user.saveUser("Anrey","sheremetjev", (byte) 23);
//        user.saveUser("sd","sheremetjev", (byte) 23);
//        List<User> allUsers = user.getAllUsers();
//        allUsers.forEach(System.out::println);
//        user.cleanUsersTable();
//        user.dropUsersTable();


        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("sdfsd", "sdfsg", (byte) 12);
        userDaoHibernate.removeUserById(8);
        userDaoHibernate.cleanUsersTable();
        System.out.println(userDaoHibernate.getAllUsers().toString());
        userDaoHibernate.dropUsersTable();
        // заработало 🙂
    }
}
