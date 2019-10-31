package ru.mishapan.app.view.search;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.mishapan.app.MainApp;

import java.io.File;

public class SearchSettingsController {

    /**
     * контроллер, описывающий экран поиска, если известен путь
     */
    public SearchSettingsController() {
    }

    @FXML
    private TextField pathTextField;
    @FXML
    private TextField fileExtensionFiend;
    @FXML
    private TextArea textToSearchArea;
    @FXML
    private Button searchButton;
    @FXML
    private Button backButton;
    @FXML
    private Button directoryButton;

    private MainApp mainApp;

    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private String getPathTextField() {
        return pathTextField.getCharacters().toString();
    }

    private String getFileExtensionFiend() {
        if (fileExtensionFiend.getCharacters().toString().equals("")) {
            return "*.*";
        }
        return "*.".concat(fileExtensionFiend.getCharacters().toString());
    }

    private String getTextToSearchArea() {

        StringBuilder sb = new StringBuilder();
        for (CharSequence symbol : textToSearchArea.getParagraphs()) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    /**
     * Открывает диалог выбора папки через файловую систему
     */
    @FXML
    private void directoryButtonHandler() {

        Stage primaryStage = mainApp.getPrimaryStage();
        File dir = directoryChooser.showDialog(primaryStage);

        if (dir != null) {
            pathTextField.setText(dir.getAbsolutePath());
        }
        else {
            pathTextField.setText(null);
        }

        primaryStage.setTitle("Choose directory");
        primaryStage.show();
    }

    @FXML
    private void onMouseEnteredTextField1() {
        pathTextField.setStyle("-fx-background-color: #313131; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedTextField1() {
        pathTextField.setStyle("-fx-background-color: #535353; -fx-text-fill: #cccccc");
    }

    @FXML
    private void onMouseEnteredTextField2() {
        fileExtensionFiend.setStyle("-fx-background-color: #313131; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedTextField2() {
        fileExtensionFiend.setStyle("-fx-background-color: #535353; -fx-text-fill: #cccccc");
    }

    @FXML
    private void onMouseEnteredSearchButton() {
        Bloom bloom = new Bloom();
        searchButton.setEffect(bloom);
    }

    @FXML
    private void onMouseExitedSearchButton() {
        searchButton.setEffect(null);
    }

    @FXML
    private void onMouseEnteredBackButton() {
        Bloom bloom = new Bloom();
        backButton.setEffect(bloom);
    }

    @FXML
    private void onMouseExitedBackButton() {
        backButton.setEffect(null);
    }

    @FXML
    private void onMouseEnteredDirectoryButton() {
        Bloom bloom = new Bloom();
        directoryButton.setEffect(bloom);
    }

    @FXML
    private void onMouseExitedDirectoryButton() {
        directoryButton.setEffect(null);
    }

    /**
     * При нажатие на кнопку поиск, производится проверка поля путь на пустоту, затем запускается новая сцена
     * с результатами поиска
     */
    @FXML
    private void buttonSearchHandler() {

        if (getPathTextField().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText(null);
            alert.setContentText("Path can't be empty");
            alert.showAndWait();
            return;
        }
        mainApp.showSearchResultScreen(getPathTextField(), getFileExtensionFiend(), getTextToSearchArea());
    }

    @FXML
    private void buttonBackHandler() {
        mainApp.showStartScreen();
    }
}
