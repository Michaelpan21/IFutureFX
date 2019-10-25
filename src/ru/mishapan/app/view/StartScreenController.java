package ru.mishapan.app.view;

import javafx.fxml.FXML;
import ru.mishapan.app.MainApp;

public class StartScreenController {

    public StartScreenController(){}

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLeftButton(){
        mainApp.showFileFinderByPathScreen();
    }

    @FXML
    private void handleRightButton(){
        mainApp.showFileFinderByFolderScreen();
    }
}
