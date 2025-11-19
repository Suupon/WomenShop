package app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;

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
            File fxmlFile = new File("src/app/views/ProductsView.fxml");
            URL fxmlUrl = fxmlFile.toURI().toURL();

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node root = loader.load();

            ProductsController controller = loader.getController();
            controller.loadCategory(category);


            contentArea.getChildren().setAll(root);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
