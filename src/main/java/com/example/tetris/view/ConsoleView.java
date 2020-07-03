package com.example.tetris.view;

import com.example.tetris.model.Point;
import com.example.tetris.model.figure.Figure;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.Ansi;

import com.example.tetris.model.Arena;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ConsoleView implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        Arena arena = (Arena)o;
        printArena(arena);
    }

    private void printArena(Arena arena) {
        Ansi ansi = Ansi.ansi().saveCursorPosition().eraseScreen();
        ansi = drawbox(ansi, 0, 0, Arena.ARENA_WIDTH + 2, Arena.ARENA_HEIGHT + 2);
        ansi = drawFigure(ansi, arena.getCurrentFigure());
        ansi = drawBricks(ansi, arena.getBricksOnTheFloor());
        if (arena.hasFullRows()) {
            ansi = drawFullRows(ansi, arena.findFullRowsIndices());
        }
        AnsiConsole.out.println(ansi);
    }

    private Ansi drawFigure(Ansi ansi, Figure figure) {
        ansi = ansi.bold();
        for (Point p : figure.getBricks()) {
            int x = p.getX();
            int y = Arena.ARENA_HEIGHT - p.getY();
            ansi = ansi
                    .cursor(y + 3, x + 2)
                    .a("*");
        }
        return ansi.reset().restoreCursorPosition();
    }

    private Ansi drawBricks(Ansi ansi, List<Point> bricks) {
        for (Point brick : bricks) {
            int x = brick.getX();
            int y = Arena.ARENA_HEIGHT - brick.getY();
            ansi = ansi
                    .cursor(y + 3, x + 2)
                    .a("*");
        }
        return ansi.reset().restoreCursorPosition();
    }

    private Ansi drawFullRows(Ansi ansi, List<Integer> fullRowsIndices) {
        for (int rowIndex : fullRowsIndices) {
            for (int columnIndex = 0; columnIndex < Arena.ARENA_WIDTH; columnIndex++) {
                ansi = ansi
                        .cursor(Arena.ARENA_HEIGHT - rowIndex + 3, columnIndex + 2)
                        .a("-");
            }
        }
        return ansi.reset().restoreCursorPosition();
    }

    private Ansi drawbox(Ansi ansi, int x0, int y0, int w, int h) {
        if (w <= 1 && h <= 1) {
            throw new IllegalArgumentException(
                    String.format("w > 1, h > 1 requirement failed. w: {0}, h: {1}"));
        }
        String topStr = "┌".concat(repeatString("─", w - 2)).concat("┐");
        String wallStr = "│".concat(repeatString(" ", w - 2)).concat("│");
        String bottomStr = "└".concat(repeatString("─",  w - 2)).concat("┘");
        ansi = ansi.cursor(y0 + 2, x0).a(topStr);
//        for (int i = 0; i < h - 1; i++) {
//            ansi = ansi.cursor(y0 + i + 1, x0).a(wallStr);
//        }
//        return ansi.cursor(y0 + h - 1, x0).a(bottomStr);
        for (int i = 0; i < h; i++) {
            ansi = ansi.cursor(y0 + 3 + i, x0).a(wallStr);
            //ansi = ansi.cursor(y0 + 2 + i, x0 + 2).a(y0 + 1 + i);
        }
        return ansi.cursor(y0 + 2 + h, x0).a(bottomStr);
    }

    private String repeatString(String str, int n) {
        int counter = n;
        StringBuilder sb = new StringBuilder();
        while (counter > 0) {
            sb.append(str);
            counter--;
        }
        return sb.toString();
    }
}
