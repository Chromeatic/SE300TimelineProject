package timeline.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TimelineMode extends Mode {
    @FXML
    private HBox upperTimelineHBox; 

    private ArrayList<Event> answerTimeline = new ArrayList<>();
    private ArrayList<Event> remainingEvents = new ArrayList<>();
    private Button selectedEventButton = null;
    private Event selectedEvent = null;
    private Button firstSwapButton = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        if (getTempTimeline() != null && timelineEventsHBox != null) {
            timelineEventsHBox.getChildren().clear();
            answerTimeline.clear();
            remainingEvents.clear();
            remainingEvents.addAll(getTempTimeline().shuffleList(getTempTimeline().getEvents()));
            for (Event event : remainingEvents) {
                Button btn = createEventButton(event);
                timelineEventsHBox.getChildren().add(btn);
            }
        }
        if (upperTimelineHBox != null) {
            upperTimelineHBox.getChildren().clear();
        }
    }

    private Button createEventButton(Event event) {
        String eventName = event.getName();
        String buttonText = eventName;

        Button btn = new Button(buttonText);
        btn.setPrefHeight(200);
        btn.setPrefWidth(200);
        btn.setWrapText(true);
        String baseStyle = "-fx-font-size: 16px; -fx-text-alignment: center;"; 
        btn.setStyle(baseStyle);
        btn.setUserData(baseStyle);
        btn.setOnMouseClicked(e -> {
            if (upperTimelineHBox.getChildren().contains(btn)) {
                handleSwapEvent(btn, event);
            } else {
                selectEvent(btn, event);
            }
        });
        return btn;
    }

    private void selectEvent(Button btn, Event event) {
        selectedEventButton = btn;
        selectedEvent = event;
    }

    private void handleSwapEvent(Button btn, Event event) {
        String baseStyle = (String) btn.getUserData();
        if (baseStyle == null) { 
            baseStyle = "-fx-font-size: 16px;";
        }

        if (firstSwapButton == null) {
            firstSwapButton = btn;
            selectedEventButton = btn;
            selectedEvent = event;
            btn.setStyle(baseStyle + " -fx-border-color: blue; -fx-border-width: 3px;");
        } else if (btn != firstSwapButton) {
            int idx1 = upperTimelineHBox.getChildren().indexOf(firstSwapButton);
            int idx2 = upperTimelineHBox.getChildren().indexOf(btn);

            if (idx1 != -1 && idx2 != -1) {
                Button button1 = firstSwapButton;
                Button button2 = btn;
                String button1BaseStyle = (String) button1.getUserData();
                String button2BaseStyle = (String) button2.getUserData();
                 if (button1BaseStyle == null) button1BaseStyle = "-fx-font-size: 16px;";
                 if (button2BaseStyle == null) button2BaseStyle = "-fx-font-size: 16px;";


                int minIdx = Math.min(idx1, idx2);
                int maxIdx = Math.max(idx1, idx2);

                upperTimelineHBox.getChildren().remove(maxIdx);
                upperTimelineHBox.getChildren().remove(minIdx);

                if (idx1 < idx2) {
                    upperTimelineHBox.getChildren().add(minIdx, button2);
                    upperTimelineHBox.getChildren().add(maxIdx, button1);
                } else {
                    upperTimelineHBox.getChildren().add(minIdx, button1);
                    upperTimelineHBox.getChildren().add(maxIdx, button2);
                }

                Collections.swap(answerTimeline, idx1, idx2);

                button1.setStyle(button1BaseStyle);
                button2.setStyle(button2BaseStyle);
                firstSwapButton = null;
                selectedEventButton = null;
                selectedEvent = null;
            } else {
                System.err.println("Error: Could not find one or both buttons in HBox during swap.");
                if (firstSwapButton != null) {
                     String firstSwapBaseStyle = (String) firstSwapButton.getUserData();
                     if (firstSwapBaseStyle == null) firstSwapBaseStyle = "-fx-font-size: 16px;";
                     firstSwapButton.setStyle(firstSwapBaseStyle);
                }
                btn.setStyle(baseStyle);
                firstSwapButton = null;
                selectedEventButton = null;
                selectedEvent = null;
            }
        } else {
            firstSwapButton.setStyle(baseStyle);
            firstSwapButton = null;
            selectedEventButton = null;
            selectedEvent = null;
        }
    }

    @FXML
    private void insertEventAtStart(ActionEvent event) {
        if (selectedEvent != null && selectedEventButton != null) {
            answerTimeline.add(0, selectedEvent);
            upperTimelineHBox.getChildren().remove(selectedEventButton);
            if (timelineEventsHBox != null) {
                timelineEventsHBox.getChildren().remove(selectedEventButton);
            }
            upperTimelineHBox.getChildren().add(0, selectedEventButton);
            remainingEvents.remove(selectedEvent);
            getTempTimeline().getEvents().remove(selectedEvent);
            selectedEvent = null;
            selectedEventButton = null;
        }
    }

    @FXML
    private void insertEventAtEnd(ActionEvent event) {
        if (selectedEvent != null && selectedEventButton != null) {
            answerTimeline.add(selectedEvent);
            upperTimelineHBox.getChildren().remove(selectedEventButton);
            if (timelineEventsHBox != null) {
                timelineEventsHBox.getChildren().remove(selectedEventButton);
            }
            upperTimelineHBox.getChildren().add(selectedEventButton);
            remainingEvents.remove(selectedEvent);
            getTempTimeline().getEvents().remove(selectedEvent);
            selectedEvent = null;
            selectedEventButton = null;
        }
    }

    @FXML
    private void returnEvent(ActionEvent event) {
        if (selectedEvent != null) {
            int idx = answerTimeline.indexOf(selectedEvent);
            if (idx != -1) {
                answerTimeline.remove(idx);
                upperTimelineHBox.getChildren().removeIf(node ->
                    node instanceof Button && ((Button) node).getText().equals(selectedEvent.getName())
                );
                if (!remainingEvents.contains(selectedEvent)) {
                    remainingEvents.add(selectedEvent);
                    timelineEventsHBox.getChildren().add(createEventButton(selectedEvent));
                }
                if (!getTempTimeline().getEvents().contains(selectedEvent)) {
                    getTempTimeline().getEvents().add(selectedEvent);
                }
                selectedEvent = null;
                selectedEventButton = null;
            }
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        List<Event> correctOrder = getTimeline().getEvents(); 
        List<Event> userAnswer = answerTimeline;

        if (userAnswer.size() != correctOrder.size()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incomplete Timeline");
            alert.setHeaderText(null);
            alert.setContentText("Please place all events onto the timeline before submitting.");
            alert.showAndWait();
            return;
        }

        boolean isCorrect = true;
        for (int i = 0; i < correctOrder.size(); i++) {
            if (!userAnswer.get(i).equals(correctOrder.get(i))) {
                isCorrect = false;
                break;
            }
        }

        if (isCorrect) {
            Alert correctAlert = new Alert(AlertType.INFORMATION);
            correctAlert.setTitle("Success!");
            correctAlert.setHeaderText(null);
            correctAlert.setContentText("Correct!");

            ButtonType restartButton = new ButtonType("Restart");
            ButtonType mainMenuButton = new ButtonType("Main Menu");
            ButtonType modeSelectButton = new ButtonType("Select Mode");

            correctAlert.getButtonTypes().setAll(restartButton, mainMenuButton, modeSelectButton);

            Optional<ButtonType> result = correctAlert.showAndWait();
            if (result.isPresent()) {
                try {
                    if (result.get() == restartButton) {
                        restartMode();
                    } else if (result.get() == mainMenuButton) {
                        switchToMainMenu(event);
                    } else if (result.get() == modeSelectButton) {
                        modeSelect(event);
                    }
                } catch (Exception e) {
                    System.err.println("Error handling success options: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            Alert incorrectAlert = new Alert(AlertType.ERROR);
            incorrectAlert.setTitle("Incorrect");
            incorrectAlert.setHeaderText(null);
            incorrectAlert.setContentText("The order of events is incorrect. Please try again.");
            incorrectAlert.showAndWait();
        }
    }

    @Override 
    protected void restartMode() {
        if (getTimeline() != null && timelineEventsHBox != null) {
            timelineEventsHBox.getChildren().clear();
            upperTimelineHBox.getChildren().clear();
            answerTimeline.clear();
            remainingEvents.clear();
            remainingEvents.addAll(getTimeline().shuffleList(new ArrayList<>(getTimeline().getEvents())));
            for (Event event : remainingEvents) {
                Button btn = createEventButton(event);
                timelineEventsHBox.getChildren().add(btn);
            }
            selectedEventButton = null;
            selectedEvent = null;
            firstSwapButton = null;
        } else {
             System.err.println("Error restarting mode: Timeline or HBox not available.");
        }
    }
}
