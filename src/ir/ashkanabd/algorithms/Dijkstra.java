package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.*;

public class Dijkstra extends Base {

    public static void dijkstra(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        Dijkstra dijkstra = new Dijkstra(map, startCell, stopCell, onEndListener);
        dijkstra.startDijkstra();
    }

    private Dijkstra(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startDijkstra() {
        Set<Cell> blockSet = new HashSet<>();
        Map<Cell, Integer> dMap = new HashMap<>();
        pathMap = new HashMap<>();
        LinkedList<Pair<Cell, Integer>> mainQueue = new LinkedList<>();
        Cell current = startCell;
        dMap.put(current, 0);
        do {
            if (stopRequest) stop = true;
            if (stop) return;
            blockSet.add(current);
            mainQueue.pollFirst();
            for (Cell cell : getFreeNeighbors(current)) {
                if (!blockSet.contains(cell)) {
                    if (!mainQueue.contains(new Pair<>(cell, 0))) {
                        mainQueue.addLast(new Pair<>(cell, dMap.get(current) + 1));
                    }
                    if (dMap.containsKey(cell)) {
                        pathMap.replace(cell, Integer.min(dMap.get(cell),
                                mainQueue.getLast().getB()) == mainQueue.getLast().getB() ? current : pathMap.get(cell));
                        dMap.replace(cell, Integer.min(dMap.get(cell), mainQueue.getLast().getB()));
                    } else {
                        dMap.put(cell, mainQueue.getLast().getB());
                        pathMap.put(cell, current);
                    }
                }
            }
            mainQueue.sort(Comparator.comparingInt(Pair::getB));
            changeCellColor(dMap.keySet(), "aqua");
            current = mainQueue.peekFirst().getA();
            if (current == null || pathMap.containsKey(stopCell)) {
                break;
            }
        } while (!mainQueue.isEmpty());
        showPath();
        System.out.println("End");
    }
}
