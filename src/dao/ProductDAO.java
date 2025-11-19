package dao;

import utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO<T> {

    List<T> findAll();
    T findById(int id);
    boolean insert(T t);
    boolean update(T t);
    boolean delete(int id);

    // Méthode statique pour update stock :
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
