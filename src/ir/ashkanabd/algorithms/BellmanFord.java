package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

public class BellmanFord extends Base {

    public static void bellmanFord(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        BellmanFord bellmanFord = new BellmanFord(map, startCell, stopCell, onEndListener);
        bellmanFord.startBellmanFord();
    }

    private BellmanFord(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startBellmanFord() {
        buildAdjacencyList();
        int startPoint = startCell.getX() * 20 + startCell.getY();
        int stopPoint = stopCell.getX() * 20 + stopCell.getY();
        int dist[] = new int[400];
        int path[] = new int[400];
        for (int i = 0; i < 400; i++) {
            dist[i] = MAX;
            path[i] = -1;
        }
        dist[startPoint] = 0;
        for (int i = 0; i < 400 - 1; i++) {
            for (int j = 0; j < 400; j++) {
                for (int k : adjacencyList[j]) {
                    if (dist[k] > dist[j] + 1) {
                        dist[k] = dist[j] + 1;
                        path[k] = j;
                    }
                }
            }
        }
        List<Cell> cellPath = new LinkedList<>();
        buildBellmanFordPath(cellPath, path, stopPoint);
        if (!cellPath.isEmpty()) {
            Collections.reverse(cellPath);
            cellPath.remove(0);
            changeCellColor(cellPath, "yellow");
            calculateTime(cellPath.size() + "");
        } else {
            calculateTime("0");
        }
        System.out.println("End");
    }

    private void buildBellmanFordPath(List<Cell> indexPath, int[] path, int s) {
        int k = path[s];
        if (k == -1) return;
        indexPath.add(map[k / 20][k % 20]);
        buildBellmanFordPath(indexPath, path, k);
    }
}
