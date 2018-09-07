package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.*;

public class AStar extends Base {

    public static void aStar(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        AStar aStar = new AStar(map, startCell, stopCell, onEndListener);
        aStar.startAStar();
    }

    private AStar(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startAStar() {
        List<Base.Pair<Cell, Integer>> openList = new LinkedList<>();
        Map<Cell, Integer> gScore = new HashMap<>();
        List<Cell> closeList = new LinkedList<>();
        pathMap = new HashMap<>();
        gScore.put(startCell, 0);
        openList.add(new Pair<>(startCell, heuristic(startCell)));
        Cell current;
        while (!openList.isEmpty()) {
            if (stopRequest) stop = true;
            if (stop) return;
            openList.sort(Comparator.comparingInt(Base.Pair::getB));
            current = openList.get(0).getA();
            closeList.add(current);
            openList.remove(0);
            for (Cell cell : getFreeNeighbors(current)) {
                if (!closeList.contains(cell)) {
                    if (!openList.contains(new Pair<>(cell, 0))) {
                        gScore.put(cell, gScore.get(current) + 1);
                        openList.add(new Pair<>(cell, gScore.get(current) + 1 + heuristic(cell)));
                        pathMap.put(cell, current);
                    } else {
                        if (openList.get(openList.indexOf(new Pair<>(cell, 0))).getB()
                                > (gScore.get(current) + 1 + heuristic(cell))) {
                            openList.get(openList.indexOf(new Pair<>(cell, 0)))
                                    .setB(gScore.get(current) + 1 + heuristic(cell));
                            pathMap.replace(cell, current);
                            gScore.replace(cell, gScore.get(current) + 1);
                        }
                    }
                }
            }
            changeCellColor(pathMap.keySet(), "aqua");
            if (pathMap.containsKey(stopCell)) {
                break;
            }
        }
        showPath();
        System.out.println("End");
    }
}
