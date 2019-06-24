package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class IShape implements Figure {

    private final Point center;
    private final boolean isVertical;

    public IShape(Point center, boolean isVertical) {
        this.center = center;
        this.isVertical = isVertical;
    }

    @Override
    public Figure rotateClockwise() {
        Point newCenter;
        if (isVertical) {
            newCenter = center.neighborBelow().neighborLeft();
        } else {
            newCenter = center.neighborAbove().neighborRight();
        }
        return new IShape(newCenter, !this.isVertical);
    }

    @Override
    public Figure moveLeft() {
        return new IShape(this.center.neighborLeft(), this.isVertical);
    }

    @Override
    public Figure moveRight() {
        return new IShape(this.center.neighborRight(), this.isVertical);
    }

    @Override
    public Figure descend() {
        return new IShape(this.center.neighborBelow(), this.isVertical);
    }

    @Override
    public Set<Point> getBricks() {
        if (isVertical) {
            return set(
                    center.neighborAbove(),
                    center,
                    center.neighborBelow(),
                    center.neighborBelow().neighborBelow());
        } else {
            return set(
                    center.neighborLeft(),
                    center,
                    center.neighborRight(),
                    center.neighborRight().neighborRight());
        }
    }
}
