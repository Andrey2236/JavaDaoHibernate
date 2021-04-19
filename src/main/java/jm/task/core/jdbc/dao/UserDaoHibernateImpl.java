package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory connection;

    public UserDaoHibernateImpl() {
        this.connection = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {


        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.beginTransaction();
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            transaction.rollback();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = (User) session.load(User.class, id);
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            transaction.rollback();
        }

    }

    @Override
    public List<User> getAllUsers() {

        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<User> list = session.createQuery("FROM " + User.class.getSimpleName()).list();
            transaction.commit();
            session.close();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (User user : getAllUsers()) {
                removeUserById(user.getId());
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}

