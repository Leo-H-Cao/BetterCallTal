package oogasalad;


import javafx.stage.Stage;
import oogasalad.Frontend.MainView;
import javafx.application.Application;
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
        myControllers = new ArrayList<>();
        myControllers.add(new Controller(stage, ()->addController()));
    }

    /**
     * Add a controller when the user clicks new window. Creates a new stage and passes it to new controller.
     */
    private void addController() {
        Stage newStage = new Stage();
        String numWindow = "" + myControllers.size() + 1;
        newStage.setTitle(TITLE + numWindow);
        newStage.show();
        Runnable runHandler = ()-> addController();
        myControllers.add(new Controller(newStage, runHandler));
    }
}
