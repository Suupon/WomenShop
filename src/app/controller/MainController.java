package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    private MenuItem menuClothes;

    @FXML
    private MenuItem menuShoes;

    @FXML
    private MenuItem menuAccessories;

    @FXML
    public void initialize() {
        menuClothes.setOnAction(e -> System.out.println("Clothes selected"));
        menuShoes.setOnAction(e -> System.out.println("Shoes selected"));
        menuAccessories.setOnAction(e -> System.out.println("Accessories selected"));
    }
}
