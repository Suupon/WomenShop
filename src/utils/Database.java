package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();

            // 🔥 Charge depuis le classpath (SAFE)
            try (InputStream input = Database.class.getClassLoader()
                    .getResourceAsStream("config/db.properties")) {

                if (input == null) {
                    throw new RuntimeException("❌ config/db.properties introuvable !");
                }

                props.load(input);
            }

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new RuntimeException("❌ Paramètres DB manquants dans config/db.properties");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
