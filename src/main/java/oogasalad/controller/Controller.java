package oogasalad.controller;

/**
 * The controller will handle all interactions between components of the project to ensure fluid passing of information.
 */

import javafx.stage.Stage;
import oogasalad.Frontend.MainView;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.Server.BoardSetup;

import java.io.File;
import java.util.ResourceBundle;

public class Controller {

    private MainView myView;
    private ChessBoard myChessBoard;

    public Controller(Stage stage, ResourceBundle rb) {
        myView = new MainView(stage, rb);
    }

    public void setJSONFilePath(String filepath) {
        setUpChessBoard(filepath);
    }

    private ChessBoard setUpChessBoard(String fp) {
        try {
            BoardSetup bs = new BoardSetup(fp);
            return bs.createBoard();
        } catch (Exception e) {
            return null;
        }
    }
}
