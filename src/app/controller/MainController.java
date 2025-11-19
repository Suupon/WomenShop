package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private MenuItem menuClothes;

    @FXML
    private MenuItem menuShoes;

    @FXML
    private MenuItem menuAccessories;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        menuClothes.setOnAction(e -> loadProductsView("Clothes"));
        menuShoes.setOnAction(e -> loadProductsView("Shoes"));
        menuAccessories.setOnAction(e -> loadProductsView("Accessories"));
    }

    private void loadProductsView(String category) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/views/ProductsView.fxml")
            );

            Node root = loader.load();

            ProductsController controller = loader.getController();
            controller.setMainController(this);
            controller.loadCategory(category);

            contentArea.getChildren().setAll(root);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void loadTransactionsView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/views/TransactionsView.fxml")
            );

            Node root = loader.load();
            contentArea.getChildren().setAll(root);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void loadCapitalView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/views/CapitalView.fxml")
            );

            Node root = loader.load();

            CapitalController controller = loader.getController();
            controller.updateValues();

            contentArea.getChildren().setAll(root);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
