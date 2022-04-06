package oogasalad.Frontend.Game;

import java.util.Collection;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;
import oogasalad.GamePlayer.Board.TurnUpdate;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends View {

    private Collection<ChessTile> myBoard;
    private Collection<Piece> myPieces;
    private BoardGrid myBoardGrid;
    private Integer Turn;
    private static Integer myID;
    private BorderPane myBP;


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
        //myBoardGrid = new BoardGrid(chessboard, id); UNCOMMENT WHEN JSON IS READY
        myBoardGrid = new BoardGrid();
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        myBP.setCenter(myBoardGrid.getBoard());

    }


    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Collection<ChessTile> litTiles) {

    }

    /**
     * updateBoard() will be called by the backend when a player clicks a lit up square. All the
     * the logic such as power ups, captures, etc will be registered on the backend, and an updated
     * board will be displayed.
     */

    public void updateBoard(TurnUpdate tu) {
        Turn = tu.nextPlayer();
        myBoardGrid.updateTiles(tu.updatedSquares());
    }


    @Override
    protected Node makeNode() {
        BorderPane bp = new BorderPane();
        bp.setTop(new TopSection().getGP());
        myBP = bp;
        // REMOVE LATER
        myBoardGrid = new BoardGrid();
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        bp.setCenter(myBoardGrid.getBoard());
        //REMOVE LATLER ^^^

        return bp;
    }
}
