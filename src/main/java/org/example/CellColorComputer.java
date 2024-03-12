package org.example;

import javafx.scene.paint.Color;

public final class CellColorComputer {
    private static final double RGB_MAX_VALUE = 255;

    private CellColorComputer() {
        // disable instantiation
    }

    public Color computeCellColorBasedOnAge(int age) {
        int r = (int) Math.min(RGB_MAX_VALUE, age / 4.0);
        int g = (int) Math.min(RGB_MAX_VALUE, age / 2.0);
        int b = (int) Math.min(RGB_MAX_VALUE, age);

        // compute color in the range 0.0 - 1.0
        return Color.color(r / RGB_MAX_VALUE, g / RGB_MAX_VALUE, b / RGB_MAX_VALUE);
    }
}