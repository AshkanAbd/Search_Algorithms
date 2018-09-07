package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.*;

public class BestFirstSearch extends Base {

    public static void bestFirstSearch(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        BestFirstSearch bestFirstSearch = new BestFirstSearch(map, startCell, stopCell, onEndListener);
        bestFirstSearch.startBestFirst();
    }

    private BestFirstSearch(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startBestFirst() {
        List<Base.Pair<Cell, Integer>> openList = new LinkedList<>();
        Map<Cell, Integer> closeList = new HashMap<>();
        pathMap = new HashMap<>();
        Cell current;
        openList.add(new Pair<>(startCell, heuristic(startCell)));
        while (!openList.isEmpty()) {
            if (stopRequest) stop = true;
            if (stop) return;
            openList.sort(Comparator.comparingInt(Base.Pair<Cell, Integer>::getB));
            current = openList.get(0).getA();
            closeList.put(current, openList.get(0).getB());
            openList.remove(0);
            for (Cell cell : getFreeNeighbors(current)) {
                if (!closeList.containsKey(cell)) {
                    if (!openList.contains(new Pair<>(cell, 0))) {
                        openList.add(new Pair<>(cell, heuristic(cell)));
                        pathMap.put(cell, current);
                    }
                } else {
                    if (heuristic(current) + 1 < closeList.get(cell)) {
                        pathMap.replace(cell, current);
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
