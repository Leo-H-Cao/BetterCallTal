package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.Frontend.SceneView;


public class GameEditorView extends SceneView {

    public GameEditorView() {
        myRoot = new Group();
        myScene = new Scene(myRoot);
    }

    @Override
    protected Scene makeScene() {

        return null;
    }
}
