package oogasalad.controller;

/**
 * The controller will handle all interactions between components of the project to ensure fluid passing of information.
 */

import javafx.stage.Stage;
import oogasalad.Frontend.MainView;

import java.util.ResourceBundle;

public class Controller {

    private MainView myView;

    public Controller(Stage stage, ResourceBundle rb) {
        myView = new MainView(stage, rb);
    }
}
