package com.example.tetris.model;

import com.example.tetris.model.figure.Figure;

import java.util.LinkedList;
import java.util.List;

public class Arena {
    public static final int ARENA_WIDTH = 20;
    public static final int ARENA_HEIGHT = 30;

    private Figure figure;
    private boolean[][] cells = new boolean[ARENA_HEIGHT][ARENA_WIDTH];

    // if can move right, move right
    // if can move left, move left
    // if can rotate clockwise, rotate clockwise
    // if can rotate counter-clockwise, rotate counter-clockwise
    // if can descend, descend
    // if can't descend, start with a new figure

    public void moveRight() {
        setFigureIfPossible(figure.moveRight());
    }

    public void moveLeft() {
        setFigureIfPossible(figure.moveLeft());
    }

    public void rotateClockwise() {
        setFigureIfPossible(figure.rotateClockwise());
    }

    public boolean descend() {
        return setFigureIfPossible(figure.descend());
    }

    public boolean setFigureIfPossible(Figure newFigure) {
        if (figureIsPossible(newFigure)) {
            this.figure = newFigure;
            return true;
        }
        return false;
    }

    public boolean figureIsPossible(Figure fig) {
        for (Point figurePoint: fig.getBricks()) {
            int x = figurePoint.getX();
            int y = figurePoint.getY();
            if (x < 0 || x > ARENA_WIDTH - 1) return false;
            if (y < 0 || y > ARENA_HEIGHT - 1) return false;
            if (cells[y][x]) return false;
        }
        return true;
    }

    public List<Integer> findFullRowsIndices() {
        List<Integer> fullRows = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < ARENA_HEIGHT; rowIndex++) {
            boolean[] row = cells[rowIndex];
            for (int cell = 0; cell < ARENA_WIDTH; cell++) {
                if (!row[cell]) {
                    continue;
                }
            }
            fullRows.add(rowIndex);
        }
        return fullRows;
    }

    public void removeFullRows() {
        List<Integer> notFullRows = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < ARENA_HEIGHT; rowIndex++) {
            boolean[] row = cells[rowIndex];
            for (int cell = 0; cell < ARENA_WIDTH; cell++) {
                if (!row[cell]) {
                    notFullRows.add(rowIndex);
                    continue;
                }
            }
        }
        if (notFullRows.size() == ARENA_HEIGHT) {
            return;
        }
        for (int i = 0; i < notFullRows.size(); i++) {
            int newRowIndex = notFullRows.get(i);
            cells[i] = cells[newRowIndex];
        }
        for (int i = notFullRows.size(); i < ARENA_HEIGHT; i++) {
            cells[i] = new boolean[ARENA_WIDTH];
        }
    }
}
