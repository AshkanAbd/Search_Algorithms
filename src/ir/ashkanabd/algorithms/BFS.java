package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.Set;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS extends Base {

    public static void bfs(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        BFS bfs = new BFS(map, startCell, stopCell, onEndListener);
        bfs.startBFS();
    }

    private BFS(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startBFS() {
        Set<Cell> blockList = new HashSet<>();
        pathMap = new HashMap<>();
        Queue<Cell> mainQueue = new LinkedList<>();
        Cell current = startCell;
        do {
            if (stopRequest) stop = true;
            if (stop) return;
            blockList.add(current);
            mainQueue.poll();
            for (Cell c : getFreeNeighbors(current)) {
                if (!blockList.contains(c) && !mainQueue.contains(c)) {
                    mainQueue.add(c);
                    pathMap.put(c, current);
                }
            }
            changeCellColor(mainQueue, "aqua");
            current = mainQueue.peek();
            if (current == null || pathMap.containsKey(stopCell)) {
                break;
            }
        } while (!mainQueue.isEmpty());
        showPath();
        System.out.println("End");
    }
}
