package org.example;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexagonMapDrawer {
    private static final int NUMBER_OF_SIDES = 6;
    private static final int ANGLE = 60;
    private final Polygon[][] hexagonPlate;
    private HexagonalMap hexagonalMap;
    private final double hexagonSize;
    private final double verticalSpacing;
    private final double horizontalSpacing;

    public HexagonMapDrawer(HexagonalMap hexagonalMap, double hexagonSize) {
        this.hexagonalMap = hexagonalMap;
        this.hexagonSize = hexagonSize;
        this.hexagonPlate = new Polygon[hexagonalMap.getRows()][hexagonalMap.getCols()];

        verticalSpacing = Math.sqrt(3) * hexagonSize;
        horizontalSpacing = 3 / 2.0 * hexagonSize;
    }

    public void setHexagonalMap(HexagonalMap newHexagonalMap) {
        hexagonalMap = newHexagonalMap;
        configHexagons();
    }

    public HexagonalMap getHexagonalMap() {
        return hexagonalMap;
    }

    private void configHexagons() {
        int rows = hexagonalMap.getRows();
        int cols = hexagonalMap.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
               changeColor(hexagonalMap.getCell(i,j));
            }
        }
    }

    public void displayMap(Pane rootPane) {
        int rows = hexagonalMap.getRows();
        int cols = hexagonalMap.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Polygon hexagon = createHexagon(j, i);
                hexagonPlate[i][j] = hexagon;
                rootPane.getChildren().add(hexagon);
            }
        }
    }

    public Polygon createHexagon(int x, int y) {
        Position center = new Position(horizontalSpacing * x, computeVerticalOffset(y, x));
        double[] points = new double[2 * NUMBER_OF_SIDES];
        for (int i = 0; i < NUMBER_OF_SIDES; i++) {
            int angleDeg = i * ANGLE;
            double angleRad = Math.PI / 180 * angleDeg;
            points[i * 2] = center.x + hexagonSize * Math.cos(angleRad);
            points[i * 2 + 1] = center.y + hexagonSize * Math.sin(angleRad);
        }

        Polygon hexagon = new Polygon(points);
        hexagon.setStroke(Color.BLACK);
        hexagon.setFill(hexagonalMap.getCell(y, x).isAlive() ? Color.BLACK : Color.WHITE);
        return hexagon;
    }

    private double computeVerticalOffset(int row, int col) {
        if ((col & 1) == 0) {
            return verticalSpacing * row;
        } else {
            return verticalSpacing * (row - 0.5);
        }
    }

    public void changeCellColorWithClick(MouseEvent e) {
        double eventX = e.getX();
        double eventY = e.getY();

        Position clickedPosition = computeOffsetPositionOfMouseClick(eventX, eventY);

        Cell clickedCell = hexagonalMap.getCell((int) clickedPosition.y, (int) clickedPosition.x);
        clickedCell.setAlive(!clickedCell.isAlive());
        changeColor(clickedCell);
    }

    private Position computeOffsetPositionOfMouseClick(double eventX, double eventY) {
        double x = (2.0 / 3 * eventX) / hexagonSize;
        double y = (-1.0 / 3 * eventX + Math.sqrt(3) / 3 * eventY) / hexagonSize;
        Position axialPosition = axialRound(new Position(x, y));

        return convertAxialToOffset(axialPosition);
    }

    private static Position convertAxialToOffset(Position axialPosition) {
        double col = axialPosition.x;
        double row = axialPosition.y + (axialPosition.x + ((int) axialPosition.x & 1)) / 2;
        return new Position(col, row);
    }

    public void changeColor(Cell cell) {
        Polygon polygonOfCell = hexagonPlate[cell.getY()][cell.getX()];
        if (cell.isAlive()) {
            int cellAge = cell.getAge();
            polygonOfCell.setFill(CellColorComputer.computeCellColorBasedOnAge(cellAge));
        } else {
            polygonOfCell.setFill(Color.WHITE);
        }
    }

    private static Position convertCubeToAxial(Cube cube) {
        return new Position(cube.x, cube.y);
    }

    private static Cube convertAxialToCube(Position axial) {
        return new Cube(axial.x, axial.y, -axial.x - axial.y);
    }

    private static Cube cubeRound(Cube cube) {
        long x = Math.round(cube.x);
        long y = Math.round(cube.y);
        long z = Math.round(cube.z);

        double xDiff = Math.abs(x - cube.x);
        double yDiff = Math.abs(y - cube.y);
        double zDiff = Math.abs(z - cube.z);
        if (xDiff > yDiff && xDiff > zDiff) {
            x = -y - z;
        } else if (yDiff > zDiff) {
            y = -x - z;
        } else {
            z = -x - y;
        }

        return new Cube(x, y, z);
    }

    private static Position axialRound(Position axial) {
        return convertCubeToAxial(cubeRound(convertAxialToCube(axial)));
    }

    private static class Cube {
        public final double x;
        public final double y;
        public final double z;

        private Cube(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
