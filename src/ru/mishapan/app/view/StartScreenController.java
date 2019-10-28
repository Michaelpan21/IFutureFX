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
    Button leftButton;
    @FXML
    Button rightButton;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * обраточкик кнопки "поиск, если известен путь"
     */
    @FXML
    private void handleLeftButton() {
        mainApp.showFileFinderByPathScreen();
    }

    /**
     * обраточкик кнопки "поиск, если известенно только имя папки"
     */
    @FXML
    private void handleRightButton() {
        mainApp.showFileFinderByFolderScreen();
    }

    @FXML
    private void onMouseEnteredLeftButton() {
        Bloom bloom = new Bloom();
        leftButton.setEffect(bloom);
        leftButton.setStyle("-fx-background-color: #232323; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseEnteredRightButton() {
        Bloom bloom = new Bloom();
        rightButton.setEffect(bloom);
        rightButton.setStyle("-fx-background-color: #232323; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedLeftButton() {
        leftButton.setEffect(null);
        leftButton.setStyle("-fx-background-color: #282828; -fx-text-fill: #5A5A5A");
    }

    @FXML
    private void onMouseExitedRightButton() {
        rightButton.setEffect(null);
        rightButton.setStyle("-fx-background-color: #282828; -fx-text-fill: #5A5A5A");
    }
}
