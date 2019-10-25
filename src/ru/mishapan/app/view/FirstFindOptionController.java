package ru.mishapan.app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FirstFindOptionController {

    @FXML
    private TextField pathTextField;

    @FXML
    private void setPathField() {
        this.pathTextField = getPathTextField();
    }

    @FXML
    private TextField getPathTextField() {
        System.out.println(pathTextField.getCharacters());
        return pathTextField;
    }
}
