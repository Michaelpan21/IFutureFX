package ru.mishapan.app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import ru.mishapan.app.MainApp;

/**
 * контроллер, описывающий начальный экран
 */
public class StartScreenController {

    public StartScreenController() {
    }

    @FXML
    Button GetStartedButton;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleGetStartedButton() {
        mainApp.showSearchSettingsScreen();
    }

    @FXML
    private void onMouseEnteredLeftButton() {
        Bloom bloom = new Bloom();
        GetStartedButton.setEffect(bloom);
        GetStartedButton.setStyle("-fx-background-color: #232323; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedLeftButton() {
        GetStartedButton.setEffect(null);
        GetStartedButton.setStyle("-fx-background-color: #282828; -fx-text-fill: #5A5A5A");
    }

}
