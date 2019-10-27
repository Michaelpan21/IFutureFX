package ru.mishapan.app.view.secondBranch;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SecondResultController {

    @FXML
    private TextFlow treeFlow;

    @FXML
    private TextFlow filesFlow;

    @FXML
    private void initialize() {
    }

    public SecondResultController() {
    }

    public void setTextFlow(String text) {
        Text text1 = new Text(text);
        text1.setFill(Color.rgb(255, 255, 255));
        text1.setFont(Font.font("System", 14));
        filesFlow.setPadding(new Insets(0, 0, 0, 6));
        filesFlow.getChildren().add(text1);
    }

    public void setTreeFlow(String text) {
        Text text1 = new Text(text);
        text1.setFill(Color.rgb(255, 255, 255));
        text1.setFont(Font.font("System", 14));
        treeFlow.setPadding(new Insets(0, 0, 0, 6));
        treeFlow.getChildren().add(text1);
    }


}
