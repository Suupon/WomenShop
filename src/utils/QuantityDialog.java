package utils;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class QuantityDialog {

    public static Integer show(String title) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle(title);

        Label label = new Label("Enter quantity:");
        TextField qtyField = new TextField();

        VBox box = new VBox(10, label, qtyField);
        dialog.getDialogPane().setContent(box);

        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == okBtn) {
                try {
                    int q = Integer.parseInt(qtyField.getText());
                    if (q <= 0) throw new Exception();
                    return q;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid quantity.", ButtonType.OK);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}
