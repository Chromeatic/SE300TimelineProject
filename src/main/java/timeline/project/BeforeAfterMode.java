package timeline.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.net.URL;
import java.util.*;

public class BeforeAfterMode extends Mode {

    @FXML private Text comparingEventText;
    @FXML private Text userEventText;
    @FXML private Button beforeButton;
    @FXML private Button afterButton;

    private List<Event> availableEvents;
    private Set<Event> usedEvents;
    private Event comparingEvent;
    private Event userEvent;
    private Random random = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (getTimeline() == null || getTimeline().getEvents().size() < 2) {
            showError("Timeline Error", "Before and After mode requires a timeline with at least 2 events.");
            if (beforeButton != null) beforeButton.setDisable(true);
            if (afterButton != null) afterButton.setDisable(true);
            if (comparingEventText != null) comparingEventText.setText("N/A");
            if (userEventText != null) userEventText.setText("N/A");
            return;
        }

        availableEvents = new ArrayList<>(getTimeline().getEvents());
        usedEvents = new HashSet<>();
        setupNextRound();
    }

    private void setupNextRound() {
        if (usedEvents.size() >= availableEvents.size()) {
            showGameWon();
            return;
        }

        if (userEvent != null) {
            comparingEvent = userEvent;
        } else {
            comparingEvent = getRandomUnusedEvent();
            if (comparingEvent == null) {
                 showError("Error", "Could not select initial comparing event.");
                 return;
            }
            usedEvents.add(comparingEvent);
        }

        userEvent = getRandomUnusedEvent();
        if (userEvent == null) {
             showGameWon();
             return;
        }
        usedEvents.add(userEvent);

        comparingEventText.setText(comparingEvent.getName());
        userEventText.setText(userEvent.getName());
    }

    private Event getRandomUnusedEvent() {
        List<Event> potentialEvents = new ArrayList<>();
        for (Event e : availableEvents) {
            if (!usedEvents.contains(e)) {
                potentialEvents.add(e);
            }
        }

        if (potentialEvents.isEmpty()) {
            return null;
        }

        int randomIndex = random.nextInt(potentialEvents.size());
        return potentialEvents.get(randomIndex);
    }

    @FXML
    private void handleBeforeButton(ActionEvent event) {
        checkAnswer(true);
    }

    @FXML
    private void handleAfterButton(ActionEvent event) {
        checkAnswer(false);
    }

    private void checkAnswer(boolean choseBefore) {
        if (comparingEvent == null || userEvent == null) return;

        int comparison = userEvent.compareTo(comparingEvent);

        boolean actuallyBefore = comparison < 0;
        boolean actuallyAfter = comparison > 0;

        boolean correctAnswer = (choseBefore && actuallyBefore) || (!choseBefore && actuallyAfter);

        if (correctAnswer) {
            setupNextRound();
        } else {
            String correctChoice = actuallyBefore ? "BEFORE" : "AFTER";
            showError("Incorrect", userEvent.getName() + " happened " + correctChoice + " " + comparingEvent.getName() + ".");
            setupNextRound();
        }
    }

    private void showGameWon() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText(null);
        alert.setContentText("You\'ve correctly ordered all available events!");

        ButtonType restartButton = new ButtonType("Restart");
        ButtonType mainMenuButton = new ButtonType("Main Menu");
        ButtonType modeSelectButton = new ButtonType("Select Mode");

        alert.getButtonTypes().setAll(restartButton, mainMenuButton, modeSelectButton);

        Optional<ButtonType> result = alert.showAndWait();
        try {
            if (result.isPresent()) {
                if (result.get() == restartButton) {
                    restartMode();
                } else if (result.get() == mainMenuButton) {
                    switchToMainMenu(null); 
                } else if (result.get() == modeSelectButton) {
                    modeSelect(null);
                }
            }
        } catch (Exception e) {
            System.err.println("Error handling game won options: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void restartMode() {
        availableEvents = new ArrayList<>(getTimeline().getEvents());
        usedEvents = new HashSet<>();
        comparingEvent = null;
        userEvent = null;      
        setupNextRound();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
