package com.example.tetris.model;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point neighborLeft() {
        return new Point(this.x - 1, this.y);
    }

    public Point neighborRight() {
        return new Point(this.x + 1, this.y);
    }

    public Point neighborAbove() {
        return new Point(this.x, this.y + 1);
    }

    public Point neighborBelow() {
        return new Point(this.x, this.y - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
