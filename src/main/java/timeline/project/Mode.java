package timeline.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;
import java.io.File;

public abstract class Mode implements Initializable {

    protected static Stage primaryStage;
    private Timeline timeline;
    private Timeline tempTimeline; 

    public Timeline getTimeline() {
        return timeline;
    }

    public Timeline getTempTimeline() {
        return tempTimeline;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    protected HBox timelineEventsHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline loadedTimeline = JavaFXMenu.getLoadedTimeline();
        if (loadedTimeline != null) {
            this.timeline = loadedTimeline;
            this.tempTimeline = new Timeline(loadedTimeline); 
        }
    }

    @FXML
    protected void restartMode() throws Exception {
        System.out.println("Restarting mode: " + this.getClass().getSimpleName());
        initialize(null, null); 
    }

    @FXML
    protected void modeSelect(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modeSelect.fxml"));
        Parent root = loader.load();
        Stage stage = primaryStage; 

        if (stage == null) {
            System.err.println("Error: Primary stage is null in modeSelect.");
            return; 
        }

        double currentWidth = stage.getScene().getWidth();
        double currentHeight = stage.getScene().getHeight();
        Scene scene = new Scene(root, currentWidth, currentHeight);
        stage.setScene(scene);
    }

    @FXML
    protected void switchToMainMenu(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = primaryStage; 

        if (stage == null) {
            System.err.println("Error: Primary stage is null in switchToMainMenu.");
            return;
        }

        Text timelineTextNode = (Text) root.lookup("#loadedTimelineText");
        if (timelineTextNode != null) {
            Timeline loadedTimeline = JavaFXMenu.getLoadedTimeline();
            if (loadedTimeline != null && loadedTimeline.getFilePath() != null) {
                File timelineFile = new File(loadedTimeline.getFilePath());
                String fileName = timelineFile.getName().replace(".csv", "");
                timelineTextNode.setText("Loaded Timeline: " + fileName);
            } else {
                timelineTextNode.setText("Loaded Timeline: None");
            }
        } else {
             System.err.println("Warning: Could not find Text node with fx:id 'loadedTimelineText' in mainMenu.fxml.");
        }

        double currentWidth = stage.getScene().getWidth();
        double currentHeight = stage.getScene().getHeight();
        Scene scene = new Scene(root, currentWidth, currentHeight);
        stage.setScene(scene);
    }
}