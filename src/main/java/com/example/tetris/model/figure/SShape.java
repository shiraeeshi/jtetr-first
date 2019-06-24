package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class SShape implements Figure {
    private final Point center;
    private final boolean isVertical;

    public SShape(Point center, boolean isVertical) {
        this.center = center;
        this.isVertical = isVertical;
    }

    @Override
    public Figure rotateClockwise() {
        return new SShape(center, !isVertical);
    }

    @Override
    public Figure moveLeft() {
        return new SShape(center.neighborLeft(), isVertical);
    }

    @Override
    public Figure moveRight() {
        return new SShape(center.neighborRight(), isVertical);
    }

    @Override
    public Figure descend() {
        return new SShape(center.neighborBelow(), isVertical);
    }

    @Override
    public Set<Point> getBricks() {
        if (isVertical) {
            return set(
                    center.neighborAbove(),
                    center,
                    center.neighborRight(),
                    center.neighborRight().neighborBelow());
        } else {
            return set(
                    center.neighborRight(),
                    center,
                    center.neighborBelow(),
                    center.neighborBelow().neighborLeft());
        }
    }
}
