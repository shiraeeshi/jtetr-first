package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

public interface Figure {

    Figure rotateClockwise();
    Figure moveLeft();
    Figure moveRight();
    Figure descend();
    Set<Point> getBricks();

    enum Direction {
        LEFT, RIGHT, UP, DOWN;

        Direction rotateClockwise() {
            switch (this) {
                case UP: return RIGHT;
                case RIGHT: return DOWN;
                case DOWN: return LEFT;
                default: return UP;
            }
        }

        Direction rotateCounterClockwise() {
            switch (this) {
                case UP: return LEFT;
                case LEFT: return DOWN;
                case DOWN: return RIGHT;
                default: return UP;
            }
        }
    }
}
