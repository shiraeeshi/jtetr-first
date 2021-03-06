package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class JShape implements Figure {
    private final Point center;
    private final Direction direction;

    public JShape(Point center, Direction direction) {
        this.center = center;
        this.direction = direction;
    }

    @Override
    public Figure rotateClockwise() {
        return new JShape(center, direction.rotateClockwise());
    }

    @Override
    public Figure moveLeft() {
        return new JShape(center.neighborLeft(), direction);
    }

    @Override
    public Figure moveRight() {
        return new JShape(center.neighborRight(), direction);
    }

    @Override
    public Figure descend() {
        return new JShape(center.neighborBelow(), direction);
    }

    @Override
    public Set<Point> getBricks() {
        switch (direction) {
            case UP:
                return set(
                        center.neighborAbove(),
                        center,
                        center.neighborBelow(),
                        center.neighborBelow().neighborLeft());
            case RIGHT:
                return set(
                        center.neighborRight(),
                        center,
                        center.neighborLeft(),
                        center.neighborLeft().neighborAbove());
            case DOWN:
                return set(
                        center.neighborBelow(),
                        center,
                        center.neighborAbove(),
                        center.neighborAbove().neighborRight());
            default:
                return set(
                        center.neighborLeft(),
                        center,
                        center.neighborRight(),
                        center.neighborRight().neighborBelow());
        }
    }
}
