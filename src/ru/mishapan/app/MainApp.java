package ru.mishapan.app;


import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mishapan.app.model.directory.DirectoryTreeCreator;
import ru.mishapan.app.model.directory.FolderFinder;
import ru.mishapan.app.model.file.FileWorker;
import ru.mishapan.app.view.firstBranch.FirstFindOptionController;
import ru.mishapan.app.view.firstBranch.FirstResultController;
import ru.mishapan.app.view.StartScreenController;
import ru.mishapan.app.view.secondBranch.SecondFindOptionController;
import ru.mishapan.app.view.secondBranch.SecondResultController;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("IFuture Text Finder");

        showStartScreen();
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
            loader.setLocation(MainApp.class.getResource("view/firstBranch/FirstFindOption.fxml"));

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
            loader.setLocation(MainApp.class.getResource("view/firstBranch/FirstFindOptionResult.fxml"));
            AnchorPane anchorPane = loader.load();

            Stage resultStage = new Stage();
            resultStage.setTitle("Finding results");
            resultStage.initModality(Modality.WINDOW_MODAL);
            resultStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            resultStage.setScene(scene);


            FileWorker fileWorker = new FileWorker();
            StringBuilder sb = new StringBuilder();

            try {
                fileWorker.findTextInFiles(fileWorker.findFiles(Paths.get(path), glob), text).forEach(
                        path1 -> {
                            sb.append(path1.getFileName());
                            sb.append("\n");
                        }
                );

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

            FirstResultController controller = loader.getController();
            controller.setTextFlow(sb.toString());
            controller.setTreeFlow(sb1.toString());

            resultStage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showFileFinderByFolderScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/secondBranch/SecondFindOption.fxml"));

            AnchorPane anchorPane = loader.load();

            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();

            SecondFindOptionController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showFileFinderByFolderResultScreen(String folderName, String startDir, String glob, String text) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/secondBranch/SecondFindOptionResult.fxml"));
            AnchorPane anchorPane = loader.load();

            Stage resultStage = new Stage();
            resultStage.setTitle("Finding results");
            resultStage.initModality(Modality.WINDOW_MODAL);
            resultStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            resultStage.setScene(scene);


            FileWorker fileWorker = new FileWorker();
            SecondResultController controller = loader.getController();

            try {
                FolderFinder folderFinder = new FolderFinder();
                folderFinder.findFolders(folderName, Paths.get(startDir), folderFinder).forEach(path -> {

                    StringBuilder sb = new StringBuilder();

                    try {
                        fileWorker.findTextInFiles(fileWorker.findFiles(path, glob), text).forEach(
                                path1 -> {
                                    sb.append(path1.getFileName());
                                    sb.append("\n");
                                }
                        );

                        DirectoryTreeCreator treeCreator = new DirectoryTreeCreator();
                        StringBuilder sb1 = new StringBuilder();
                        int count = 1;
                        String[] folders = treeCreator.createTree(path);

                        for (String folder : folders) {
                            sb1.append(folder);
                            if (count == folders.length) {
                                break;
                            }
                            sb1.append("\n").append("   ".repeat(count));
                            count++;
                        }

                        controller.setTextFlow(path.toString().concat("\n").concat(sb.toString()).concat("\n\n"));
                        controller.setTreeFlow(sb1.toString().concat("\n\n"));
                    } catch (Exception ex) {
                    }
                });

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Alert");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                return;
            }

            resultStage.showAndWait();

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