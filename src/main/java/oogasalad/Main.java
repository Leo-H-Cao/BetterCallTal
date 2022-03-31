package oogasalad;


import javafx.stage.Stage;
import javafx.application.Application;
import oogasalad.Frontend.Menu.LanguageModal;
import oogasalad.controller.Controller;
import java.util.List;

import java.util.ArrayList;
/**
 * Main class to be run by the user, will launch the program.
 */

public class Main extends Application {

    private static List<Controller> myControllers;
    public static final String TITLE = "Better Call Tal";

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
