package ru.mishapan.app.view.search;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;

/**
 * контроллер, описывающий экран открытого в новой кладке файла
 */
public class OpenedFileController {

    private int pageLines = 0;
    private final int LINES_IN_PAGE = 23;
    private Path filePath;
    private Map<String, List<Integer>> linesWithMatchesMap = new HashMap<>();

    @FXML
    private TextFlow fileTextFlow;
    @FXML
    private TextFlow pageTextFlow;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label pageLabel;
    @FXML
    private Label fileNameLabel;

    public OpenedFileController() {
    }

    public void setLinesWithMatchesMap(Map<String, List<Integer>> linesWithMatchesMap) {
        this.linesWithMatchesMap = linesWithMatchesMap;
    }

    public Map<String, List<Integer>> getLinesWithMatchesMap() {
        System.out.println(linesWithMatchesMap.size());
        return linesWithMatchesMap;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
        fileNameLabel.setText(filePath.getFileName().toString());
    }

    /**
     * Открывает файл и выводит на экран одну стринцу текста
     */
    public void setFileTextFlow() {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString(), Charset.defaultCharset()))) {

            StringBuilder sb = new StringBuilder();
            String line;
            int lineCounter = 0;

            for (int i = 0; i < pageLines; i++) {
                line = br.readLine();
            }
            while ((line = br.readLine()) != null && lineCounter < LINES_IN_PAGE) {
                sb.append(line);
                sb.append("\n");

                lineCounter++;
            }

            Text text1 = new Text(sb.toString());
            text1.setFill(Color.web("#ffffff"));
            text1.setFont(Font.font("System", 14));
            fileTextFlow.setPadding(new Insets(0, 0, 0, 6));
            fileTextFlow.getChildren().add(text1);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Выводит на экран номера страниц, на которых найден текст
     */
    public void setPageTextFlow() {

        if (linesWithMatchesMap.size() == 0) {
            return;
        }

        Set<Integer> pagesSet = new HashSet<>();
        linesWithMatchesMap.get(filePath.toString()).forEach((pageLine) -> {
            int page = pageLine / LINES_IN_PAGE + 1;
            pagesSet.add(page);
        });

        pagesSet.forEach((page) -> {

            Label label = new Label(String.valueOf(page));

            label.setOnMouseClicked(event -> {
                pageLines = (page - 1) * LINES_IN_PAGE;
                fileTextFlow.getChildren().clear();
                pageLabel.setText(Integer.toString(page));
                setFileTextFlow();
            });

            label.setOnMouseEntered(event -> {
                label.setTextFill(Color.web("#ff7b00"));
                label.setFont(Font.font(15));
            });

            label.setOnMouseExited(event -> {
                label.setTextFill(Color.web("#ffffff"));
                label.setFont(Font.font(14));
            });

            label.setTextFill(Color.web("#ffffff"));
            label.setFont(Font.font("System", 14));
            pageTextFlow.getChildren().add(label);
            pageTextFlow.getChildren().add(new Text("\n"));
        });
    }

    /**
     * Открывает предыдущую страницу текста
     */
    @FXML
    private void PreviousButtonHandler() {
        pageLines = pageLines - LINES_IN_PAGE;
        if (pageLines <= 0) {
            pageLines = 0;
        }
        fileTextFlow.getChildren().clear();
        setFileTextFlow();
        pageLabel.setText(Integer.toString((pageLines / 23) + 1));
    }

    /**
     * Открывает следущую страницу текста
     */
    @FXML
    private void NextButtonHandler() {
        pageLines = pageLines + LINES_IN_PAGE;
        fileTextFlow.getChildren().clear();
        setFileTextFlow();
        pageLabel.setText(Integer.toString((pageLines / 23) + 1));
    }

    @FXML
    private void onMouseEnteredPreviousButton() {
        previousButton.setEffect(new Bloom());
    }

    @FXML
    private void onMouseExitedPreviousButton() {
        previousButton.setEffect(new DropShadow());
    }

    @FXML
    private void onMouseEnteredNextButton() {
        nextButton.setEffect(new Bloom());
    }

    @FXML
    private void onMouseExitedNextButton() {
        nextButton.setEffect(new DropShadow());
    }
}
