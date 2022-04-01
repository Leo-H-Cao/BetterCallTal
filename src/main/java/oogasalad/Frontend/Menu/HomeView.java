package oogasalad.Frontend.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.SceneView;

import java.util.Arrays;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */

public class HomeView extends SceneView {

    private VBox myVbox;
    private static final String[] ButtonOptions = {"HomeViewCreate", "HomeViewJoin", "HomeViewHost"};
    private static final String TITLE = "HomeViewTitle";

    public HomeView() {
    }

    @Override
    protected Scene makeScene() {

        Scene scene = new Scene(myRoot);
        myVbox = new VBox();

        myRoot.prefWidth(500);
        myRoot.prefHeight(700);

        Text t = new Text(MainView.getLanguage().getString(TITLE));
        t.setFont(new Font(64));
        myVbox.getChildren().add(t);

        Button[] buttons = makeButtons();

        // TODO: Figure out setVgrow
        // VBox.setVgrow(buttons[0], Priority.ALWAYS);
        // VBox.setVgrow(buttons[1], Priority.ALWAYS);
        // VBox.setVgrow(buttons[2], Priority.ALWAYS);

        myVbox.getChildren().addAll(buttons);
        myRoot.getChildren().add(myVbox);

        return scene;
    }

    private Button[] makeButtons() {
        Button[] ret = new Button[ButtonOptions.length];
        for (int i=0; i < ButtonOptions.length; i++) {
            ret[i] = new Button(MainView.getLanguage().getString(ButtonOptions[i]));
            ret[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        return ret;
    }
}
