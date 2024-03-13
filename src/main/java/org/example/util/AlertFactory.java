package org.example.util;

import javafx.scene.control.Alert;

public final class AlertFactory {
    private AlertFactory() {
        // disable instantiation
    }

    public static void showAlert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
