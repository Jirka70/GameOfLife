package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MapLoader {
    private final Path configFileToLoad;

    public MapLoader(Path fileWithSerializedMap) {
        configFileToLoad = fileWithSerializedMap;
    }

    public HexagonalMap loadMap() throws ParsingException {
        try {
            List<String> lines = Files.readAllLines(configFileToLoad);

            int cols = lines.get(0).length();
            int rows = lines.size() - 1; // neglecting the last empty line

            HexagonalMap loadedMap = new HexagonalMap(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char currentCell = lines.get(i).charAt(j);
                    Cell loadedCell = new Cell(j, i, currentCell == '1');
                    loadedMap.setCell(loadedCell, i, j);
                }
            }

            return loadedMap;
        } catch (Exception e) {
            throw new ParsingException("Selected file " + configFileToLoad + " does not contain configuration of map",
                    e);
        }
    }

    public static class ParsingException extends Exception {
        public ParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
