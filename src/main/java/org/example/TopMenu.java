package org.example;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TopMenu {
    private final MenuBar menuBar = new MenuBar();

    public TopMenu(HexagonMapDrawer hexagonMapDrawer) {
        MenuItem loadConfiguration = new Menu("Load configuration");
        loadConfiguration.setOnAction(e -> loadConfig(hexagonMapDrawer));
        Menu saveConfiguration = new Menu("Save configuration");
        saveConfiguration.setOnAction(e -> saveConfig(hexagonMapDrawer.getHexagonalMap()));
        Menu fileMenu = new Menu("File");
        
        fileMenu.getItems().addAll(saveConfiguration, loadConfiguration);
        menuBar.getMenus().add(fileMenu);
    }

    private void loadConfig(HexagonMapDrawer hexagonMapDrawer) {

        Window homeWindow = menuBar.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(Paths.get("/Users/jirka/Jirka/KPG/GameOfLife/database").toFile());
        File selectedFile = chooser.showOpenDialog(homeWindow);
        if (selectedFile == null) {
            return;
        }
        MapLoader loader = new MapLoader(selectedFile.toPath());
        try {
            HexagonalMap loadedMap = loader.loadMap();
            hexagonMapDrawer.setHexagonalMap(loadedMap);
        } catch (MapLoader.ParsingException e) {
            AlertFactory.showAlert(Alert.AlertType.ERROR, "Invalid selected file",
                    "Selected file cannot be solved",
                    "No file was loaded. Try to select valid file within the database/ folder");
        }
    }

    private void saveConfig(HexagonalMap hexagonalMap) {
        String typedFileName = getFileToSave();
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

    private String getFileToSave() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Enter file name");
        textInputDialog.setHeaderText("Type a file name, where you want to save the configuration");
        textInputDialog.setContentText("Enter file name");

        Optional<String> result = textInputDialog.showAndWait();

        return result.orElse(null);
    }

    public MenuBar getTopMenu() {
        return menuBar;
    }
}
