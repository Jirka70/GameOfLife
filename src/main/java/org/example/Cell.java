package org.example;

import java.util.Objects;

public class Cell {
    private boolean isAlive;
    private final int x;
    private final int y;
    private int age = 0;

    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        if (!alive) {
            age = 0;
        } else {
            if (!isAlive) {
                age = 0;
            } else {
                age++;
            }
        }
        isAlive = alive;
    }

    public int getAge() {
        return age;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cell cell)){
            return false;
        }
        return isAlive == cell.isAlive && x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlive, x, y);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "isAlive=" + isAlive +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
