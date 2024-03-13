package org.example.control;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.model.Cell;
import org.example.model.HexagonalMap;
import org.example.util.AlertFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TopMenuController {
    public void saveConfig(HexagonalMap hexagonalMap) {
        String typedFileName = getFileToSave(); // displays text field dialog
        if (typedFileName == null) {
            return;
        }

        Path fileToSave = Paths.get("database").resolve(Paths.get(getFileToSave()));
        HexagonMapSerializer serializer = new HexagonMapSerializer(hexagonalMap);

        try {
            serializer.serializeMap(fileToSave);
        } catch (IOException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR,
                    "Cannot solve file",
                    "Typed file cannot be solved, try to type another name of file", "");
        }
    }

    public void loadConfig(MenuBar menuBar, HexagonalMap hexagonalMap, HexagonMapDrawer drawer) {
        Window homeWindow = menuBar.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(Paths.get("/Users/jirka/Jirka/KPG/GameOfLife/database").toFile());
        File selectedFile = chooser.showOpenDialog(homeWindow);
        if (selectedFile == null) {
            return;
        }
        MapLoader loader = new MapLoader(selectedFile.toPath());
        try {
            loader.loadMapContent(hexagonalMap);
            changeColorOfAllCells(drawer, hexagonalMap);
        } catch (MapLoader.ParsingException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, "Invalid selected file",
                    "Selected file cannot be solved",
                    "No file was loaded. Try to select valid file within the database/ folder");
        }
    }

    private void changeColorOfAllCells(HexagonMapDrawer drawer, HexagonalMap map) {
        int rows = map.getRows();
        int cols = map.getCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = map.getCell(i, j);
                drawer.changeColor(cell);
            }
        }
    }

    private String getFileToSave() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter file name");
        textInputDialog.setHeaderText("Type a file name, where you want to save the configuration");
        textInputDialog.setContentText("Enter file name");

        Optional<String> result = textInputDialog.showAndWait();

        return result.orElse(null);
    }


}
