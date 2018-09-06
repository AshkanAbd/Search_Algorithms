package ir.ashkanabd;

import ir.ashkanabd.algorithms.BFS;
import ir.ashkanabd.algorithms.DFS;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    AnchorPane windowPane;

    private AnchorPane mainPane;
    private Button bfsButton;
    private Button dfsButton;
    private Button resetButton;
    private Cell[][] map = new Cell[20][20];
    private Cell startCell, stopCell;
    private boolean start = false;
    private boolean points[] = {false, false};
    private Thread bfsThread;
    private Thread dfsThread;

    public void initialize() {
        windowPane.setPrefHeight(Main.windowHeight);
        windowPane.setPrefWidth(Main.windowWidth);
        mainPane = new AnchorPane();
        windowPane.getChildren().add(mainPane);
        mainPane.setPrefHeight(Main.windowHeight + 1);
        mainPane.setPrefWidth(Main.windowHeight + 1);
        mainPane.setStyle("-fx-background-color: black");
        addAllNodes();
        addButtons();
    }

    private void clickListener(MouseEvent event) {
        Cell src = (Cell) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_CLICKED)
                && !src.isStop() && !src.isStart()) {
            if (src.isWall()) {
                src.setStyle("-fx-background-color: snow");
                src.setWall(false);
            } else {
                src.setStyle("-fx-background-color: deepskyblue");
                src.setWall(true);
            }
        }
        if (event.getButton().equals(MouseButton.SECONDARY) && event.getEventType().equals(MouseEvent.MOUSE_CLICKED)
                && !src.isWall()) {
            if (src.isStart()) {
                src.setStyle("-fx-background-color: snow");
                src.setStart(false);
                points[0] = false;
                start = false;
            } else if (src.isStop()) {
                src.setStyle("-fx-background-color: snow");
                src.setStop(false);
                points[1] = false;
            } else {
                if (start && !points[1]) {
                    src.setStyle("-fx-background-color: red");
                    src.setStop(true);
                    stopCell = src;
                    points[1] = true;
                } else if (!start && !points[0]) {
                    src.setStyle("-fx-background-color: lawngreen");
                    src.setStart(true);
                    startCell = src;
                    points[0] = true;
                    start = true;
                }
            }
        }
    }

    private void performAlgorithm(MouseEvent event) {
        Button src = (Button) event.getSource();
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton().equals(MouseButton.PRIMARY)
                && points[0] && points[1]) {
            if (src.equals(bfsButton)) {
                reset();
                bfsThread = new Thread(() -> BFS.bfs(map, startCell, stopCell));
                bfsThread.start();
            } else if (src.equals(dfsButton)) {
                reset();
                dfsThread = new Thread(() -> DFS.dfs(map, startCell, stopCell));
                dfsThread.start();
            }
        }
    }

    private void reset() {
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.getStyle().contains("aqua") || cell.getStyle().contains("yellow"))
                    cell.reset();
            }
        }
    }

    private void resetAll(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            for (Cell[] cells : map) {
                for (Cell cell : cells) {
                    cell.reset();
                }
            }
            start = false;
            points[0] = false;
            points[1] = false;
        }
    }

    private void addAllNodes() {
        Cell sampleCell;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    sampleCell = new Cell(i / 2, j / 2);
                    sampleCell.setPrefWidth((Main.windowHeight / 20) - (2));
                    sampleCell.setMinWidth((Main.windowHeight / 20) - (2));
                    sampleCell.setMaxWidth((Main.windowHeight / 20) - (2));
                    sampleCell.setPrefHeight((Main.windowHeight / 20) - (2));
                    sampleCell.setMinHeight((Main.windowHeight / 20) - (2));
                    sampleCell.setMaxHeight((Main.windowHeight / 20) - (2));
                    sampleCell.setStyle("-fx-background-color: snow");
                    sampleCell.setLayoutX(i * Main.windowHeight / 40 + 1);
                    sampleCell.setLayoutY(j * Main.windowHeight / 40 + 1);
                    sampleCell.setOnMouseClicked(this::clickListener);
                    map[j / 2][i / 2] = sampleCell;
                    mainPane.getChildren().add(sampleCell);
                }
            }
        }
    }

    private void addButtons() {
        bfsButton = new Button("BFS algorithm");
        bfsButton.setLayoutX(Main.windowHeight + 20);
        bfsButton.setLayoutY(20);
        bfsButton.setPrefHeight(30);
        bfsButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        bfsButton.setOnMouseClicked(this::performAlgorithm);

        dfsButton = new Button("DFS algorithm");
        dfsButton.setLayoutX(Main.windowHeight + 20);
        dfsButton.setLayoutY(70);
        dfsButton.setPrefHeight(30);
        dfsButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        dfsButton.setOnMouseClicked(this::performAlgorithm);

        resetButton = new Button("Reset");
        resetButton.setLayoutX(Main.windowHeight + 20);
        resetButton.setLayoutY(Main.windowHeight - 50);
        resetButton.setPrefHeight(30);
        resetButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        resetButton.setOnMouseClicked(this::resetAll);

        windowPane.getChildren().add(bfsButton);
        windowPane.getChildren().add(dfsButton);
        windowPane.getChildren().add(resetButton);
    }
}
