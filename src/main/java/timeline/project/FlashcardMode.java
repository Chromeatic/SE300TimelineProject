package timeline.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class FlashcardMode extends Mode {

    @FXML
    private HBox timelineEventsHBox; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources); 
        populateFlashcards();
    }

    private void populateFlashcards() {
        if (getTimeline() != null && timelineEventsHBox != null) {
            timelineEventsHBox.getChildren().clear(); 
            
            Collections.sort(getTimeline().getEvents());

            for (Event event : getTimeline().getEvents()) {
                String eventName = event.getName();
                String eventDate = event.getDate().toString();
                String frontText = eventName + "\n" + eventDate;

                Button eventButton = new Button(frontText);
                eventButton.setPrefHeight(300.0);
                eventButton.setPrefWidth(350.0);
                eventButton.setFont(new Font(24.0));
                eventButton.setWrapText(true);
                eventButton.setStyle("-fx-text-alignment: center;"); 

                final String eventDescription = event.getDescription();

                eventButton.setOnAction(e -> {
                    if (eventButton.getText().equals(frontText)) {
                        eventButton.setText(eventDescription);
                    } else {
                        eventButton.setText(frontText);
                    }
                });

                timelineEventsHBox.getChildren().add(eventButton);
            }
        }
    }

}