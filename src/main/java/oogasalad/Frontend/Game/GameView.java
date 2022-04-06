package oogasalad.Frontend.Game;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnUpdate;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends View {

    private Collection<Piece> myPieces;
    private BoardGrid myBoardGrid;
    private Integer Turn;
    private static Integer myID;
    private BorderPane myBP;
    private Consumer<Piece> lightUpCons;
    private Consumer<Coordinate> MoveCons;


    public GameView(MainView mainView) {
        super(mainView);
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
        //myBoardGrid = new BoardGrid(chessboard, id, lightUpCons); //TODO: Figure out player ID stuff
        myBoardGrid = new BoardGrid(lightUpCons, id, MoveCons);
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        myBP.setCenter(myBoardGrid.getBoard());

    }

    private void makeConsumers() {
        lightUpCons = piece -> lightUpSquares(piece);
        MoveCons = coor -> makeMove(coor);
    }



    private void makeMove(Coordinate c) {
        try {
            TurnUpdate tu = getMainView().getMyBackend().getChessBoard().move(myBoardGrid.getSelectedPiece(), c);
            updateBoard(tu);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Piece p) {
        Collection<ChessTile> possibletiles = getMainView().getMyBackend().getChessBoard().getMoves(p);
        myBoardGrid.lightSquares(possibletiles);
    }

    /**
     * updateBoard() will be called by the backend when a player clicks a lit up square. All the
     * the logic such as power ups, captures, etc will be registered on the backend, and an updated
     * board will be displayed.
     */

    private void updateBoard(TurnUpdate tu) {
        Turn = tu.nextPlayer();
        myBoardGrid.updateTiles(tu.updatedSquares());
    }


    @Override
    protected Node makeNode() {
        BorderPane bp = new BorderPane();
        bp.setTop(new TopSection().getGP());
        myBP = bp;
        // REMOVE LATER
        makeConsumers();
        myBoardGrid = new BoardGrid(lightUpCons, 0, MoveCons);
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        bp.setCenter(myBoardGrid.getBoard());
        //REMOVE LATLER ^^^

        return bp;
    }
}
