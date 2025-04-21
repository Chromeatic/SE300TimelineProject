package timeline.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class QuizMode {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
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
