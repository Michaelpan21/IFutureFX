package ru.mishapan.app;


import java.io.IOException;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mishapan.app.model.file.FileFinder;
import ru.mishapan.app.view.FirstFindOptionController;
import ru.mishapan.app.view.FirstResultController;
import ru.mishapan.app.view.StartScreenController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IFuture Text Finder");

        showStartScreen();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void showFileFinderByPathScreen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FirstFindOption.fxml"));

            AnchorPane anchorPane = loader.load();

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            FirstFindOptionController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showFileFinderByPathResultScreen(String path, String glob, String text) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FirstFindOptionResult.fxml"));
            AnchorPane anchorPane = loader.load();

            Stage resultStage = new Stage();
            resultStage.setTitle("Finding results");
            resultStage.initModality(Modality.WINDOW_MODAL);
            resultStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            resultStage.setScene(scene);


            FileFinder fileFinder = new FileFinder();

            StringBuilder sb = new StringBuilder();

            fileFinder.findTextInFiles(fileFinder.findFiles(Paths.get(path), glob), text).forEach(
                    path1 -> {
                        sb.append(path1.getFileName());
                        sb.append("\n");
                    }
            );

            FirstResultController controller = loader.getController();
            controller.setTextFlow(sb.toString());

            resultStage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showFileFinderByFolderScreen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/sample.fxml"));

            AnchorPane anchorPane = loader.load();

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();

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