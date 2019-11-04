package ru.mishapan.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mishapan.app.model.directory.DirectoryTreeCreator;
import ru.mishapan.app.model.file.FileWorker;
import ru.mishapan.app.view.StartScreenController;
import ru.mishapan.app.view.search.OpenedFileController;
import ru.mishapan.app.view.search.SearchResultController;
import ru.mishapan.app.view.search.SearchSettingsController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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
     * Добавляет пути к найденным файлам в контроллер
     * Добавляет фукнцию открытия файлов в новой вкладке по щелчку
     *
     * @param path       путь к папке, в которой будет производится поиск файлов с заданным расширением
     * @param extension  расширение
     * @param text       текст поиска
     * @param controller контроллер страницы, на которой выводится результат поиска
     * @throws Exception при ошибках поиска
     */
    public void prepareSearchResultScreen(String path, String extension, String text,
                                          SearchResultController controller) throws Exception {

        FileWorker fileWorker = new FileWorker();

        List<Path> files = fileWorker.findTextInFiles(fileWorker.findFiles(Paths.get(path), extension), text);

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

        controller.setFiles(files);
        controller.setFilesTextFlow();
        controller.setTreeFlow(sb1.toString());
        controller.setMainApp(this);

        for (Label label : controller.getLabels()) {
            label.setOnMouseClicked(event -> {
                for (Path path1 : controller.getFiles()) {
                    if (path1.getFileName().toString().equals(label.getText())) {
                        controller.openFileTab(path1, fileWorker.getLinesWithMatchesMap());
                        break;
                    }
                }
            });
        }

    }

    /**
     * Загружает страницу, отражающую результат поиска, помещает ее в новую вкладку на панели вкладок
     * Вывызвает метод, подготавливающий данные к отображению
     * Выводит стрицу с результатами поиска
     *
     * @param path      путь к папке, в которой будет производится поиск файлов с заданным расширением
     * @param extension расширение
     * @param text      текст поиска
     * @throws Exception при ошибках поиска
     */
    public void snowSearchResultScreen(String path, String extension, String text) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/search/SearchResult.fxml"));
        AnchorPane anchorPane = loader.load();

        Stage resultStage = new Stage();
        resultStage.setTitle("Finding results");

        SearchResultController controller = loader.getController();

        prepareSearchResultScreen(path, extension, text, controller);

        Tab tab = new Tab("Home");
        tab.setContent(anchorPane);
        tab.setClosable(false);
        tab.setStyle("-fx-background-color: #cccccc");
        controller.getTabPane().getTabs().add(tab);

        AnchorPane tabScreen = new AnchorPane(controller.getTabPane());

        Scene scene = new Scene(tabScreen);
        resultStage.setScene(scene);
        resultStage.showAndWait();
    }

    /**
     * Показывает страницу, откажающую открытый файл
     * Страница открывается в новой вкладке
     *
     * @param filePath               путь к файлу, который необходимо открыть
     * @param map                    коллекция, содержащая пути к  файлам с найденным текстом и номера строк,
     *                               в которых был найден текст
     * @param searchResultController контроллер, в котором располежен TabPane
     */
    public void showOpenedFileScreen(Path filePath, Map<String, List<Integer>> map,
                                     SearchResultController searchResultController) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/search/OpenedFile.fxml"));
            AnchorPane anchorPane = loader.load();

            OpenedFileController controller = loader.getController();
            controller.setFilePath(filePath);
            controller.setLinesWithMatchesMap(map);
            controller.setFileTextFlow();
            controller.setPageTextFlow();

            Tab tab = new Tab(filePath.getFileName().toString());
            tab.setContent(anchorPane);
            tab.setStyle("-fx-background-color: #cccccc");

            searchResultController.getTabPane().getTabs().add(tab);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showTutorialScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Tutorial.fxml"));
            AnchorPane anchorPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("How to");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}