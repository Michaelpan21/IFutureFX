package ru.mishapan.app.view.search;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.mishapan.app.MainApp;

import java.io.File;

/**
 * контроллер, описывающий экран поиска
 */
public class SearchSettingsController {

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
        } else {
            pathTextField.setText(null);
        }

        primaryStage.setTitle("Choose directory");
        primaryStage.show();
    }

    @FXML
    private void onMouseEnteredTextField1() {
        pathTextField.setEffect(new DropShadow());
    }

    @FXML
    private void onMouseExitedTextField1() {
        pathTextField.setEffect(null);
    }

    @FXML
    private void onMouseEnteredTextField2() {
        fileExtensionFiend.setEffect(new DropShadow());
    }

    @FXML
    private void onMouseExitedTextField2() {
        fileExtensionFiend.setEffect(null);
    }

    @FXML
    private void onMouseEnteredSearchButton() {
        searchButton.setEffect(new Bloom());
    }

    @FXML
    private void onMouseExitedSearchButton() {
        searchButton.setEffect(new DropShadow());
    }

    @FXML
    private void onMouseEnteredBackButton() {
        backButton.setEffect(new Bloom());
    }

    @FXML
    private void onMouseExitedBackButton() {
        backButton.setEffect(null);
    }

    @FXML
    private void onMouseEnteredDirectoryButton() {
        directoryButton.setEffect(new Bloom());
    }

    @FXML
    private void onMouseExitedDirectoryButton() {
        directoryButton.setEffect(new DropShadow());
    }

    /**
     * При нажатие на кнопку поиск, производится проверка поля путь на пустоту, затем запускается новая сцена
     * с результатами поиска
     * Выводит предупреждающее окно, если данные для поиска введены неправильно
     */
    @FXML
    private void buttonSearchHandler() {

        try {
            if (getPathTextField().equals("")) {
                throw new IllegalArgumentException("Path to folder can't be empty");
            }
            mainApp.snowSearchResultScreen(getPathTextField(), getFileExtensionFiend(), getTextToSearchArea());

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Alert");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void buttonBackHandler() {
        mainApp.showStartScreen();
    }
}
