package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class OShape implements Figure {

    private final Point center;

    public OShape(Point center) {
        this.center = center;
    }

    @Override
    public Figure rotateClockwise() {
        return this;
    }

    @Override
    public Figure moveLeft() {
        return new OShape(center.neighborLeft());
    }

    @Override
    public Figure moveRight() {
        return new OShape(center.neighborRight());
    }

    @Override
    public Figure descend() {
        return new OShape(center.neighborBelow());
    }

    @Override
    public Set<Point> getBricks() {
        return set(
                center,
                center.neighborRight(),
                center.neighborBelow(),
                center.neighborRight().neighborBelow());
    }
}
