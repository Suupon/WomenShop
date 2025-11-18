package dao;

import model.Clothes;
import model.Product;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothesDAO implements ProductDAO<Clothes> {

    @Override
    public List<Clothes> findAll() {
        List<Clothes> list = new ArrayList<>();
        String sql = "SELECT * FROM clothes";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Clothes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("purchase_price"),
                        rs.getInt("nbItems"),
                        rs.getBoolean("discount_applied")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Clothes findById(int id) {
        String sql = "SELECT * FROM clothes WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Clothes(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDouble("purchase_price"),
                        rs.getInt("nbItems"),
                        rs.getBoolean("discount_applied")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean insert(Clothes c) {
        String sql = "INSERT INTO clothes(name, price, purchase_price, nbItems, discount_applied) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setDouble(2, c.getPrice());
            ps.setDouble(3, c.getPurchasePrice());
            ps.setInt(4, c.getNbItems());
            ps.setBoolean(5, c.isDiscountApplied());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Clothes c) {
        String sql = "UPDATE clothes SET name=?, price=?, purchase_price=?, nbItems=?, discount_applied=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setDouble(2, c.getPrice());
            ps.setDouble(3, c.getPurchasePrice());
            ps.setInt(4, c.getNbItems());
            ps.setBoolean(5, c.isDiscountApplied());
            ps.setInt(6, c.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM clothes WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
