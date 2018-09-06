package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Collections;

public class DFS extends Base {

    public static void dfs(Cell[][] map, Cell startCell, Cell stopCell) {
        DFS dfs = new DFS(map, startCell, stopCell);
        dfs.startDFS();
    }

    private DFS(Cell[][] map, Cell startCell, Cell stopCell) {
        super(map, startCell, stopCell);
    }

    private void DFSStack(Map<Cell, Cell> pathMap, Cell current, Set<Cell> blockSet) {
        blockSet.add(current);
        for (Cell c : getNeighbors(current)) {
            if (blockSet.contains(stopCell)) {
                return;
            }
            if (!blockSet.contains(c)) {
                pathMap.put(c, current);
                changeCellColor(Collections.singletonList(c), "aqua");
                if (blockSet.contains(stopCell)) {
                    return;
                }
                DFSStack(pathMap, c, blockSet);
            }
        }
    }

    private void startDFS() {
        Map<Cell, Cell> pathMap = new HashMap<>();
        Set<Cell> blockSet = new HashSet<>();
        Cell current = startCell;
        DFSStack(pathMap, current, blockSet);
        if (pathMap.containsKey(stopCell)) {
            List<Cell> path = buildPath(pathMap);
            Collections.reverse(path);
            changeCellColor(path, "yellow");
        }
        System.out.println("End");
    }

    private void stopDFS(Map<Cell, Cell> pathMap) {

    }
}
