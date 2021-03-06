package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Collections;

public class DFS extends Base {

    public static void dfs(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        DFS dfs = new DFS(map, startCell, stopCell, onEndListener);
        dfs.startDFS();
    }

    private DFS(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void DFSStack(Map<Cell, Cell> pathMap, Cell current, Set<Cell> blockSet) {
        if (stopRequest) stop = true;
        if (stop) return;
        blockSet.add(current);
        for (Cell c : getFreeNeighbors(current)) {
            if (pathMap.containsKey(stopCell)) {
                return;
            }
            if (!blockSet.contains(c)) {
                pathMap.put(c, current);
                changeCellColor(Collections.singletonList(c), "aqua");
                if (pathMap.containsKey(stopCell)) {
                    return;
                }
                DFSStack(pathMap, c, blockSet);
            }
        }
    }

    private void startDFS() {
        pathMap = new HashMap<>();
        Set<Cell> blockSet = new HashSet<>();
        Cell current = startCell;
        DFSStack(pathMap, current, blockSet);
        showPath();
        System.out.println("End");
    }
}
