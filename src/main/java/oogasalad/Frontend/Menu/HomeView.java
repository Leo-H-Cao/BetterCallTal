package oogasalad.Frontend.Menu;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */

public class HomeView {

    private Scene myScene;
    private Group myRoot;

    public HomeView() {
        myRoot = new Group();
        myScene = new Scene(myRoot);
    }

    public Scene getScene() {return myScene;}
}
