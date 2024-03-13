package org.example.util;

import javafx.scene.paint.Color;

public final class CellColorComputer {
    private static final double RGB_MAX_VALUE = 255;

    private CellColorComputer() {
        // disable instantiation
    }

    public static Color computeCellColorBasedOnAge(int age) {
        int r = (int) Math.min(RGB_MAX_VALUE, 10*age);
        int g = (int) Math.min(RGB_MAX_VALUE, 20*age);
        int b = (int) Math.min(RGB_MAX_VALUE, 40*age);

        // compute color in the range 0.0 - 1.0
        return Color.color(r / RGB_MAX_VALUE, g / RGB_MAX_VALUE, b / RGB_MAX_VALUE);
    }
}