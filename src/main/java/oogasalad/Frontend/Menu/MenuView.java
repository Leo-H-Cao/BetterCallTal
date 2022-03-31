package oogasalad.Frontend.Menu;

/**
 * This class will handle all Windows that are not GameEditor or GameView (basically all set up pop-ups).
 */

import javafx.stage.Stage;


public class MenuView {

    public MenuView(Stage stage) {
        LanguageModal languageModal = new LanguageModal(stage);
        stage.setScene(languageModal.makeScene());
        stage.show();
    }
}
