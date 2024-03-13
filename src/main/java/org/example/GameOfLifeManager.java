package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameOfLifeManager {
    private static final double FRAME_DURATION_IN_SECONDS = 0.1;

    private final Timeline timeline;
    private final HexagonMapDrawer hexagonMapDrawer;
    private AnimationState actualState = AnimationState.PAUSED;
    public GameOfLifeManager(HexagonMapDrawer hexagonMapDrawer) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(FRAME_DURATION_IN_SECONDS),
            event -> processFrame(hexagonMapDrawer)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.hexagonMapDrawer = hexagonMapDrawer;
    }

    private void processFrame(HexagonMapDrawer drawer) {
        HexagonalMap hexagonalMap = drawer.getHexagonalMap();
        if (actualState == AnimationState.NEXT_STEP_MODE) {
            generateNextStep(hexagonalMap);
            pause();
        } else if (actualState == AnimationState.RUNNING) {
            generateNextStep(hexagonalMap);
        }
    }
    private void generateNextStep(HexagonalMap hexagonalMap) {
        int rows = hexagonalMap.getRows();
        int cols = hexagonalMap.getCols();

        HexagonalMap tempMap = new HexagonalMap(rows, cols);
        copyDeeplyMapToTempMap(hexagonalMap, tempMap);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = tempMap.getCell(i,j);
                int numberOfAliveAdjacentCells = hexagonalMap.computeNumberOfAliveAdjacentCells(i,j);
                applyConwayRules(currentCell, numberOfAliveAdjacentCells);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = tempMap.getCell(i,j);
                Cell cellFromOriginalMap = hexagonalMap.getCell(i,j);
                cellFromOriginalMap.setAlive(currentCell.isAlive());
                hexagonMapDrawer.changeColor(cellFromOriginalMap);
            }
        }
    }

    private void applyConwayRules(Cell cell, int numberOfAliveAdjacentCells) {
        if (numberOfAliveAdjacentCells < 2) {
            cell.setAlive(false);
        } else {
            cell.setAlive(numberOfAliveAdjacentCells == 2);
        }
    }

    private static void copyDeeplyMapToTempMap(HexagonalMap hexagonalMap, HexagonalMap tempMap) {
        int rows = hexagonalMap.getRows();
        int cols = hexagonalMap.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cellFromHexagonalMap = hexagonalMap.getCell(i,j);
                Cell deepCopiedCell = new Cell(j,i, cellFromHexagonalMap.isAlive());
                tempMap.setCell(deepCopiedCell, i, j);
            }
        }
    }

    public void play() {
        actualState = AnimationState.RUNNING;
        timeline.play();
    }

    public void pause() {
        actualState = AnimationState.PAUSED;
        timeline.pause();
    }

    public void nextStep() {
        actualState = AnimationState.NEXT_STEP_MODE;
        timeline.play();
    }

    public boolean isProcessPaused() {
        return actualState == AnimationState.PAUSED;
    }

    private enum AnimationState {
        PAUSED, RUNNING, NEXT_STEP_MODE
    }
}
