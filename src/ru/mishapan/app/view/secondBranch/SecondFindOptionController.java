package ru.mishapan.app.view.secondBranch;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import ru.mishapan.app.MainApp;

/**
 * контроллер, описывающий экран поиска, если известено имя папки
 */
public class SecondFindOptionController {

    public SecondFindOptionController() {
    }

    @FXML
    private TextField folderNameTextField;
    @FXML
    private TextField directoryTextField;
    @FXML
    private TextField fileExtensionFiend;
    @FXML
    private TextArea textToSearchArea;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Button backButton;
    @FXML
    private Button searchButton;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private String getFolderNameTextField() {
        return folderNameTextField.getCharacters().toString();
    }

    private String getDirectoryTextField() {
        return directoryTextField.getCharacters().toString();
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
    private void onMouseEnteredLabel1() {
        Bloom bloom = new Bloom();
        label1.setEffect(bloom);
    }

    @FXML
    private void onMouseExitedLabel1() {
        label1.setEffect(null);
    }

    @FXML
    private void onMouseClickedLabel1() {
        label1.setVisible(false);
        label2.setVisible(true);
    }

    @FXML
    private void onMouseClickedLabel2() {
        label1.setVisible(true);
        label2.setVisible(false);
    }

    @FXML
    private void onMouseEnteredTextField1() {
        folderNameTextField.setStyle("-fx-background-color: #313131; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedTextField1() {
        folderNameTextField.setStyle("-fx-background-color: #535353; -fx-text-fill: #cccccc");
    }

    @FXML
    private void onMouseEnteredTextField2() {
        directoryTextField.setStyle("-fx-background-color: #313131; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedTextField2() {
        directoryTextField.setStyle("-fx-background-color: #535353; -fx-text-fill: #cccccc");
    }

    @FXML
    private void onMouseEnteredTextField3() {
        fileExtensionFiend.setStyle("-fx-background-color: #313131; -fx-text-fill: #ffffff");
    }

    @FXML
    private void onMouseExitedTextField3() {
        fileExtensionFiend.setStyle("-fx-background-color: #535353; -fx-text-fill: #cccccc");
    }

    @FXML
    private void onMouseEnteredButton() {
        Bloom bloom = new Bloom();
        searchButton.setEffect(bloom);
    }

    @FXML
    private void onMouseExitedButton() {
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
     * При нажатие на кнопку поиск, производится проверка полей имени папки и начальной директории на пустоту,
     * затем запускается новая сцена с результатами поиска
     */
    @FXML
    private void buttonSearchHandler() {

        if (getFolderNameTextField().equals("") && getDirectoryTextField().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText(null);
            alert.setContentText("Path can't be empty");
            alert.showAndWait();
            return;
        }
        mainApp.showFileFinderByFolderResultScreen(getFolderNameTextField(), getDirectoryTextField(),
                getFileExtensionFiend(), getTextToSearchArea());
    }

    @FXML
    private void buttonBackHandler() {
        mainApp.showStartScreen();
    }
}
