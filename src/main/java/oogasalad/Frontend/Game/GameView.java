package oogasalad.Frontend.Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Frontend.MainView;
import oogasalad.Frontend.SceneView;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends SceneView {

    private Collection<ChessTile> myBoard;
    private Collection<Piece> myPieces;
    private static Integer SCENE_WIDTH_SIZE = 1500;
    private static Integer SCENE_HEIGHT_SIZE = 1000;
    private static final String TITLE = "Title";

    public GameView(MainView mainView) {
        super(mainView);
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relavant to
     * setting up the board.
     */

    public void SetUpBoard(Collection<ChessTile> board, Collection<Piece> pieces) {
        myBoard = board;
        myPieces = pieces;
    }


    /**
     * lightUpSquares() will be called to make the correct squares light up when a piece is selected.
     * Only when a square is lit up and clicked will a move be made.
     */

    public void lightUpSquares(Collection<ChessTile> litTiles) {

    }

    /**
     * completeMove() will be called by the backend when a player clicks a lit up square. All the
     * the logic such as power ups, captures, etc will be registered on the backend, and an updated
     * board will be displayed.
     */
    public void completeMove(Collection<ChessTile> newboard, Collection<Piece> newpieces){}

    @Override
    protected Scene makeScene() {
        BorderPane bp = new BorderPane();
        bp.setTop(makeTopSection());
        myRoot.getChildren().add(bp);
        return new Scene(myRoot, SCENE_WIDTH_SIZE, SCENE_HEIGHT_SIZE);
    }

    private GridPane makeTopSection() {
        GridPane gp = new GridPane();
        setTopColConstraints(gp);
        ArrayList<HBox> Hboxes = createHBoxes();
        gp.add(Hboxes.get(0), 0, 0);
        gp.add(Hboxes.get(1), 1, 0);
        gp.add(Hboxes.get(2), 2, 0);
        return gp;
    }

    private void setTopColConstraints(GridPane gridpane) {
        gridpane.getColumnConstraints().add(new ColumnConstraints(500));
        gridpane.getColumnConstraints().add(new ColumnConstraints(500));
        gridpane.getColumnConstraints().add(new ColumnConstraints(500));
    }

    private ArrayList<HBox> createHBoxes() {
        ArrayList<HBox> hboxes = new ArrayList<>();
        HBox left = new HBox();
        left.setAlignment(Pos.CENTER_LEFT);
        Button Exit = makeExitButton();
        left.getChildren().add(Exit);
        hboxes.add(left);

        HBox middle = new HBox();
        middle.setAlignment(Pos.CENTER);
        Text title = new Text(MainView.getLanguage().getString(getClass().getSimpleName() + TITLE));
        title.setFont(new Font("Courier New", 30));
        title.setFill(Color.PURPLE);
        middle.getChildren().add(title);
        hboxes.add(middle);

        HBox right = new HBox(new Text("Image here maybe"));
        right.setAlignment(Pos.CENTER);
        hboxes.add(right);
        return hboxes;
    }
}