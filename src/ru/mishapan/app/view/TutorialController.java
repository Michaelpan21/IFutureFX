package ru.mishapan.app.view;

        import javafx.fxml.FXML;
        import javafx.geometry.Insets;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import javafx.scene.text.Text;
        import javafx.scene.text.TextFlow;

public class TutorialController {

    @FXML
    private TextFlow textFlow;

    @FXML
    private void initialize() {
        Text text = new Text("Press Get Started button to start work.\n\n" +
                "Then enter path or choose it manually.\n" +
                "Enter file extension if necessary.\n" +
                "Enter text to search, if you left it empty,\n" +
                "   you will get all files.\n\n" +
                "You can click on found file to open it.\n" +
                "Press buttons previous or next to navigate text.\n" +
                "Press page on right part of screen\n " +
                "   to open page with found text.");
        text.setFont(Font.font("System", 14));
        text.setFill(Color.web("#847979"));
        textFlow.setPadding(new Insets(20, 0, 0, 4));
        textFlow.getChildren().add(text);
    }
}
