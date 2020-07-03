package com.example.tetris.model;

import com.example.tetris.model.figure.Figure;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Arena extends Observable {
    public static final int ARENA_WIDTH = 20;
    public static final int ARENA_HEIGHT = 20;

    private Figure figure;
    private boolean[][] cells = new boolean[ARENA_HEIGHT][ARENA_WIDTH];

    public Figure getCurrentFigure() {
        return figure;
    }

    public List<Point> getBricksOnTheFloor() {
        List<Point> bricks = new LinkedList<>();
        for (int rowIndex=0; rowIndex<ARENA_HEIGHT; rowIndex++) {
            boolean[] row = cells[rowIndex];
            for (int columnIndex=0; columnIndex<ARENA_WIDTH; columnIndex++) {
                if (row[columnIndex]) {
                    bricks.add(new Point(columnIndex, rowIndex));
                }
            }
        }
        return bricks;
    }

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

    public void fixCurrentFigure() {
        for (Point figurePoint: figure.getBricks()) {
            int x = figurePoint.getX();
            int y = figurePoint.getY();
            cells[y][x] = true;
        }
        setChanged();
    }

    public boolean setFigureIfPossible(Figure newFigure) {
        if (figureIsPossible(newFigure)) {
            this.figure = newFigure;
            setChanged();
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

    public boolean hasFullRows() {
        return !findFullRowsIndices().isEmpty();
    }

    public List<Integer> findFullRowsIndices() {
        List<Integer> fullRows = new LinkedList<>();
        outer: for (int rowIndex = 0; rowIndex < ARENA_HEIGHT; rowIndex++) {
            boolean[] row = cells[rowIndex];
            for (int cell = 0; cell < ARENA_WIDTH; cell++) {
                if (!row[cell]) {
                    continue outer;
                }
            }
            fullRows.add(rowIndex);
        }
        return fullRows;
    }

    public void removeFullRows() {
        List<Integer> notFullRows = new LinkedList<>();
        outer: for (int rowIndex = 0; rowIndex < ARENA_HEIGHT; rowIndex++) {
            boolean[] row = cells[rowIndex];
            for (int cell = 0; cell < ARENA_WIDTH; cell++) {
                if (!row[cell]) {
                    notFullRows.add(rowIndex);
                    continue outer;
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
        setChanged();
    }
}
