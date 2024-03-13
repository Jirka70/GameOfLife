package org.example.components;

import javafx.scene.control.*;
import org.example.control.HexagonMapDrawer;
import org.example.control.TopMenuController;
import org.example.model.HexagonalMap;

public class TopMenu {
    private final MenuBar menuBar = new MenuBar();

    public TopMenu(HexagonalMap hexagonalMap, HexagonMapDrawer drawer) {
        TopMenuController controller = new TopMenuController();

        MenuItem loadConfiguration = new Menu("Load configuration");
        loadConfiguration.setOnAction(e -> controller.loadConfig(menuBar, hexagonalMap, drawer));
        Menu saveConfiguration = new Menu("Save configuration");
        saveConfiguration.setOnAction(e -> controller.saveConfig(hexagonalMap));
        Menu fileMenu = new Menu("File");
        
        fileMenu.getItems().addAll(saveConfiguration, loadConfiguration);
        menuBar.getMenus().add(fileMenu);
    }

    public MenuBar getTopMenu() {
        return menuBar;
    }
}
