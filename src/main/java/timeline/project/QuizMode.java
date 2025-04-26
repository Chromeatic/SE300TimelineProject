package timeline.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class QuizMode extends Mode {

    @FXML private Text questionText;
    @FXML private Button answerButton1;
    @FXML private Button answerButton2;
    @FXML private Button answerButton3;
    @FXML private Button answerButton4;

    private List<Event> remainingQuestions;
    private List<Event> allEvents;
    private Event currentQuestion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (getTimeline() == null || getTimeline().getEvents().isEmpty()) {
            showError("Timeline Error", "Quiz mode requires a loaded timeline with at least one event.");
            disableQuiz();
            return;
        }

        allEvents = new ArrayList<>(getTimeline().getEvents());
        remainingQuestions = new ArrayList<>(allEvents);
        Collections.shuffle(remainingQuestions);

        setupNextQuestion();
    }

    private void setupNextQuestion() {
        if (remainingQuestions.isEmpty()) {
            showCompletionDialog();
            return;
        }

        currentQuestion = remainingQuestions.remove(0);
        questionText.setText("Which date corresponds to the event: " + currentQuestion.getName() + "?");

        List<String> answers = new ArrayList<>();
        String correctAnswer = currentQuestion.getDate().toString();
        answers.add(correctAnswer);

        List<Event> potentialIncorrect = allEvents.stream()
                .filter(event -> !event.equals(currentQuestion))
                .collect(Collectors.toList());
        Collections.shuffle(potentialIncorrect);

        int incorrectNeeded = 3;
        for (Event incorrectEvent : potentialIncorrect) {
            if (answers.size() > incorrectNeeded) break;
            String incorrectDate = incorrectEvent.getDate().toString();
            if (!incorrectDate.equals(correctAnswer) && !answers.contains(incorrectDate)) {
                answers.add(incorrectDate);
            }
        }

        while (answers.size() < 4 && !potentialIncorrect.isEmpty()) {
             String fallbackDate = potentialIncorrect.remove(0).getDate().toString();
             if (!answers.contains(fallbackDate)) {
                 answers.add(fallbackDate);
             }
        }
         while (answers.size() < 4) {
             answers.add(correctAnswer + " (duplicate)");
         }


        Collections.shuffle(answers);

        Button[] buttons = {answerButton1, answerButton2, answerButton3, answerButton4};
        for (int i = 0; i < buttons.length; i++) {
            if (i < answers.size()) {
                final String answer = answers.get(i);
                buttons[i].setText(answer);
                buttons[i].setOnAction(e -> checkAnswer(answer));
                buttons[i].setDisable(false);
            } else {
                buttons[i].setText("");
                buttons[i].setDisable(true);
            }
        }
    }

    private void checkAnswer(String chosenDate) {
        String correctAnswer = currentQuestion.getDate().toString();
        if (chosenDate.equals(correctAnswer) || chosenDate.equals(correctAnswer + " (duplicate)")) {
            setupNextQuestion();
        } else {
            Alert incorrectAlert = new Alert(AlertType.ERROR);
            incorrectAlert.setTitle("Incorrect");
            incorrectAlert.setHeaderText(null);
            incorrectAlert.setContentText("Sorry, that is not the correct date for " + currentQuestion.getName() + ".");
            incorrectAlert.showAndWait();
        }
    }

    private void showCompletionDialog() {
        Alert completionAlert = new Alert(AlertType.INFORMATION);
        completionAlert.setTitle("Quiz Complete!");
        completionAlert.setHeaderText(null);
        completionAlert.setContentText("You have completed the quiz!");

        ButtonType restartButton = new ButtonType("Restart Quiz");
        ButtonType mainMenuButton = new ButtonType("Main Menu");
        ButtonType modeSelectButton = new ButtonType("Select Mode");

        completionAlert.getButtonTypes().setAll(restartButton, mainMenuButton, modeSelectButton);

        Optional<ButtonType> result = completionAlert.showAndWait();
        try {
            if (result.isPresent()) {
                if (result.get() == restartButton) {
                    restartMode();
                } else if (result.get() == mainMenuButton) {
                    switchToMainMenu(null);
                } else if (result.get() == modeSelectButton) {
                    modeSelect(null);
                }
            } else {
                modeSelect(null);
            }
        } catch (Exception e) {
            System.err.println("Error handling completion options: " + e.getMessage());
            e.printStackTrace();
            showError("Navigation Error", "Could not switch views.");
        }
    }

    @Override
    protected void restartMode() {
        if (allEvents != null && !allEvents.isEmpty()) {
            remainingQuestions = new ArrayList<>(allEvents);
            Collections.shuffle(remainingQuestions);
            setupNextQuestion();
        } else {
             showError("Restart Error", "Cannot restart quiz. Timeline data is missing.");
             disableQuiz();
        }
    }

    private void disableQuiz() {
        questionText.setText("Quiz cannot be started.");
        answerButton1.setDisable(true);
        answerButton2.setDisable(true);
        answerButton3.setDisable(true);
        answerButton4.setDisable(true);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
