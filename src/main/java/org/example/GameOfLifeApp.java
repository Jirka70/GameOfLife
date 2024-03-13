package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameOfLifeApp extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    /**
     * Something like "radius" of hexagon. Indicates the half of the hexagon width basically
     */
    private static final int HEXAGON_SIZE = 10;
    private final Pane rootPane = new Pane();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(rootPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("GameOfLife - Jiri Tresohlavy KPG/cv03");
        stage.setResizable(false);
        stage.show();

        HexagonalMap hexagonalMap = new HexagonalMap(HEIGHT / HEXAGON_SIZE, WIDTH / HEXAGON_SIZE);
        hexagonalMap.initMapWithDeadCells();

        HexagonMapDrawer drawer = new HexagonMapDrawer(hexagonalMap, HEXAGON_SIZE);
        drawer.displayMap(rootPane);

        TopMenu topMenu = new TopMenu(drawer);
        rootPane.getChildren().add(topMenu.getTopMenu());

        rootPane.setOnMouseClicked(drawer::changeCellColorWithClick);

        GameOfLifeManager gameOfLifeManager = new GameOfLifeManager(drawer);
        scene.setOnKeyPressed(keyEvent -> processKeyPress(gameOfLifeManager, keyEvent));
    }

    private void processKeyPress(GameOfLifeManager gameOfLifeManager, KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            gameOfLifeManager.play();
        } else if (keyEvent.getCode() == KeyCode.SPACE) {
            gameOfLifeManager.pause();
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            if (gameOfLifeManager.isProcessPaused()) {
                gameOfLifeManager.nextStep();
            }
        }
    }
}
