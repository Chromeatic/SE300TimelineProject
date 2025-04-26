package timeline.project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text; // Import Text
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class PivotMode extends Mode {

    @FXML private HBox beforePivotHBox;
    @FXML private HBox afterPivotHBox;
    @FXML private Text pivotEventText; // To display the pivot event name

    private ArrayList<Event> eventsBeforePivot = new ArrayList<>();
    private ArrayList<Event> eventsAfterPivot = new ArrayList<>();
    private ArrayList<Event> remainingEvents = new ArrayList<>();
    private Event pivotEvent = null;
    private Button selectedEventButton = null;
    private Event selectedEvent = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources); // Call superclass initialize

        if (getTempTimeline() != null && getTempTimeline().getEvents().size() >= 3) {
            // Clear previous state
            timelineEventsHBox.getChildren().clear(); // Assumes timelineEventsHBox exists from Mode
            beforePivotHBox.getChildren().clear();
            afterPivotHBox.getChildren().clear();
            eventsBeforePivot.clear();
            eventsAfterPivot.clear();
            remainingEvents.clear();

            // Select Pivot
            List<Event> originalEvents = new ArrayList<>(getTempTimeline().getEvents()); // Use a copy
            Random random = new Random();
            // Ensure pivot is not the first or last event
            int pivotIndex = random.nextInt(originalEvents.size() - 2) + 1;
            pivotEvent = originalEvents.get(pivotIndex);
            pivotEventText.setText(pivotEvent.getName()); // Display pivot event name

            // Prepare remaining events (all except pivot)
            remainingEvents.addAll(originalEvents);
            remainingEvents.remove(pivotEvent);
            Collections.shuffle(remainingEvents); // Shuffle the remaining events

            // Create buttons for remaining events
            for (Event event : remainingEvents) {
                Button btn = createEventButton(event);
                timelineEventsHBox.getChildren().add(btn);
            }
        } else {
            // Handle case with less than 3 events (Pivot mode needs at least 3)
            pivotEventText.setText("Not enough events for Pivot Mode");
            // Optionally disable buttons or show an alert
             Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Timeline Too Short");
            alert.setHeaderText(null);
            alert.setContentText("Pivot Mode requires at least 3 events in the timeline.");
            alert.showAndWait();
            // Consider navigating back or disabling interaction
             try {
                 modeSelect(null); // Go back to mode select if timeline is too short
             } catch (Exception e) { // Catch potential Exception (more general)
                 // Handle the exception appropriately, e.g., log it or show an error
                 System.err.println("Error returning to mode select: " + e.getMessage());
                 e.printStackTrace(); // Or use a logger
             }
        }
    }

     private Button createEventButton(Event event) {
        Button btn = new Button(event.getName());
        btn.setPrefHeight(150); // Adjust size as needed
        btn.setPrefWidth(150);
        btn.setWrapText(true);
        btn.setStyle("-fx-font-size: 14px;");
        btn.setOnAction(e -> selectEvent(btn, event)); // Use setOnAction for single click selection
        return btn;
    }

     private void selectEvent(Button btn, Event event) {
        // Deselect previous button if any
        if (selectedEventButton != null) {
            selectedEventButton.setStyle("-fx-font-size: 14px;"); // Reset style
        }

        // Select new button
        selectedEventButton = btn;
        selectedEvent = event;
        selectedEventButton.setStyle("-fx-border-color: blue; -fx-border-width: 3px; -fx-font-size: 14px;"); // Highlight selected
    }

    @FXML
    private void insertBeforePivot(ActionEvent event) {
        if (selectedEvent != null && selectedEventButton != null) {
            eventsBeforePivot.add(selectedEvent);
            timelineEventsHBox.getChildren().remove(selectedEventButton); // Remove from bottom
            beforePivotHBox.getChildren().add(selectedEventButton); // Add to 'before' area
            remainingEvents.remove(selectedEvent);

            // Reset selection and style
            selectedEventButton.setStyle("-fx-font-size: 14px;"); // Reset style after placing
            selectedEvent = null;
            selectedEventButton = null;
        }
    }

    @FXML
    private void insertAfterPivot(ActionEvent event) {
         if (selectedEvent != null && selectedEventButton != null) {
            eventsAfterPivot.add(selectedEvent);
            timelineEventsHBox.getChildren().remove(selectedEventButton); // Remove from bottom
            afterPivotHBox.getChildren().add(selectedEventButton); // Add to 'after' area
            remainingEvents.remove(selectedEvent);

            // Reset selection and style
            selectedEventButton.setStyle("-fx-font-size: 14px;"); // Reset style after placing
            selectedEvent = null;
            selectedEventButton = null;
        }
    }

     // Optional: Add a button/mechanism to return a placed event
     @FXML
     private void returnSelectedEvent(ActionEvent event) {
         if (selectedEvent != null && selectedEventButton != null) {
             boolean removed = false;
             if (beforePivotHBox.getChildren().contains(selectedEventButton)) {
                 eventsBeforePivot.remove(selectedEvent);
                 beforePivotHBox.getChildren().remove(selectedEventButton);
                 removed = true;
             } else if (afterPivotHBox.getChildren().contains(selectedEventButton)) {
                 eventsAfterPivot.remove(selectedEvent);
                 afterPivotHBox.getChildren().remove(selectedEventButton);
                 removed = true;
             }

             if (removed) {
                 remainingEvents.add(selectedEvent); // Add back to remaining pool
                 timelineEventsHBox.getChildren().add(selectedEventButton); // Add button back to bottom HBox
                 // Deselect after returning
                 selectedEventButton.setStyle("-fx-font-size: 14px;");
                 selectedEvent = null;
                 selectedEventButton = null;
             }
         }
     }


    @FXML
    private void handleSubmit(ActionEvent event) {
        // Ensure pivot is selected and timeline is valid
        if (pivotEvent == null || getTimeline() == null) {
             Alert errorAlert = new Alert(AlertType.ERROR);
             errorAlert.setTitle("Error");
             errorAlert.setHeaderText(null);
             errorAlert.setContentText("Cannot submit. Timeline or Pivot Event not properly initialized.");
             errorAlert.showAndWait();
             return;
        }

        // Check if all events have been placed
        if (!remainingEvents.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Incomplete Placement");
            alert.setHeaderText(null);
            alert.setContentText("Please place all events either before or after the pivot event before submitting.");
            alert.showAndWait();
            return;
        }

        // Validate placement relative to the pivot
        List<Event> correctOrder = getTimeline().getEvents();
        int pivotIndexInCorrect = correctOrder.indexOf(pivotEvent);
        boolean isCorrect = true;

        // Check 'before' events
        for (Event placedEvent : eventsBeforePivot) {
            int correctIndex = correctOrder.indexOf(placedEvent);
            if (correctIndex >= pivotIndexInCorrect) {
                isCorrect = false;
                System.out.println("Incorrect placement (before): " + placedEvent.getName() + " should be before " + pivotEvent.getName());
                break; // Found an error
            }
        }

        // Check 'after' events only if 'before' events were correct so far
        if (isCorrect) {
            for (Event placedEvent : eventsAfterPivot) {
                int correctIndex = correctOrder.indexOf(placedEvent);
                if (correctIndex <= pivotIndexInCorrect) {
                    isCorrect = false;
                     System.out.println("Incorrect placement (after): " + placedEvent.getName() + " should be after " + pivotEvent.getName());
                    break; // Found an error
                }
            }
        }

        // Display result
        if (isCorrect) {
            Alert correctAlert = new Alert(AlertType.INFORMATION);
            correctAlert.setTitle("Success!");
            correctAlert.setHeaderText(null);
            correctAlert.setContentText("Correct! All events are placed correctly relative to the pivot event: " + pivotEvent.getName());

            ButtonType restartButton = new ButtonType("Restart");
            ButtonType mainMenuButton = new ButtonType("Main Menu");
            ButtonType modeSelectButton = new ButtonType("Select Mode");
            correctAlert.getButtonTypes().setAll(restartButton, mainMenuButton, modeSelectButton);

            Optional<ButtonType> result = correctAlert.showAndWait();
            if (result.isPresent()) {
                try { // Add try-catch block for potential exceptions
                    if (result.get() == restartButton) {
                        restartMode();
                    } else if (result.get() == mainMenuButton) {
                        switchToMainMenu(event);
                    } else if (result.get() == modeSelectButton) {
                        modeSelect(event);
                    }
                } catch (Exception e) { // Catch potential Exception (more general)
                     // Handle the exception, e.g., log or show an error message
                     System.err.println("Error switching views: " + e.getMessage());
                     e.printStackTrace(); // Or use a logger
                }
            }
        } else {
            Alert incorrectAlert = new Alert(AlertType.ERROR);
            incorrectAlert.setTitle("Incorrect");
            incorrectAlert.setHeaderText(null);
            incorrectAlert.setContentText("The placement of one or more events relative to the pivot event (" + pivotEvent.getName() + ") is incorrect. Please try again.");
            incorrectAlert.showAndWait();
        }
    }

    @Override // Add Override annotation
    protected void restartMode() {
        // Re-initialize the mode state using the original timeline data
        // Need to ensure getTimeline() provides the original, unmodified timeline
        if (getTimeline() != null && getTimeline().getEvents().size() >= 3) {
            // Clear previous state
            timelineEventsHBox.getChildren().clear();
            beforePivotHBox.getChildren().clear();
            afterPivotHBox.getChildren().clear();
            eventsBeforePivot.clear();
            eventsAfterPivot.clear();
            remainingEvents.clear();

            // Re-select Pivot
            List<Event> originalEvents = new ArrayList<>(getTimeline().getEvents()); // Use a fresh copy from original
            Random random = new Random();
            int pivotIndex = random.nextInt(originalEvents.size() - 2) + 1;
            pivotEvent = originalEvents.get(pivotIndex);
            pivotEventText.setText(pivotEvent.getName());

            // Prepare remaining events
            remainingEvents.addAll(originalEvents);
            remainingEvents.remove(pivotEvent);
            Collections.shuffle(remainingEvents);

            // Create buttons for remaining events
            for (Event ev : remainingEvents) {
                Button btn = createEventButton(ev);
                timelineEventsHBox.getChildren().add(btn);
            }

            // Reset selection
            selectedEvent = null;
            selectedEventButton = null;
        } else {
            System.err.println("Error restarting PivotMode: Not enough events or timeline unavailable.");
            // Handle error, maybe navigate back or show an alert
            try {
                modeSelect(null); // Attempt to go back
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Ensure modeSelect and switchToMainMenu methods are accessible (they are in Mode.java)
    // No need to redefine them here unless overriding is necessary.
}