package org.example.model;

public class HexagonalMap {
    private static final int[][][] NEIGHBOUR_MAP = new int[][][]{{{1, 1}, {1, 0}, {0, -1},
            {-1, 0}, {-1, 1}, {0, 1}},
            {{1, 0}, {1, -1}, {0, -1},
            {-1, -1}, {-1, 0}, {0, 1}}};
    private final Cell[][] map;
    private final int rows;
    private final int cols;

    public HexagonalMap(int rows, int cols) {
        map = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void initMapWithDeadCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                initCell(i, j);
            }
        }
    }

    public void setCell(Cell cell, int row, int col) {
        map[row][col] = cell;
    }

    private void initCell(int i, int j) {
        Cell initializedCell = new Cell(j, i, false);
        map[i][j] = initializedCell;
    }

    public Cell getCell(int row, int col) {
        return map[row][col];
    }

    public int computeNumberOfAliveAdjacentCells(int row, int col) {
        int numberOfDirections = 6;
        int aliveAdjacentCells = 0;
        Cell centerCell = getCell(row, col);

        for (int direction = 0; direction < numberOfDirections; direction++) {
            Cell adjacentCell = getAdjacentCell(centerCell.getY(), centerCell.getX(), direction);
            if (adjacentCell == null) {
                continue;
            }
            if (adjacentCell.isAlive()) {
                aliveAdjacentCells++;
            }
        }

        return aliveAdjacentCells;
    }

    private Cell getAdjacentCell(int centerCellRow, int centerCellCol, int direction) {
        int parity = centerCellCol & 1;
        int[] diff = NEIGHBOUR_MAP[parity][direction];

        int adjacentCellX = centerCellCol + diff[0];
        int adjacentCellY = centerCellRow + diff[1];

        if (isCellInRangeOfMap(adjacentCellY, adjacentCellX)) {
            return getCell(centerCellRow + diff[1], centerCellCol + diff[0]);
        }
        return null;
    }

    private boolean isCellInRangeOfMap(int row, int col) {
        return row < rows && row >= 0 && col < cols && col >= 0;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
