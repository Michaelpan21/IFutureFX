package ru.mishapan.app.view.search;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.mishapan.app.MainApp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private TabPane tabPane = new TabPane();

    private MainApp mainApp;

    @FXML
    private void initialize() {
    }

    public SearchResultController() {
    }

    public void setFiles(List<Path> files) {
        this.files = files;
    }

    /**
     * Добавляет в текстовое поле название файлов, которые подсвечиваются, если на них навести мышкой
     */
    public void setFilesTextFlow() {

        files.forEach(file -> {
            Label label = new Label();
            label.setText(file.getFileName().toString());
            label.setTextFill(Color.web("#ffffff"));
            label.setFont(Font.font("System", 14));
            labels.add(label);
        });

        filesFlow.setPadding(new Insets(0, 0, 0, 6));

        labels.forEach(label -> {
            filesFlow.getChildren().add(label);
            filesFlow.getChildren().add(new Text("\n"));

            label.setOnMouseEntered((event) -> {
                label.setTextFill(Color.web("#ff7b00"));
                label.setFont(Font.font(15));
            });

            label.setOnMouseExited(event -> {
                label.setTextFill(Color.web("#ffffff"));
                label.setFont(Font.font(14));
            });
        });
    }

    public void setTreeFlow(String text) {

        Text text1 = new Text(text);
        text1.setFill(Color.web("#ffffff"));
        text1.setFont(Font.font("System", 14));
        treeFlow.setPadding(new Insets(0, 0, 0, 6));
        treeFlow.getChildren().add(text1);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Path> getFiles() {
        return files;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    /**
     * Вызывает метод, открывающий файл в новой вкладке
     * Передает ссылку на себя, чтобы новая вкладка добавилась в TabPane
     *
     * @param filePath путь к файлу, который необходимо открыть
     * @param map      коллекция, содержащая пути к  файлам с найденным текстом и номера строк,
     *                 в которых был найден текст
     */
    public void openFileTab(Path filePath, Map<String, List<Integer>> map) {
        mainApp.showOpenedFileScreen(filePath, map, this);
    }
}
