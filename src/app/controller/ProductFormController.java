package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Product;

public class ProductFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private TextField nbItemsField;

    @FXML
    private CheckBox discountCheck;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private ProductsController parent;
    private String category;
    private Product editingProduct;
    private Stage dialogStage;

    public void setContext(ProductsController parent,
                           String category,
                           Product product,
                           Stage stage) {

        this.parent = parent;
        this.category = category;
        this.editingProduct = product;
        this.dialogStage = stage;

        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            purchasePriceField.setText(String.valueOf(product.getPurchasePrice()));
            nbItemsField.setText(String.valueOf(product.getNbItems()));
            discountCheck.setSelected(product.isDiscountApplied());
        }

        saveButton.setOnAction(e -> onSave());
        cancelButton.setOnAction(e -> dialogStage.close());
    }

    private void onSave() {
        // validations simples
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            alert("Name cannot be empty.");
            return;
        }

        double price;
        double purchasePrice;
        int nbItems;

        try {
            price = Double.parseDouble(priceField.getText().trim());
            purchasePrice = Double.parseDouble(purchasePriceField.getText().trim());
            nbItems = Integer.parseInt(nbItemsField.getText().trim());
        } catch (NumberFormatException ex) {
            alert("Price, purchase price and stock must be numeric.");
            return;
        }

        if (price <= 0 || purchasePrice < 0 || nbItems < 0) {
            alert("Price must be > 0, purchase price ≥ 0, stock ≥ 0.");
            return;
        }

        boolean discountApplied = discountCheck.isSelected();

        parent.saveProductFromForm(
                category,
                editingProduct,
                name,
                price,
                purchasePrice,
                nbItems,
                discountApplied
        );

        dialogStage.close();
    }

    private void alert(String msg) {
        javafx.scene.control.Alert a =
                new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
