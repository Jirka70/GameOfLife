package org.example.control;

import org.example.model.Cell;
import org.example.model.HexagonalMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MapLoader {
    private final Path configFileToLoad;

    public MapLoader(Path fileWithSerializedMap) {
        configFileToLoad = fileWithSerializedMap;
    }

    public void loadMapContent(HexagonalMap map) throws ParsingException {
        try {
            List<String> lines = Files.readAllLines(configFileToLoad);

            int cols = lines.get(0).length();
            int rows = lines.size() - 1; // neglecting the last empty line

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    char currentCellAliveIndicator = lines.get(i).charAt(j);
                    Cell currentCell = map.getCell(i,j);
                    currentCell.setAlive(currentCellAliveIndicator == '1');
                }
            }
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
