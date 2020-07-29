package com.example.tetris;

import com.example.tetris.model.Arena;
import com.example.tetris.model.figure.Figure;
import com.example.tetris.model.level.Level;
import com.example.tetris.model.level.NextFigureProvider;

import java.util.Observer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

public class Tetris {
    private final Arena arena = new Arena();
    private final NextFigureProvider level = new Level();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> beeperHandle;

    public void setView(Observer view) {
        arena.addObserver(view);
    }

    public void start() {
        arena.setFigureIfPossible(level.nextFigure());
        startBeeper();
    }

    private void startBeeper() {
        Runnable beeper = new Runnable() {
            @Override
            public void run() {
                //System.out.println("step");
                Tetris.this.step();
            }
        };
        // this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 1, SECONDS);
        this.beeperHandle = scheduler.scheduleAtFixedRate(beeper, 100, 300, MILLISECONDS);
    }

    private boolean isRunning() {
        return beeperHandle != null && !beeperHandle.isCancelled();
    }

    private void cancelBeeper() {
        beeperHandle.cancel(true);
    }

    private void updateFigure() {
        Figure nextFigure = level.nextFigure();
        if (!arena.setFigureIfPossible(nextFigure)) {
            System.out.println("Game over");
            if (!beeperHandle.isCancelled()) {
                beeperHandle.cancel(true);
            }
            System.exit(0);
        } else {
            //System.out.println("new figure");
            arena.notifyObservers();
        }
    }

    public synchronized void step() {
        boolean descended = arena.descend();
        if (descended) {
            arena.notifyObservers();
            return;
        }
        arena.fixCurrentFigure();
        if (arena.hasFullRows()) {
            cancelBeeper();
            // display full rows
            arena.notifyObservers();
            Runnable command = new Runnable() {
                @Override
                public void run() {
                	synchronized (Tetris.this) {
						arena.removeFullRows();
						startBeeper();
						updateFigure();
                	}
                }
            };
            scheduler.schedule(command, 100, MILLISECONDS);
        } else {
            updateFigure();
        }
    }

    public synchronized void moveRight() {
        arena.moveRight();
        arena.notifyObservers();
    }

    public synchronized void moveLeft() {
        arena.moveLeft();
        arena.notifyObservers();
    }

    public synchronized void rotateClockwise() {
        arena.rotateClockwise();
        arena.notifyObservers();
    }

    public synchronized void descend() {
        arena.descend();
        arena.notifyObservers();
    }

    public synchronized void pauseOrResume() {
        if (isRunning()) {
            cancelBeeper();
        } else {
            startBeeper();
        }
    }
}
