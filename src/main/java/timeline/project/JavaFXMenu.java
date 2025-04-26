package timeline.project;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class JavaFXMenu extends Application {

    private static Stage primaryStage;
    private static Timeline loadedTimeline;

    @FXML
    private Text loadedTimelineText;

    @Override
    public void start(Stage primaryStage) throws Exception {
        JavaFXMenu.primaryStage = primaryStage;
        Mode.setPrimaryStage(primaryStage); 
        primaryStage.setTitle("JavaFX Menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void modeSelect() throws Exception {
        if (loadedTimeline == null) {
            // Show a centered popup alert if no timeline is loaded
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("No Timeline Loaded");
            alert.setHeaderText(null);
            alert.setContentText("Please load a timeline before selecting a mode.");
            alert.initOwner(primaryStage);
            // Center the alert on the screen
            alert.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - 200);
            alert.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - 100);
            alert.getDialogPane().setPrefSize(400, 200);
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modeSelect.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void mainMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();

        Text timelineTextNode = (Text) root.lookup("#loadedTimelineText");

        if (timelineTextNode != null) {
            if (loadedTimeline != null && loadedTimeline.getFilePath() != null) { 
                File timelineFile = new File(loadedTimeline.getFilePath());
                String fileName = timelineFile.getName().replace(".csv", "");
                timelineTextNode.setText("Loaded Timeline: " + fileName);
            } else {
                timelineTextNode.setText("Loaded Timeline: None");
            }
        } else {
             System.err.println("Warning: Could not find Text node with fx:id 'loadedTimelineText' in mainMenu.fxml when returning to main menu.");
        }

        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void userStatistics() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userStatistics.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void timelineMode() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/timelineMode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void beforeAfterMode() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/beforeAfter.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void quizMode() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quizMode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void pivotMode() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pivotMode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void flashcardMode() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/flashcardMode.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
    }

    @FXML
    private void loadTimeline() 
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            try 
            {
                Timeline timeline = new Timeline(f.getAbsolutePath());
                loadedTimeline = timeline;
                timeline.displayTimeline();
                String fileName = f.getName().replace(".csv", "");
                loadedTimelineText.setText("Loaded Timeline: " + fileName);
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        } else 
        {
            System.out.println("No file selected.");
        }
    }

    public static Timeline getLoadedTimeline() {
        return loadedTimeline;
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}