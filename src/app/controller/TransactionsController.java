package app.controller;

import dao.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionsController {

    @FXML private TableView<Transaction> transactionsTable;

    @FXML private TableColumn<Transaction, Integer> colId;
    @FXML private TableColumn<Transaction, Integer> colProductId;
    @FXML private TableColumn<Transaction, String> colCategory;
    @FXML private TableColumn<Transaction, String> colType;
    @FXML private TableColumn<Transaction, Integer> colQty;
    @FXML private TableColumn<Transaction, Double> colUnitPrice;
    @FXML private TableColumn<Transaction, Double> colTotal;
    @FXML private TableColumn<Transaction, LocalDateTime> colDate;  // <-- ici

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        colDate.setCellFactory(column -> new TableCell<Transaction, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        loadTransactions();
    }

    private void loadTransactions() {
        transactionsTable.setItems(
                FXCollections.observableArrayList(TransactionDAO.findAll())
        );
    }
}
