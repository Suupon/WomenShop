package dao;

import utils.Database;

import java.sql.*;
import java.util.List;

public interface ProductDAO<T> {

    List<T> findAll();

    T findById(int id);

    boolean insert(T t);

    boolean update(T t);

    boolean delete(int id);

    // ----- Méthode statique utilitaire pour la valeur totale du stock -----
    static double getTotalStockValue() {
        String sql =
                "SELECT " +
                        "IFNULL((SELECT SUM(price * nbItems) FROM clothes), 0) +" +
                        "IFNULL((SELECT SUM(price * nbItems) FROM shoes), 0) +" +
                        "IFNULL((SELECT SUM(price * nbItems) FROM accessories), 0) " +
                        "AS total_stock";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    static void updateStock(String table, int id, int newStock) {
        String sql = "UPDATE " + table + " SET nbItems = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newStock);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




