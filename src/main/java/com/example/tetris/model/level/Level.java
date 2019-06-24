package com.example.tetris.model.level;

import com.example.tetris.model.Arena;
import com.example.tetris.model.Point;
import com.example.tetris.model.figure.*;

import java.util.*;

public class Level implements NextFigureProvider {
    private static List<Figure> figuresByCode = new ArrayList<>();

    private int[] levelFigures = new int[10];
    private int currentFigureIndex = 0;
    private Random r = new Random();

    static {
        Point center = new Point(Arena.ARENA_WIDTH / 2 - 1, Arena.ARENA_HEIGHT - 2);
        figuresByCode.add(new IShape(center.neighborBelow().neighborLeft(), false));
        figuresByCode.add(new IShape(center, true));
        figuresByCode.add(new IShape(center.neighborBelow().neighborLeft(), false));
        figuresByCode.add(new IShape(center, true));
        figuresByCode.add(new JShape(center, Figure.Direction.UP));
        figuresByCode.add(new JShape(center, Figure.Direction.RIGHT));
        figuresByCode.add(new JShape(center, Figure.Direction.DOWN));
        figuresByCode.add(new JShape(center, Figure.Direction.LEFT));
        figuresByCode.add(new LShape(center, Figure.Direction.UP));
        figuresByCode.add(new LShape(center, Figure.Direction.RIGHT));
        figuresByCode.add(new LShape(center, Figure.Direction.DOWN));
        figuresByCode.add(new LShape(center, Figure.Direction.LEFT));
        figuresByCode.add(new OShape(center.neighborLeft()));
        figuresByCode.add(new OShape(center.neighborLeft()));
        figuresByCode.add(new OShape(center.neighborLeft()));
        figuresByCode.add(new OShape(center.neighborLeft()));
        figuresByCode.add(new SShape(center, true));
        figuresByCode.add(new SShape(center, false));
        figuresByCode.add(new SShape(center, true));
        figuresByCode.add(new SShape(center, false));
        figuresByCode.add(new TShape(center, Figure.Direction.UP));
        figuresByCode.add(new TShape(center, Figure.Direction.RIGHT));
        figuresByCode.add(new TShape(center, Figure.Direction.DOWN));
        figuresByCode.add(new TShape(center, Figure.Direction.LEFT));
        figuresByCode.add(new ZShape(center, true));
        figuresByCode.add(new ZShape(center, false));
        figuresByCode.add(new ZShape(center, true));
        figuresByCode.add(new ZShape(center, false));
    }

    public Level() {
        reset();
    }

    @Override
    public Figure nextFigure() {
        if (currentFigureIndex == 10) {
            reset();
        }
        int figureCode = levelFigures[currentFigureIndex];
        currentFigureIndex++;
        return figureByCode(figureCode);
    }

    private void reset() {
        for (int i=0; i < 10; i++) {
            levelFigures[i] = r.nextInt(figuresByCode.size());
        }
        currentFigureIndex = 0;
    }

    private Figure figureByCode(int figureCode) {
        return figuresByCode.get(figureCode);
    }
}
