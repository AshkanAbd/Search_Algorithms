package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Base {

    public static boolean stopRequest = false;

    protected OnEndListener onEndListener;
    protected long time;
    protected static int MAX = 1000000;
    protected boolean stop = false;
    protected Cell[][] map;
    protected Map<Cell, Cell> pathMap;
    protected Cell startCell, stopCell;
    protected int[][] proximityMatrix;

    private int colored = 0;

    Base(Cell[][] map, Cell startCell, Cell stopCell, OnEndListener onEndListener) {
        this.startCell = startCell;
        this.stopCell = stopCell;
        this.map = map;
        this.onEndListener = onEndListener;
        stopRequest = false;
        time = System.currentTimeMillis();
    }

    protected void buildProximityMatrix() {
        proximityMatrix = new int[400][400];
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                proximityMatrix[i][j] = MAX;
                if (i == j) {
                    proximityMatrix[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell c = map[i][j];
                for (Cell cell : getNeighbors(c)) {
                    if (!c.isWall() && !cell.isWall()) {
                        proximityMatrix[cell.getX() * 20 + cell.getY()][c.getX() * 20 + c.getY()] = 1;
                        proximityMatrix[c.getX() * 20 + c.getY()][cell.getX() * 20 + cell.getY()] = 1;
                    }
                }
            }
        }
    }

    protected void changeCellColor(Iterable<Cell> cells, String color) {
        try {
            for (Cell cell : cells) {
                if (stopRequest) stop = true;
                if (stop) return;
                if (!cell.getStyle().contains("-fx-background-color: " + color)) {
                    if (!cell.isStop() && !cell.isStart()) {
                        Platform.runLater(() -> cell.setStyle("-fx-background-color: " + color));
                        Thread.sleep(75);
                        colored++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void calculateTime() {
        long endTime = System.currentTimeMillis() - time;
        endTime = endTime - (75 * colored);
        onEndListener.onEnd("Time : " + endTime + " ms");
    }

    protected Cell getUp(Cell cell) throws Exception {
        return map[cell.getX() - 1][cell.getY()];
    }

    protected Cell getDown(Cell cell) throws Exception {
        return map[cell.getX() + 1][cell.getY()];
    }

    protected Cell getRight(Cell cell) throws Exception {
        return map[cell.getX()][cell.getY() + 1];
    }

    protected Cell getLeft(Cell cell) throws Exception {
        return map[cell.getX()][cell.getY() - 1];
    }

    protected List<Cell> getNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        try {
            neighbors.add(getUp(cell));
        } catch (Exception ignored) {
        }
        try {
            neighbors.add(getRight(cell));
        } catch (Exception ignored) {
        }
        try {
            neighbors.add(getDown(cell));
        } catch (Exception ignored) {
        }
        try {
            neighbors.add(getLeft(cell));
        } catch (Exception ignored) {
        }
        return neighbors;
    }

    protected List<Cell> getFreeNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        for (Cell c : getNeighbors(cell))
            if (!c.isWall())
                neighbors.add(c);
        return neighbors;
    }

    protected List<Cell> buildPath() {
        List<Cell> path = new ArrayList<>();
        Cell cell = stopCell;
        while (!cell.equals(startCell)) {
            cell = pathMap.get(cell);
            path.add(cell);
        }
        path.remove(path.size() - 1);
        return path;
    }

    protected static class Pair<A, B> {
        private A a;
        private B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }

        @Override
        public int hashCode() {
            return a.hashCode() * b.hashCode();
        }

        @Override
        public String toString() {
            return "< " + a.toString() + " , " + b.hashCode() + " >";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                Pair p = (Pair) obj;
                return p.a.equals(a);
            }
            return false;
        }
    }
}
