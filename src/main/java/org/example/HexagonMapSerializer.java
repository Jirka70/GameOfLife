package org.example;

import java.io.*;
import java.nio.file.Path;

public class HexagonMapSerializer {
    private final HexagonalMap map;
    public HexagonMapSerializer(HexagonalMap hexagonalMap) {
        map = hexagonalMap;
    }

    public void serializeMap(Path destinationFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile.toFile()))) {
            writeMap(writer);
        }

    }

    private void writeMap(Writer writer) throws IOException {
        int rows = map.getRows();
        int cols = map.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = map.getCell(i,j);
                writeCell(writer, currentCell);
            }
            writer.write("\n");
        }
    }

    private void writeCell(Writer writer, Cell cell) throws IOException {
        writer.write(cell.isAlive() ? "1" : "0");
    }
}
