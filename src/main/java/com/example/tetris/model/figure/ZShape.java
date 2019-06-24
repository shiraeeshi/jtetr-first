package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class ZShape implements Figure {
    private final Point center;
    private final boolean isVertical;

    public ZShape(Point center, boolean isVertical) {
        this.center = center;
        this.isVertical = isVertical;
    }

    @Override
    public Figure rotateClockwise() {
        return new ZShape(center, !isVertical);
    }

    @Override
    public Figure moveLeft() {
        return new ZShape(center.neighborLeft(), isVertical);
    }

    @Override
    public Figure moveRight() {
        return new ZShape(center.neighborRight(), isVertical);
    }

    @Override
    public Figure descend() {
        return new ZShape(center.neighborBelow(), isVertical);
    }

    @Override
    public Set<Point> getBricks() {
        if (isVertical) {
            return set(
                    center.neighborBelow(),
                    center,
                    center.neighborRight(),
                    center.neighborRight().neighborAbove());
        } else {
            return set(
                    center.neighborLeft(),
                    center,
                    center.neighborBelow(),
                    center.neighborBelow().neighborRight());
        }
    }
}
