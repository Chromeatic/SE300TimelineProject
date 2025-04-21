package timeline.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class TimelineMode implements Initializable {
    
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    @FXML
    private HBox timelineEventsHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        Timeline loadedTimeline = JavaFXMenu.getLoadedTimeline();
        if (loadedTimeline != null && timelineEventsHBox != null) 
        {
            timelineEventsHBox.getChildren().clear();
            for (Event event : loadedTimeline.getEvents()) 
            {
                Button eventButton = new Button(event.getName());
                eventButton.setPrefWidth(200);
                eventButton.setPrefHeight(200);
                timelineEventsHBox.getChildren().add(eventButton);
            }
        }
    }

    @FXML
    private void modeSelect(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modeSelect.fxml"));
        Parent root = loader.load();
        Stage stage = primaryStage;
        if (stage == null && event.getSource() != null) {
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        }
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
    }
    
}
