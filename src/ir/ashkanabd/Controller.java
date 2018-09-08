package ir.ashkanabd;

import ir.ashkanabd.algorithms.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class Controller {

    @FXML
    AnchorPane windowPane;

    private static boolean mark = false;
    private static boolean unMark = false;

    private AnchorPane mainPane;
    private Button bfsButton;
    private Button dfsButton;
    private Button dijkstraButton;
    private Button bestFirstButton;
    private Button aStarButton;
    private Button bellmanFordButton;
    private Button floydWarshallButton;
    private Button resetButton;
    private Label timeLabel;
    private Cell[][] map = new Cell[20][20];
    private Cell startCell, stopCell;
    private boolean start = false;
    private boolean points[] = {false, false};
    private Thread algorithmThread;

    public void initialize() {
        windowPane.setPrefHeight(Main.windowHeight);
        windowPane.setPrefWidth(Main.windowWidth);
        windowPane.addEventHandler(MouseEvent.ANY, this::dragging);
        mainPane = new AnchorPane();
        windowPane.getChildren().add(mainPane);
        mainPane.setPrefHeight(Main.windowHeight + 1);
        mainPane.setPrefWidth(Main.windowHeight + 1);
        mainPane.setStyle("-fx-background-color: black");
        addAllNodes();
        addButtons();
    }

    private void dragging(MouseEvent event) {
        if (event.getTarget() instanceof Cell) {
            Cell src = (Cell) event.getTarget();
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                if (src.isWall()) {
                    if (!src.isStop() && !src.isStart()) {
                        unMark = true;
                        src.setWall(false);
                        src.setStyle("-fx-background-color: snow");
                    }
                } else {
                    if (!src.isStop() && !src.isStart()) {
                        mark = true;
                        src.setWall(true);
                        src.setStyle("-fx-background-color: dodgerblue");
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                if (event.getPickResult().getIntersectedNode() instanceof Cell) {
                    src = (Cell) event.getPickResult().getIntersectedNode();
                    if (unMark) {
                        if (!src.isStop() && !src.isStart()) {
                            src.setWall(false);
                            src.setStyle("-fx-background-color: snow");
                        }
                    }
                    if (mark) {
                        if (!src.isStop() && !src.isStart()) {
                            src.setWall(true);
                            src.setStyle("-fx-background-color: dodgerblue");
                        }
                    }
                }
            }
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                if (unMark) unMark = false;
                if (mark) mark = false;
            }
        }
    }

    private void clickListener(MouseEvent event) {
        Cell src = (Cell) event.getSource();
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

    private void cancelRequest() {
        Base.stopRequest = true;
    }

    private void setTimeLabel(String text) {
        Platform.runLater(() -> timeLabel.setText(text));
    }

    private void performAlgorithm(MouseEvent event) {
        try {
            Button src = (Button) event.getSource();
            if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED) && event.getButton().equals(MouseButton.PRIMARY)
                    && points[0] && points[1]) {
                cancelRequest();
                reset();
                Thread.sleep(100);
                if (src.equals(bfsButton)) {
                    algorithmThread = new Thread(() -> BFS.bfs(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(dfsButton)) {
                    algorithmThread = new Thread(() -> DFS.dfs(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(dijkstraButton)) {
                    algorithmThread = new Thread(() -> Dijkstra.dijkstra(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(floydWarshallButton)) {
                    algorithmThread = new Thread(() -> FloydWarshall.floydWarshall(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(bestFirstButton)) {
                    algorithmThread = new Thread(() -> BestFirstSearch.bestFirstSearch(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(aStarButton)) {
                    algorithmThread = new Thread(() -> AStar.aStar(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                } else if (src.equals(bellmanFordButton)) {
                    algorithmThread = new Thread(() -> BellmanFord.bellmanFord(map, startCell, stopCell, this::setTimeLabel));
                    algorithmThread.start();
                }
            }
        } catch (Exception ignored) {
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
        try {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                cancelRequest();
                Thread.sleep(10);
                for (Cell[] cells : map) {
                    for (Cell cell : cells) {
                        cell.reset();
                    }
                }
                start = false;
                points[0] = false;
                points[1] = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        // BFS button setup
        bfsButton = new Button("BFS algorithm");
        bfsButton.setLayoutX(Main.windowHeight + 20);
        bfsButton.setLayoutY(20);
        bfsButton.setPrefHeight(30);
        bfsButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        bfsButton.setOnMouseClicked(this::performAlgorithm);
        // DFS button setup
        dfsButton = new Button("DFS algorithm");
        dfsButton.setLayoutX(Main.windowHeight + 20);
        dfsButton.setLayoutY(70);
        dfsButton.setPrefHeight(30);
        dfsButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        dfsButton.setOnMouseClicked(this::performAlgorithm);
        // Dijkstra button setup
        dijkstraButton = new Button("Dijkstra algorithm");
        dijkstraButton.setLayoutX(Main.windowHeight + 20);
        dijkstraButton.setLayoutY(120);
        dijkstraButton.setPrefHeight(30);
        dijkstraButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        dijkstraButton.setOnMouseClicked(this::performAlgorithm);
        // Best First button setup
        bestFirstButton = new Button("Best First algorithm");
        bestFirstButton.setLayoutX(Main.windowHeight + 20);
        bestFirstButton.setLayoutY(170);
        bestFirstButton.setPrefHeight(30);
        bestFirstButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        bestFirstButton.setOnMouseClicked(this::performAlgorithm);
        // A Start button setup
        aStarButton = new Button("A* algorithm");
        aStarButton.setLayoutX(Main.windowHeight + 20);
        aStarButton.setLayoutY(220);
        aStarButton.setPrefHeight(30);
        aStarButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        aStarButton.setOnMouseClicked(this::performAlgorithm);
        // Bellman Ford button setup
        bellmanFordButton = new Button("Bellman-Ford");
        bellmanFordButton.setLayoutX(Main.windowHeight + 20);
        bellmanFordButton.setLayoutY(270);
        bellmanFordButton.setPrefHeight(30);
        bellmanFordButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        bellmanFordButton.setOnMouseClicked(this::performAlgorithm);
        // Floyd Warshall button setup
        floydWarshallButton = new Button("Floyd-Warshall");
        floydWarshallButton.setLayoutX(Main.windowHeight + 20);
        floydWarshallButton.setLayoutY(320);
        floydWarshallButton.setPrefHeight(30);
        floydWarshallButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        floydWarshallButton.setOnMouseClicked(this::performAlgorithm);
        // Reset button setup
        resetButton = new Button("Reset");
        resetButton.setLayoutX(Main.windowHeight + 20);
        resetButton.setLayoutY(370);
        resetButton.setPrefHeight(30);
        resetButton.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        resetButton.setOnMouseClicked(this::resetAll);
        // Time label setup
        timeLabel = new Label();
        timeLabel.setLayoutX(Main.windowHeight + 20);
        timeLabel.setLayoutY(420);
        timeLabel.setPrefHeight(40);
        timeLabel.setPrefWidth(Main.windowWidth - (Main.windowHeight + 30));
        timeLabel.setFont(Font.font(15));
        timeLabel.setStyle("-fx-alignment: center; -fx-font-family: sans-serif;");
        // Add all to windowPane
        windowPane.getChildren().add(bfsButton);
        windowPane.getChildren().add(dfsButton);
        windowPane.getChildren().add(dijkstraButton);
        windowPane.getChildren().add(bestFirstButton);
        windowPane.getChildren().add(aStarButton);
        windowPane.getChildren().add(bellmanFordButton);
        windowPane.getChildren().add(floydWarshallButton);
        windowPane.getChildren().add(resetButton);
        windowPane.getChildren().add(timeLabel);
    }
}
