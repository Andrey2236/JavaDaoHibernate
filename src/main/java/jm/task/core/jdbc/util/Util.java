package jm.task.core.jdbc.util;

import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Util {
    // реализуйте настройку соеденения с БД


    public static Connection getConnection() throws SQLException {

        String hostName = "localhost";
        String dbName = "astrio";
        String userName = "root";
        String password = "Umulbion@2236";

        return getConnection(hostName, dbName, userName, password);
    }

    public static Connection getConnection(String hostName, String dbName, String userName, String password) throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        return (Connection) DriverManager.getConnection(connectionURL, userName, password);
    }


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/astrio");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "Umulbion@2236");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE);
            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("успешно");
        }
        return sessionFactory;
    }

}



