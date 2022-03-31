package oogasalad.Frontend.Menu;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.SceneView;

/**
 * HomeView class will handle the navigation of the User from the home screen to the next page they seek.
 * Home Screen is the screen that displays "Build Game," "Join Game," and "Host Game"
 */

public class HomeView extends SceneView {

    @Override
    protected Scene makeScene() {
        Scene scene = new Scene(myRoot);
        myRoot.prefWidth(500);
        myRoot.prefHeight(700);
        Text t = new Text("BETTER CALL TAL HOME SCREEN");
        t.setFont(new Font(64));
        VBox vBox = new VBox();
        vBox.getChildren().add(t);
        myRoot.getChildren().add(vBox);
        return scene;
    }
}
