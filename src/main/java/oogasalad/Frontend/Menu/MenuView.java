package oogasalad.Frontend.Menu;

/**
 * This class will handle all Windows that are not GameEditor or GameView (basically all set up pop-ups).
 */

import javafx.stage.Stage;

import java.util.ResourceBundle;

public class MenuView {

    private ResourceBundle language;

    public MenuView(Stage stage) {
        LanguageModal languageModal = new LanguageModal(stage);
    }
}
