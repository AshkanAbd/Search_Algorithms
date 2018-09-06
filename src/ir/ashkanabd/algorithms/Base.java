package ir.ashkanabd.algorithms;

import ir.ashkanabd.Cell;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Base {

    protected Cell[][] map;
    protected Cell startCell, stopCell;

    Base(Cell[][] map, Cell startCell, Cell stopCell) {
        this.startCell = startCell;
        this.stopCell = stopCell;
        this.map = map;
    }

    protected void changeCellColor(Iterable<Cell> cells, String color) {
        try {
            for (Cell cell : cells) {
                if (!cell.getStyle().contains("-fx-background-color: " + color)) {
                    if (!cell.isStop() && !cell.isStart()) {
                        Platform.runLater(() -> cell.setStyle("-fx-background-color: " + color));
                        Thread.sleep(75);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Cell c;
        try {
            if (!(c = getUp(cell)).isWall()) {
                neighbors.add(c);
            }
        } catch (Exception ignored) {
        }
        try {
            if (!(c = getRight(cell)).isWall()) {
                neighbors.add(c);
            }
        } catch (Exception ignored) {
        }
        try {
            if (!(c = getDown(cell)).isWall())
                neighbors.add(c);
        } catch (Exception ignored) {
        }
        try {
            if (!(c = getLeft(cell)).isWall())
                neighbors.add(c);
        } catch (Exception ignored) {
        }
        return neighbors;
    }

    protected List<Cell> buildPath(Map<Cell, Cell> pathMap) {
        List<Cell> path = new ArrayList<>();
        Cell cell = stopCell;
        while (!cell.equals(startCell)) {
            cell = pathMap.get(cell);
            path.add(cell);
        }
        path.remove(path.size() - 1);
        return path;
    }
}
