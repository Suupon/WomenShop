package app.controller;

import com.sun.javafx.css.StyleClassSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private MenuItem menuClothes;

    @FXML
    private MenuItem menuShoes;

    @FXML
    private MenuItem menuAccessories;

    @FXML
    private StackPane contentArea;

    @FXML private ToggleButton themeToggle;

    @FXML
    public void initialize() {
        menuClothes.setOnAction(e -> loadProductsView("Clothes"));
        menuShoes.setOnAction(e -> loadProductsView("Shoes"));
        menuAccessories.setOnAction(e -> loadProductsView("Accessories"));
    }


    @FXML
    private void loadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/views/HomeView.fxml"));
            loader.setController(this);
            Node root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Node buildHomePage() {

        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 40;");

        Label title = new Label("Welcome to WomenShop");
        title.getStyleClass().add("welcome-title");

        Label subtitle = new Label("Manage products, track inventory and monitor financial performance.");
        subtitle.getStyleClass().add("welcome-subtitle");

        // cards row
        HBox cards = new HBox(20);
        cards.setAlignment(Pos.CENTER);

        cards.getChildren().add(makeCard("Products", "All categories"));
        cards.getChildren().add(makeCard("Inventory", "Track stock levels"));
        cards.getChildren().add(makeCard("Finances", "Overview of performance"));

        Button btn = new Button("Go to Products");
        btn.getStyleClass().add("welcome-button");
        btn.setOnAction(e -> loadProductsViewFromHome());

        root.getChildren().addAll(title, subtitle, cards, btn);

        return root;
    }

    private VBox makeCard(String title, String subtitle) {
        VBox box = new VBox(6);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("welcome-card");

        Label t = new Label(title);
        t.getStyleClass().add("card-title");

        Label s = new Label(subtitle);
        s.getStyleClass().add("card-subtitle");

        box.getChildren().addAll(t, s);
        return box;
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

    @FXML
    private void loadProductsViewFromHome() {
        chooseCategoryPopup();
    }

    private void chooseCategoryPopup() {
        // Create popup window
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Choose Category");
        popup.setResizable(false);

        // Title
        Label title = new Label("Select Product Category");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Buttons
        Button btnClothes = new Button("Clothes");
        Button btnShoes = new Button("Shoes");
        Button btnAccessories = new Button("Accessories");

        btnClothes.setPrefWidth(120);
        btnShoes.setPrefWidth(120);
        btnAccessories.setPrefWidth(120);

        // Style
        String style = """
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: #cfcfcf;
            -fx-background-color: white;
            -fx-padding: 10 18;
            -fx-font-size: 14px;
            -fx-font-weight: 500;
            """;

        btnClothes.setStyle(style);
        btnShoes.setStyle(style);
        btnAccessories.setStyle(style);

        // Layout
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 25; -fx-background-color: #f7f7f7;");

        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(btnClothes, btnShoes, btnAccessories);

        root.getChildren().addAll(title, buttonsBox);

        // Button actions
        btnClothes.setOnAction(e -> {
            popup.close();
            loadProductsView("Clothes");
        });

        btnShoes.setOnAction(e -> {
            popup.close();
            loadProductsView("Shoes");
        });

        btnAccessories.setOnAction(e -> {
            popup.close();
            loadProductsView("Accessories");
        });

        // Show popup
        Scene scene = new Scene(root);
        popup.setScene(scene);
        popup.showAndWait();
    }
    @FXML
    private void goToProducts() {
        loadProductsViewFromHome(); // ton popup
    }

    @FXML
    private void goToInventory() {
        loadTransactionsView();
    }

    @FXML
    private void goToFinances() {
        loadCapitalView();
    }




}
