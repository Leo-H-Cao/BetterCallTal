package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameEditorView {
    private Scene myScene;
    private Group myRoot;

    public GameEditorView() {
        myRoot = new Group();
        myScene = new Scene(myRoot);
    }

    private Scene getScene() {
        return myScene;
    }
}
