package com.example.tetris.model.figure;

import com.example.tetris.model.Point;

import java.util.Set;

import static com.example.tetris.util.SetUtils.set;

public class TShape implements Figure {

    private final Point center;
    private final Direction direction;

    public TShape(Point center, Direction direction) {
        this.center = center;
        this.direction = direction;
    }

    @Override
    public Figure rotateClockwise() {
        return new TShape(center, direction.rotateClockwise());
    }

    @Override
    public Figure moveLeft() {
        return new TShape(center.neighborLeft(), direction);
    }

    @Override
    public Figure moveRight() {
        return new TShape(center.neighborRight(), direction);
    }

    @Override
    public Figure descend() {
        return new TShape(center.neighborBelow(), direction);
    }

    @Override
    public Set<Point> getBricks() {
       switch (direction) {
           case UP:
               return set(
                       center,
                       center.neighborRight(),
                       center.neighborLeft(),
                       center.neighborAbove());
           case RIGHT:
               return set(
                       center,
                       center.neighborAbove(),
                       center.neighborBelow(),
                       center.neighborRight());
           case DOWN:
               return set(
                       center,
                       center.neighborLeft(),
                       center.neighborRight(),
                       center.neighborBelow());
           default:
               return set(
                       center,
                       center.neighborBelow(),
                       center.neighborAbove(),
                       center.neighborLeft());
       }
    }
}
