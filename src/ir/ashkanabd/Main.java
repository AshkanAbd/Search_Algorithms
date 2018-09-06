package ir.ashkanabd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Toolkit;

public class Main extends Application {

    private Controller controller;
    static double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    static double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static int screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
    static int windowHeight;
    static int windowWidth;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI.fxml"));
        controller = new Controller();
        loader.setController(controller);
        windowWidth = (int) screenWidth / (2 * screenResolution / 96);
        windowHeight = (int) (((int) screenWidth / (2 * screenResolution / 96)) / 1.333333333);
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
