package dao;

import model.Accessory;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesDAO implements ProductDAO<Accessory> {

    @Override
    public List<Accessory> findAll() {
        List<Accessory> list = new ArrayList<>();
        String sql = "SELECT * FROM accessories";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Accessory(
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
    public Accessory findById(int id) {
        String sql = "SELECT * FROM accessories WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Accessory(
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
    public boolean insert(Accessory a) {
        String sql = "INSERT INTO accessories(name, price, purchase_price, nbItems, discount_applied) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getName());
            ps.setDouble(2, a.getPrice());
            ps.setDouble(3, a.getPurchasePrice());
            ps.setInt(4, a.getNbItems());
            ps.setBoolean(5, a.isDiscountApplied());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Accessory a) {
        String sql = "UPDATE accessories SET name=?, price=?, purchase_price=?, nbItems=?, discount_applied=? WHERE id=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getName());
            ps.setDouble(2, a.getPrice());
            ps.setDouble(3, a.getPurchasePrice());
            ps.setInt(4, a.getNbItems());
            ps.setBoolean(5, a.isDiscountApplied());
            ps.setInt(6, a.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM accessories WHERE id = ?";

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
        ProductDAO.updateStock("accessories", id, newStock); // ou shoes / accessories
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
