package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.Set;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class BFS extends Base {

    public static void bfs(Cell[][] map, Cell startCell, Cell stopCell) {
        BFS bfs = new BFS(map, startCell, stopCell);
        bfs.startBFS();
    }

    private BFS(Cell[][] map, Cell startCell, Cell stopCell) {
        super(map, startCell, stopCell);
    }

    private void startBFS() {
        Set<Cell> blockList = new HashSet<>();
        Map<Cell, Cell> pathMap = new HashMap<>();
        Queue<Cell> mainQueue = new LinkedList<>();
        Cell current = startCell;
        do {
            blockList.add(current);
            mainQueue.poll();
            List<Cell> neighbors = getNeighbors(current);
            for (Cell c : neighbors) {
                if (!blockList.contains(c) && !mainQueue.contains(c)) {
                    mainQueue.add(c);
                    pathMap.put(c, current);
                }
            }
            changeCellColor(mainQueue, "aqua");
            current = mainQueue.peek();
            if (current.equals(stopCell)) {
                break;
            }
        } while (!mainQueue.isEmpty());
        if (current.equals(stopCell)) {
            List<Cell> path = buildPath(pathMap);
            Collections.reverse(path);
            changeCellColor(path, "yellow");
        }
        System.out.println("End");
    }
}
