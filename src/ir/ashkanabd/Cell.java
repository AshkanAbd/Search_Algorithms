package ir.ashkanabd;

import javafx.scene.control.Label;

public class Cell extends Label {
    private Integer x, y;
    private boolean wall = false;
    private boolean start = false;
    private boolean stop = false;

    public Cell(int y, int x) {
        super();
        this.x = x;
        this.y = y;
    }

    public Cell(int y, int x, String text) {
        super(text);
        this.x = x;
        this.y = y;
    }

    void reset() {
        wall = false;
        stop = false;
        start = false;
        setStyle("-fx-background-color: snow");
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell c = (Cell) obj;
            return c.x.equals(x) && c.y.equals(y);
        }
        return false;
    }

    @Override
    public String toString() {
        return x + " , " + y;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
