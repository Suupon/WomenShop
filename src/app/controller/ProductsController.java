package app.controller;

import dao.AccessoriesDAO;
import dao.ClothesDAO;
import dao.ProductDAO;
import dao.ShoesDAO;
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
import model.Accessory;
import model.Clothes;
import model.Product;
import model.Shoes;

import java.io.IOException;
import java.util.List;

public class ProductsController {

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
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, Boolean> colDiscount;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button discountButton;

    private final ClothesDAO clothesDAO = new ClothesDAO();
    private final ShoesDAO shoesDAO = new ShoesDAO();
    private final AccessoriesDAO accessoriesDAO = new AccessoriesDAO();

    private String currentCategory = "Clothes";
    private ProductDAO<?> currentDAO;

    @FXML
    public void initialize() {
        // mapping colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        // colonne discount (true / false)
        colDiscount.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().isDiscountApplied()));

        // handlers boutons
        addButton.setOnAction(e -> onAdd());
        editButton.setOnAction(e -> onEdit());
        deleteButton.setOnAction(e -> onDelete());
        discountButton.setOnAction(e -> onToggleDiscount());

        // par défaut si jamais la vue est ouverte sans catégorie
        loadCategory("Clothes");
    }

    // appelé par MainController
    public void loadCategory(String category) {
        this.currentCategory = category;

        switch (category) {
            case "Clothes" -> currentDAO = clothesDAO;
            case "Shoes" -> currentDAO = shoesDAO;
            case "Accessories" -> currentDAO = accessoriesDAO;
            default -> currentDAO = clothesDAO;
        }

        categoryLabel.setText("Products - " + currentCategory);
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
       BOUTONS CRUD
       ===================== */

    private void onAdd() {
        openForm(null);   // produit null = création
    }

    private void onEdit() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Please select a product to edit.");
            return;
        }
        openForm(selected);
    }

    private void onDelete() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Please select a product to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete product");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete \"" + selected.getName() + "\" ?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        boolean ok;
        if (selected instanceof Clothes c) {
            ok = clothesDAO.delete(c.getId());
        } else if (selected instanceof Shoes s) {
            ok = shoesDAO.delete(s.getId());
        } else if (selected instanceof Accessory a) {
            ok = accessoriesDAO.delete(a.getId());
        } else {
            ok = false;
        }

        if (!ok) {
            alert("Error while deleting product in database.");
        }
        refreshTable();
    }

    private void onToggleDiscount() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            alert("Please select a product.");
            return;
        }

        boolean newValue = !selected.isDiscountApplied();
        selected.setDiscountApplied(newValue);

        boolean ok;
        if (selected instanceof Clothes c) {
            ok = clothesDAO.update(c);
        } else if (selected instanceof Shoes s) {
            ok = shoesDAO.update(s);
        } else if (selected instanceof Accessory a) {
            ok = accessoriesDAO.update(a);
        } else {
            ok = false;
        }

        if (!ok) {
            alert("Error while updating discount in database.");
        }

        refreshTable();
    }

    /* =====================
       OUVERTURE FORMULAIRE
       ===================== */

    private void openForm(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/views/ProductFormView.fxml"));
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
            alert("Error while opening the form window.");
        }
    }

    /* =====================
       Utilitaire
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
            // création
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
            // modification
            original.setName(name);
            original.setPrice(price);
            original.setPurchasePrice(purchasePrice);
            original.setNbItems(nbItems);
            original.setDiscountApplied(discountApplied);

            if (original instanceof Clothes c) {
                ok = clothesDAO.update(c);
            } else if (original instanceof Shoes s) {
                ok = shoesDAO.update(s);
            } else if (original instanceof Accessory a) {
                ok = accessoriesDAO.update(a);
            } else {
                ok = false;
            }
        }

        if (!ok) {
            alert("Error while saving product in database.");
        }

        refreshTable();
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
