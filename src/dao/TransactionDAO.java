package dao;

import model.Transaction;
import utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {

    public static void insert(Transaction t) {
        String sql = "INSERT INTO transactions(product_id, category, type, quantity, unit_price, total_amount) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getProductId());
            ps.setString(2, t.getCategory());
            ps.setString(3, t.getType());
            ps.setInt(4, t.getQuantity());
            ps.setDouble(5, t.getUnitPrice());
            ps.setDouble(6, t.getTotalAmount());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
