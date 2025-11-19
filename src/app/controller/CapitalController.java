package app.controller;

import dao.ProductDAO;
import dao.TransactionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CapitalController {

    private static final double INITIAL_CAPITAL = 10000; // adapte selon ton projet

    @FXML
    private Label labelIncomes;

    @FXML
    private Label labelCosts;

    @FXML
    private Label labelStockValue;

    @FXML
    private Label labelCapital;

    @FXML
    public void initialize() {
        updateValues();
    }

    public void updateValues() {

        double incomes = TransactionDAO.getTotalIncomes();
        double costs = TransactionDAO.getTotalCosts();
        double stockValue = ProductDAO.getTotalStockValue();
        double capital = INITIAL_CAPITAL + incomes - costs;

        labelIncomes.setText(String.format("%.2f", incomes));
        labelCosts.setText(String.format("%.2f", costs));
        labelStockValue.setText(String.format("%.2f", stockValue));
        labelCapital.setText(String.format("%.2f", capital));
    }
}
