package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.Set;
import java.util.HashSet;

public class FloydWarshall extends Base {

    public static void floydWarshall(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        FloydWarshall floydWarshall = new FloydWarshall(map, startCell, stopCell, onEndListener);
        floydWarshall.startFloydWarshall();
    }

    private FloydWarshall(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        super(map, startCell, stopCell, onEndListener);
    }

    private void startFloydWarshall() {
        buildAdjacencyMatrix();
        int D[][] = adjacencyMatrix.clone();
        int path[][] = new int[400][400];
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                path[i][j] = -1;
            }
        }
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                for (int k = 0; k < 400; k++) {
                    if (D[j][k] > D[j][i] + D[i][k]) {
                        D[j][k] = D[j][i] + D[i][k];
                        path[j][k] = i;
                    }
                }
            }
        }
        int startPoint = startCell.getX() * 20 + startCell.getY();
        int stopPoint = stopCell.getX() * 20 + stopCell.getY();
        Set<Cell> cellPath = new HashSet<>();
        buildFloydWarshallPath(cellPath, path, startPoint, stopPoint);
        if (!cellPath.isEmpty()) {
            changeCellColor(cellPath, "yellow");
            calculateTime(cellPath.size() + "");
        }else{
            calculateTime("0");
        }
        System.out.println("End");
    }

    private void buildFloydWarshallPath(Set<Cell> indexPath, int[][] path, int f, int s) {
        int k = path[f][s];
        if (k == -1) return;
        indexPath.add(map[k / 20][k % 20]);
        buildFloydWarshallPath(indexPath, path, f, k);
        buildFloydWarshallPath(indexPath, path, k, s);
    }
}
