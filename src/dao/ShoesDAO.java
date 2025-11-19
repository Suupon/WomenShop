package dao;

import model.Shoes;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoesDAO implements ProductDAO<Shoes> {

    @Override
    public List<Shoes> findAll() {
        List<Shoes> list = new ArrayList<>();
        String sql = "SELECT * FROM shoes";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Shoes(
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
    public Shoes findById(int id) {
        String sql = "SELECT * FROM shoes WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Shoes(
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
    public boolean insert(Shoes s) {
        String sql = "INSERT INTO shoes(name, price, purchase_price, nbItems, discount_applied) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setDouble(2, s.getPrice());
            ps.setDouble(3, s.getPurchasePrice());
            ps.setInt(4, s.getNbItems());
            ps.setBoolean(5, s.isDiscountApplied());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Shoes s) {
        String sql = "UPDATE shoes SET name=?, price=?, purchase_price=?, nbItems=?, discount_applied=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setDouble(2, s.getPrice());
            ps.setDouble(3, s.getPurchasePrice());
            ps.setInt(4, s.getNbItems());
            ps.setBoolean(5, s.isDiscountApplied());
            ps.setInt(6, s.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM shoes WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateStock(int id, int newStock) {
        ProductDAO.updateStock("shoes", id, newStock); // ou shoes / accessories
    }

    public void updateDiscount(int id, boolean status) {
        String sql = "UPDATE clothes SET discount_applied = ? WHERE id = ?"; // adapter table

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
