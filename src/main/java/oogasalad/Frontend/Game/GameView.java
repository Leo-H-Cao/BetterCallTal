package oogasalad.Frontend.Game;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.GameOverDisplay;
import oogasalad.Frontend.Game.Sections.RightSideSection;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Board.TurnUpdate;
import oogasalad.GamePlayer.Movement.Coordinate;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends View {

    private static final Logger LOG = LogManager.getLogger(GameView.class);

    private BoardGrid myBoardGrid;
    private Integer Turn;
    private static Integer myID;
    private BorderPane myBP;
    private Consumer<Piece> lightUpCons;
    private Consumer<Coordinate> MoveCons;
    private Consumer<Node> removeGOCons;
    private Boolean GameOver;
    private StackPane myCenterBoard;
    private Runnable flipRun;
    private RightSideSection myRightSide;
    private Consumer<TurnUpdate> servUpRun;


    public GameView(Stage stage) {
        super(stage);
        GameOver = false;
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relavant to
     * setting up the board.
     */

    public void SetUpBoard(ChessBoard chessboard, int id) {
        Turn = 0;   // give white player first turn
        myID = id;
        makeConsandRuns();
        myBoardGrid = new BoardGrid(chessboard, id, lightUpCons, MoveCons); //TODO: Figure out player ID stuff
        //myBoardGrid = new BoardGrid(lightUpCons, id, MoveCons); // for testing
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);

    }

    private void makeConsandRuns() {
        lightUpCons = piece -> lightUpSquares(piece);
        MoveCons = coor -> makeMove(coor);
        removeGOCons = node -> removeGameOverNode(node);
        flipRun = () -> flipBoard();
        servUpRun = tu -> updateBoard(tu);
    }



    private void makeMove(Coordinate c) {
        LOG.debug("makeMove in GameView reached\n");
        LOG.debug(String.format("Current player: %d", Turn));
        try {
            TurnUpdate tu = getGameBackend().getChessBoard().move(myBoardGrid.getSelectedPiece(), c);
            updateBoard(tu);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.warn("Move failed");
        }
    }

    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Piece p)  {
        LOG.debug("I made it to lightUpSquares method in GameView\n");
        try{
            Collection<ChessTile> possibletiles = getGameBackend().getChessBoard().getMoves(p);
            myBoardGrid.lightSquares(possibletiles);
        }
        catch (EngineException e){
          LOG.error("unexpected error"); //TODO: remove this and handle the exception in the call stack
        }

    }

    /**
     * updateBoard() will be called by the backend when a player clicks a lit up square. All the
     * the logic such as power ups, captures, etc will be registered on the backend, and an updated
     * board will be displayed.
     */

    private void updateBoard(TurnUpdate tu) {
        LOG.debug("Updating board");
        Turn = tu.nextPlayer();
        myBoardGrid.updateTiles(tu.updatedSquares());
        if (getGameBackend().getChessBoard().isGameOver()) {
           gameOver();
        }
    }

    private void gameOver(){
        GameOver = true;
        Map<Integer, Double> scores = getGameBackend().getChessBoard().getScores();
        GameOverDisplay godisp = new GameOverDisplay(scores, removeGOCons);
        StackPane.setAlignment(godisp.getDisplay(), Pos.CENTER);
        myCenterBoard.getChildren().add(godisp.getDisplay());
    }


    @Override
    protected Node makeNode() {
        BorderPane bp = new BorderPane();
        myBP = bp;
        bp.setTop(new TopSection().getGP());

        myCenterBoard = new StackPane();
        myCenterBoard.getChildren().add(myBoardGrid.getBoard());
        bp.setCenter(myCenterBoard);

        myRightSide = new RightSideSection(flipRun);
        bp.setRight(myRightSide.getVbox());

        return bp;
    }

    private void removeGameOverNode(Node n) {
        myCenterBoard.getChildren().remove(n);
    }

    private void flipBoard() {
        myBoardGrid.flip();
    }


    /**
     * RECEIVED PERMISSION FROM DUVALL TO DO THIS
     */
    public static Piece promotionPopUp(List<Piece> possPromotions){
        return possPromotions.get(0);
    }
}
