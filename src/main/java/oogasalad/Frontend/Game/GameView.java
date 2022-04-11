package oogasalad.Frontend.Game;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Board.TurnUpdate;
import oogasalad.GamePlayer.Movement.Coordinate;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class will handle the View for the game being played.
 */

public class GameView extends View {
    private static GameBackend myBackend;
    private BoardGrid myBoardGrid;
    private Integer Turn;
    private Integer myID;
    private BorderPane myBP;
    private Consumer<Piece> lightUpCons;
    private Consumer<Coordinate> MoveCons;


    public GameView(Stage stage) {
        super(stage);
        if(myBackend == null) {
            myBackend = new GameBackend();
        }
    }

    protected Optional<GameBackend> getBackend() {
        return Optional.of(myBackend);
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
        makeConsumers();
        myBoardGrid = new BoardGrid(chessboard, id, lightUpCons, MoveCons); //TODO: Figure out player ID stuff
        //myBoardGrid = new BoardGrid(lightUpCons, id, MoveCons); // for testing
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);

    }

    private void makeConsumers() {
        lightUpCons = piece -> {
            try {
                lightUpSquares(piece);
            } catch (EngineException e) {
                e.printStackTrace();
            }
        };
        MoveCons = coor -> makeMove(coor);
    }

    private void makeMove(Coordinate c) {
        LOG.debug("makeMove in GameView reached\n");
        LOG.debug(String.format("Current player: %d", Turn));
        try {
            updateBoard(myBackend.getChessBoard().move(myBoardGrid.getSelectedPiece(), c));
        } catch (EngineException ex) {
            ex.printStackTrace();
            LOG.warn("Move failed");
        }
    }

    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Piece p) throws EngineException {
        LOG.debug("I made it to lightUpSquares method in GameView\n");
        myBoardGrid.lightSquares(myBackend.getChessBoard().getMoves(p));
        getBackend().ifPresent((e) -> {
            try {
                myBoardGrid.lightSquares(e.getChessBoard().getMoves(p));
            } catch (EngineException ex) {
                ex.printStackTrace();
            }
        });
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
    }


    @Override
    protected Node makeNode() {
        BorderPane bp = new BorderPane();
        myBP = bp;
        bp.setTop(new TopSection().getGP());
        bp.setCenter(myBoardGrid.getBoard());
        return bp;
    }
}
