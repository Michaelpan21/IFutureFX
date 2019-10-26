package ru.mishapan.app.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.mishapan.app.MainApp;

public class FirstFindOptionController {

    public FirstFindOptionController() {
    }

    @FXML
    private TextField pathTextField;
    @FXML
    private TextField fileExtensionFiend;
    @FXML
    private TextArea textToSearchArea;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private String getPathTextField() {
        return pathTextField.getCharacters().toString();
    }

    private String getFileExtensionFiend() {
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
    private void buttonSearchHandler() {
        mainApp.showFileFinderByPathResultScreen(getPathTextField(), getFileExtensionFiend(), getTextToSearchArea());
    }
}
