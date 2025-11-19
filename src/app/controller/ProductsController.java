package app.controller;

import dao.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.QuantityDialog;

import java.io.IOException;
import java.util.List;

public class ProductsController {

    /* =====================
       FXML COMPONENTS
       ===================== */

    @FXML
    private Label categoryLabel;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Number> colId;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Number> colPrice;
    @FXML
    private TableColumn<Product, Boolean> colDiscount;

    @FXML
    private TableColumn<Product, Boolean> colStock;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button discountButton;

    @FXML
    private Button sellButton;

    @FXML
    private Button purchaseButton;


    /* =====================
       DAO + CATEGORY
       ===================== */

    private final ClothesDAO clothesDAO = new ClothesDAO();
    private final ShoesDAO shoesDAO = new ShoesDAO();
    private final AccessoriesDAO accessoriesDAO = new AccessoriesDAO();

    private String currentCategory = "Clothes";


    /* =====================
       INITIALIZATION
       ===================== */

    @FXML
    public void initialize() {

        // mapping colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("nbItems"));

        productsTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        productsTable.getColumns().removeIf(c -> c.getText() == null || c.getText().isBlank());


        colDiscount.setCellValueFactory(cd ->
                new SimpleBooleanProperty(cd.getValue().isDiscountApplied()));

        // boutons CRUD
        addButton.setOnAction(e -> handleAdd());
        editButton.setOnAction(e -> handleEdit());
        deleteButton.setOnAction(e -> handleDelete());
        discountButton.setOnAction(e -> onToggleDiscount());

        // boutons Sell/Purchase
        sellButton.setOnAction(e -> handleSell());
        purchaseButton.setOnAction(e -> handlePurchase());

        loadCategory("Clothes");
    }

    /* =====================
       CATEGORY LOADING
       ===================== */

    public void loadCategory(String category) {
        this.currentCategory = category;
        categoryLabel.setText("Products - " + category);
        refreshTable();
    }

    public void refreshTable() {
        List<? extends Product> list;

        switch (currentCategory) {
            case "Clothes" -> list = clothesDAO.findAll();
            case "Shoes" -> list = shoesDAO.findAll();
            case "Accessories" -> list = accessoriesDAO.findAll();
            default -> list = clothesDAO.findAll();
        }

        ObservableList<Product> items = FXCollections.observableArrayList(list);
        productsTable.setItems(items);
    }


    /* =====================
       CRUD BUTTONS
       ===================== */
    @FXML
    private void handleAdd() {
        openForm(null);
    }

    @FXML
    private void handleEdit() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Please select a product to edit.");
            return;
        }
        openForm(selected);
    }

    @FXML
    private void handleDelete() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Please select a product to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText(null);
        confirm.setContentText("Delete \"" + selected.getName() + "\" ?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;

        boolean ok;

        if (selected instanceof Clothes c) ok = clothesDAO.delete(c.getId());
        else if (selected instanceof Shoes s) ok = shoesDAO.delete(s.getId());
        else if (selected instanceof Accessory a) ok = accessoriesDAO.delete(a.getId());
        else ok = false;

        if (!ok) alert("Error while deleting product.");

        refreshTable();
    }


    /* =====================
       OPEN FORM
       ===================== */

    private void openForm(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/ProductFormView.fxml"));
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle(product == null ? "Add product" : "Edit product");
            dialog.setScene(new javafx.scene.Scene(root));

            ProductFormController controller = loader.getController();
            controller.setContext(this, currentCategory, product, dialog);

            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            alert("Error opening form window.");
        }
    }


    /* =====================
       SAVE FROM FORM
       ===================== */

    public void saveProductFromForm(String category,
                                    Product original,
                                    String name,
                                    double price,
                                    double purchasePrice,
                                    int nbItems,
                                    boolean discountApplied) {

        boolean ok;

        if (original == null) {
            // creation
            switch (category) {
                case "Clothes" -> {
                    Clothes c = new Clothes(0, name, price, purchasePrice, nbItems, discountApplied);
                    ok = clothesDAO.insert(c);
                }
                case "Shoes" -> {
                    Shoes s = new Shoes(0, name, price, purchasePrice, nbItems, discountApplied);
                    ok = shoesDAO.insert(s);
                }
                case "Accessories" -> {
                    Accessory a = new Accessory(0, name, price, purchasePrice, nbItems, discountApplied);
                    ok = accessoriesDAO.insert(a);
                }
                default -> ok = false;
            }
        } else {
            // update
            original.setName(name);
            original.setPrice(price);
            original.setPurchasePrice(purchasePrice);
            original.setNbItems(nbItems);
            original.setDiscountApplied(discountApplied);

            if (original instanceof Clothes c) ok = clothesDAO.update(c);
            else if (original instanceof Shoes s) ok = shoesDAO.update(s);
            else if (original instanceof Accessory a) ok = accessoriesDAO.update(a);
            else ok = false;
        }

        if (!ok) alert("Error while saving product.");

        refreshTable();
    }


    /* =====================
       DISCOUNT
       ===================== */

    @FXML
    private void onToggleDiscount() {
        Product p = productsTable.getSelectionModel().getSelectedItem();
        if (p == null) {
            alert("Please select a product.");
            return;
        }

        boolean newVal = !p.isDiscountApplied();
        p.setDiscountApplied(newVal);

        boolean ok;

        if (p instanceof Clothes c) ok = clothesDAO.update(c);
        else if (p instanceof Shoes s) ok = shoesDAO.update(s);
        else if (p instanceof Accessory a) ok = accessoriesDAO.update(a);
        else ok = false;

        if (!ok) alert("Error updating discount.");

        refreshTable();
    }


    /* =====================
       SELL
       ===================== */

    @FXML
    private void handleSell() {
        Product p = productsTable.getSelectionModel().getSelectedItem();
        if (p == null) {
            alert("Please select a product.");
            return;
        }

        Integer qty = QuantityDialog.show("Sell product");
        if (qty == null) return;

        if (qty > p.getNbItems()) {
            alert("Not enough stock.");
            return;
        }

        double salePrice = p.getPrice();

        if (p.isDiscountApplied()) {
            switch (currentCategory) {
                case "Clothes" -> salePrice *= 0.70;
                case "Shoes" -> salePrice *= 0.80;
                case "Accessories" -> salePrice *= 0.50;
            }
        }

        int newStock = p.getNbItems() - qty;
        updateStock(p, newStock);

        TransactionDAO.insert(new Transaction(
                p.getId(), currentCategory, "SELL", qty, salePrice));

        refreshTable();
    }


    /* =====================
       PURCHASE
       ===================== */

    @FXML
    private void handlePurchase() {
        Product p = productsTable.getSelectionModel().getSelectedItem();
        if (p == null) {
            alert("Please select a product.");
            return;
        }

        Integer qty = QuantityDialog.show("Purchase stock");
        if (qty == null) return;

        double cost = p.getPurchasePrice();

        int newStock = p.getNbItems() + qty;
        updateStock(p, newStock);

        TransactionDAO.insert(new Transaction(
                p.getId(), currentCategory, "PURCHASE", qty, cost));

        refreshTable();
    }


    /* =====================
       UPDATE STOCK (DAO)
       ===================== */

    private void updateStock(Product p, int newStock) {
        p.setNbItems(newStock);

        if (p instanceof Clothes c) clothesDAO.updateStock(c.getId(), newStock);
        else if (p instanceof Shoes s) shoesDAO.updateStock(s.getId(), newStock);
        else if (p instanceof Accessory a) accessoriesDAO.updateStock(a.getId(), newStock);
    }


    /* =====================
       UTIL
       ===================== */

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
