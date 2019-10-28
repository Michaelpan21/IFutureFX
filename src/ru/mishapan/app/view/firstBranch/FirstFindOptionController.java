package ru.mishapan.app.view.firstBranch;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import ru.mishapan.app.MainApp;

public class FirstFindOptionController {

    /**
     * контроллер, описывающий экран поиска, если известен путь
     */
    public FirstFindOptionController() {
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

    private MainApp mainApp;

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
        mainApp.showFileFinderByPathResultScreen(getPathTextField(), getFileExtensionFiend(), getTextToSearchArea());
    }

    @FXML
    private void buttonBackHandler() {
        mainApp.showStartScreen();
    }
}
