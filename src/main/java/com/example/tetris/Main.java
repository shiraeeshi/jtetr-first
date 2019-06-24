package com.example.tetris;

import com.example.tetris.model.Arena;
import com.example.tetris.model.figure.Figure;
import com.example.tetris.model.level.Level;
import com.example.tetris.model.level.NextFigureProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class Main {
    public static void main(String[] args) {
        final Arena arena = new Arena();
        final NextFigureProvider level = new Level();
        arena.setFigureIfPossible(level.nextFigure());
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable beeper = new Runnable() {
            @Override
            public void run() {
                if (!arena.descend()) {
                    Figure nextFigure = level.nextFigure();
                    if (!arena.setFigureIfPossible(nextFigure)) {
                        System.out.println("Game over");
                        System.exit(0);
                    }
                }
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 1, SECONDS);
    }
}
