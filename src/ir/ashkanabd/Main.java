package ir.ashkanabd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Toolkit;

public class Main extends Application {

    private Controller controller;
    static int windowHeight = 480;
    static int windowWidth = 640;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI.fxml"));
        controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("Search algorithms");
        Scene scene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
