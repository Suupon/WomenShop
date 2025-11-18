package dao;

import model.Product;
import utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoesDAO implements ProductDAO {

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String query = "SELECT id, name, price, category FROM shoes";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
