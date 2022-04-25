package oogasalad.Frontend.Game;

import javafx.stage.Stage;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.GamePlayer.Board.BoardSetup;
import oogasalad.GamePlayer.Board.ChessBoard;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.IOException;
import java.util.ResourceBundle;

public class GameViewTest extends DukeApplicationTest {

    private Stage myStage;
    private GameView myGameView;
    private ResourceBundle myResources;
    private ChessBoard myChessBoard;

    @Override
    public void start (Stage stage) throws IOException {
        myStage = stage;
        myGameView = new GameView(stage);
        myResources = ResourceBundle.getBundle("oogasalad.Frontend.Menu.languages.English");
        BackendConnector.initBackend(myResources);
        myChessBoard = BoardSetup.createRemoteBoard("doc/games/TicTacToe.json", "RoomName", 0);
        myGameView.SetUpBoard(myChessBoard, 0, "Server");
        stage.setScene(myGameView.getScene());
        stage.show();
    }

    @Test
    void testClickOnBoard() {
        clickOn(lookup("#flip").query());
    }
}
