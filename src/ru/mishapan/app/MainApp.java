package ru.mishapan.app;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mishapan.app.model.directory.DirectoryTreeCreator;
import ru.mishapan.app.model.file.FileWorker;
import ru.mishapan.app.view.StartScreenController;
import ru.mishapan.app.view.search.SearchSettingsController;
import ru.mishapan.app.view.search.SearchResultController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Главный класс приложения
 */
public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IFuture Text Finder");

        showStartScreen();
    }

    /**
     * Показывает главный экран
     */
    public void showStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/StartScreen.fxml"));
            AnchorPane startScreen = loader.load();

            Scene scene = new Scene(startScreen);

            primaryStage.setScene(scene);
            primaryStage.show();

            StartScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает экран поиска при известном пути
     */
    public void showSearchSettingsScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/search/SearchSettings.fxml"));

            AnchorPane anchorPane = loader.load();

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            SearchSettingsController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Производит поиск файлов по заданному пути, затем поиск среди этих файлов файлов с заданным текстом
     * Показывает экран результата поиска при известном пути
     *
     * @param path      путь к папке, в которой будет производится поиск файлов с заданным расширением
     * @param extension расширение
     * @param text      текст поиска
     */
    public void showSearchResultScreen(String path, String extension, String text) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/search/SearchResult.fxml"));
            AnchorPane anchorPane = loader.load();

            Stage resultStage = new Stage();
            resultStage.setTitle("Finding results");
            resultStage.initModality(Modality.WINDOW_MODAL);
            resultStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            resultStage.setScene(scene);


            FileWorker fileWorker = new FileWorker();
            List<Path> files;

            try {
                files = fileWorker.findTextInFiles(fileWorker.findFiles(Paths.get(path), extension), text);

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Alert");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            DirectoryTreeCreator treeCreator = new DirectoryTreeCreator();
            StringBuilder sb1 = new StringBuilder();
            int count = 1;
            String[] folders = treeCreator.createTree(Paths.get(path));
            for (String folder : folders) {
                sb1.append(folder);
                if (count == folders.length) {
                    break;
                }

                sb1.append("\n").append("   ".repeat(count));
                count++;
            }

            SearchResultController controller = loader.getController();
            controller.setFiles(files);
            controller.setTextFlow();
            controller.setTreeFlow(sb1.toString());

            for (Label label : controller.getLabels()){
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        controller.openFile(Paths.get(label.getText()));
                    }
                });
            }

            resultStage.showAndWait();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}