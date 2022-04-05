package oogasalad.Frontend.Editor;
/**
 * This class will handle the view for the Game Editor.
 */

import javafx.scene.Scene;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;


public class GameEditorView extends View {
    private ChessBoardView myChessBoard;

    public GameEditorView(MainView mainView) {
        super(mainView);
        myChessBoard = new ChessBoardView();
    }

    @Override
    protected Scene makeScene() {
        Scene scene = new Scene(myRoot);
        myRoot.getChildren().add(myChessBoard.getNode());
        return scene;
    }
}
