package ru.mishapan.app.view.search;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Описывает экран вывода результатов поиска
 */
public class SearchResultController {

    @FXML
    private TextFlow treeFlow;

    @FXML
    private TextFlow filesFlow;

    private List<Label> labels = new ArrayList<>();

    private List<Path> files;

    @FXML
    private void initialize() {
    }

    public SearchResultController() {
    }

    public void setFiles(List<Path> files) {
        this.files = files;
    }

    public void setTextFlow() {

        files.forEach(file -> {
            Label label = new Label();
            label.setText(file.getFileName().toString());
            label.setTextFill(Color.web("#ffffff"));
            label.setFont(Font.font("System", 14));
            labels.add(label);
        });

        filesFlow.setPadding(new Insets(0, 0, 0, 6));

        labels.forEach(label -> {filesFlow.getChildren().add(label);
        filesFlow.getChildren().add(new Text("\n"));
        });
    }

    public void openFile(Path filepath) {

        try(BufferedReader reader = new BufferedReader(new FileReader(filepath.toString()))){

        } catch (IOException ex) {
        }



    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setTreeFlow(String text) {
        Text text1 = new Text(text);
        text1.setFill(Color.rgb(255, 255, 255));
        text1.setFont(Font.font("System", 14));
        treeFlow.setPadding(new Insets(0, 0, 0, 6));
        treeFlow.getChildren().add(text1);
    }


}
