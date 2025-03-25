package timeline.project;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXMenu extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        JavaFXMenu.primaryStage = primaryStage;
        primaryStage.setTitle("JavaFX Menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void modeSelect() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modeSelect.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
    }

    @FXML
    private void mainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
    }

    @FXML
    private void userStatistics() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userStatistics.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
    }



    public static void main(String[] args) {
        launch(args);
    }
}