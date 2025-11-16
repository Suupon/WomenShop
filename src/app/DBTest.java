package app;

import utils.Database;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            System.out.println("Connexion réussie !");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
