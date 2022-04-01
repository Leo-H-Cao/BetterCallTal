package oogasalad.Frontend.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.SceneView;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */

public class HomeView extends SceneView {

    private VBox myVbox;
    private static final String RESOURCE_PATH = "HomeView";
    private static final String[] ButtonOptions = {"Create", "Join", "Host"};
    private static final String TITLE = "Title";
    private static final int VBOX_SPACING = 5;
    private static final int SCREEN_SIZE = 500;

    public HomeView() {
    }

    @Override
    protected Scene makeScene() {

        Scene scene = new Scene(myRoot, 500, 500);
        myVbox = new VBox(VBOX_SPACING);
        myVbox.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);


        Text t = new Text(MainView.getLanguage().getString(RESOURCE_PATH + TITLE));
        t.setFont(new Font(64));
        t.setWrappingWidth(SCREEN_SIZE);
        myVbox.getChildren().add(t);

        Button[] buttons = makeButtons();

        //TODO: Figure out setVgrow
        //VBox.setVgrow(buttons[0], Priority.ALWAYS);
        //VBox.setVgrow(buttons[1], Priority.ALWAYS);
        //VBox.setVgrow(buttons[2], Priority.ALWAYS);

        myVbox.getChildren().addAll(buttons);
        myRoot.getChildren().add(myVbox);

        return scene;
    }

    private Button[] makeButtons() {
        Button[] ret = new Button[ButtonOptions.length];
        for (int i=0; i < ButtonOptions.length; i++) {
            ret[i] = new Button(MainView.getLanguage().getString(RESOURCE_PATH + ButtonOptions[i]));
            ret[i].setPrefSize(SCREEN_SIZE, SCREEN_SIZE);
            ret[i].setAlignment(Pos.CENTER);
        }
        return ret;
    }
}
