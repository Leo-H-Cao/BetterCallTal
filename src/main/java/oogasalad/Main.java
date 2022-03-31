package oogasalad;

import javafx.stage.Stage;
import javafx.application.Application;
import oogasalad.Frontend.Menu.LanguageModal;

/**
 * Main class to be run by the user, will launch a language modal splash screen which will then launch the modal program.
 */

public class Main extends Application {

    /**
     * Start of the program.
     */
    @Override
    public void start (Stage stage) {
        LanguageModal languageModal = new LanguageModal(stage);
        stage.setScene(languageModal.makeScene());
        stage.show();
    }
}
