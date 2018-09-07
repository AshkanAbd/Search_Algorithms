package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
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
        buildProximityMatrix();
        int D[][] = proximityMatrix.clone();
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
        Set<Integer> indexPath = new HashSet<>();
        buildFloydPath(indexPath, path, startPoint, stopPoint);
        if (!indexPath.isEmpty()) {
            List<Integer> iList = new ArrayList<>();
            iList.addAll(indexPath);
            List<Cell> cellPath = getCellFromIndex(iList);
            changeCellColor(cellPath, "yellow");
            calculateTime(iList.size() + "");
        }else{
            calculateTime("0");
        }
        System.out.println("End");
    }

    private void buildFloydPath(Set<Integer> indexPath, int[][] path, int f, int s) {
        int k = path[f][s];
        if (k == -1) return;
        indexPath.add(k);
        buildFloydPath(indexPath, path, f, k);
        buildFloydPath(indexPath, path, k, s);
    }

    private List<Cell> getCellFromIndex(List<Integer> path) {
        List<Cell> cellPath = new ArrayList<>();
        for (Integer i : path) {
            cellPath.add(map[i / 20][i % 20]);
        }
        return cellPath;
    }
}
