package oogasalad.Frontend.Menu;

import javafx.scene.control.Alert;

/**
 * This class will handle showing the errors of the program.
 */

public class ErrorPopUp extends Alert {

    public ErrorPopUp(String message) {
        super(AlertType.ERROR, message);
        this.showAndWait();
    }
}
