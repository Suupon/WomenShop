package dao;

import model.Transaction;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public static boolean insert(Transaction t) {
        String sql = "INSERT INTO transactions " +
                "(product_id, category, type, quantity, unit_price, total_amount, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getProductId());
            ps.setString(2, t.getCategory());
            ps.setString(3, t.getType());
            ps.setInt(4, t.getQty());
            ps.setDouble(5, t.getUnitPrice());     // 700
            ps.setDouble(6, t.getTotalAmount());   // 7000
            ps.setTimestamp(7, Timestamp.valueOf(t.getDate()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static List<Transaction> findAll() {
        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT id, product_id, category, type, quantity, unit_price, total_amount, date FROM transactions ORDER BY date DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getString("category"),
                        rs.getString("type"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getTimestamp("date").toLocalDateTime()
                );
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static double getTotalIncomes() {
        String sql = "SELECT SUM(total_amount) FROM transactions WHERE type = 'SELL'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static double getTotalCosts() {
        String sql = "SELECT SUM(total_amount) FROM transactions WHERE type = 'PURCHASE'";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static final double INITIAL_CAPITAL = 10000.0;

    public static double getCapital() {
        return INITIAL_CAPITAL + getTotalIncomes() - getTotalCosts();
    }

    private static double queryDouble(String sql) {
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }





}
