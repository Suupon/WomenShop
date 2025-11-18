package utils;

import java.io.FileInputStream;
import java.io.IOException;
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
            // Charge le driver MySQL (sécurisé)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Charge les propriétés
            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("config/db.properties")) {
                props.load(fis);
            }

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new RuntimeException("❌ Paramètres DB manquants dans config/db.properties");
            }

        } catch (IOException e) {
            System.err.println("❌ Erreur : impossible de charger config/db.properties");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL non trouvé");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
