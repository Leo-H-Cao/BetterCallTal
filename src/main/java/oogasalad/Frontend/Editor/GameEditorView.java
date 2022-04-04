package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.scene.Scene;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.SceneView;


public class GameEditorView extends SceneView {
    public GameEditorView(MainView mainView) {
        super(mainView);
    }

    @Override
    protected Scene makeScene() {
        Scene scene = new Scene(myRoot);
        myRoot.getChildren().add(makeExitButton());
        return scene;
    }
}
