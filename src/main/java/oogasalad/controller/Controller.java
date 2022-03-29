package oogasalad.controller;

import javafx.stage.Stage;
import oogasalad.Frontend.MainView;

public class Controller {

    private MainView myView;

    public Controller(Stage stage, Runnable newControllerHandler) {
        myView = new MainView(stage);
    }
}
