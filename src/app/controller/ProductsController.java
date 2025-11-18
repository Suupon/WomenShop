package app.controller;

import dao.ProductDAO;
import dao.ShoesDAO; // ou ClothesDAO ou AccessoriesDAO
import model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductsController {

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Number> colId;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Number> colPrice;

    @FXML
    private TableColumn<Product, String> colCategory;

    private ProductDAO dao = new ShoesDAO();  // ← CHOISIR ICI

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        ObservableList<Product> products =
                FXCollections.observableArrayList(dao.getAllProducts());

        productsTable.setItems(products);
    }
}
