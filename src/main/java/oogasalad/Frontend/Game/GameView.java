package oogasalad.Frontend.Game;

import static oogasalad.Frontend.Game.TurnKeeper.AI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.GameOverDisplay;
import oogasalad.Frontend.Game.Sections.RightSideSection;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.ArtificialPlayer.Bot;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class will handle the View for the game being played.
 */


public class GameView extends View {

    private static final Logger LOG = LogManager.getLogger(GameView.class);

    private BoardGrid myBoardGrid;
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

    private TurnKeeper turnKeeper;
    private Bot bot;


    public GameView(Stage stage) {
        super(stage);
        GameOver = false;
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relevant to
     * setting up the board.
     */

    public void SetUpBoard(ChessBoard chessboard, int id, boolean singleplayer) {
        myID = id;
        makeConsandRuns();
        myBoardGrid = new BoardGrid(chessboard, id, lightUpCons, MoveCons); //TODO: Figure out player ID stuff
        //myBoardGrid = new BoardGrid(lightUpCons, id, MoveCons); // for testing
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        if (singleplayer) {
            turnKeeper = new TurnKeeper(new String[]{"human", AI});
            bot = new Bot(turnKeeper);
        } else {
            turnKeeper = new TurnKeeper(new String[]{"human", "human"});
        }
    }


    private void makeConsandRuns() {
        lightUpCons = this::lightUpSquares;
        MoveCons = this::makeMove;
        removeGOCons = this::removeGameOverNode;
        flipRun = this::flipBoard;
        servUpRun = this::updateBoard;
    }

    private void makeMove(Coordinate c) {
        LOG.debug("makeMove in GameView reached\n");
        try {
            Collection<TurnUpdate> updates = new ArrayList<>();
            TurnUpdate tu = getGameBackend().getChessBoard().move(myBoardGrid.getSelectedPiece(), c);
            updates.add(tu);
            if (turnKeeper.hasAI()) {
                updates.add(bot.getBotMove(getGameBackend().getChessBoard(), 1));
            }
            updateBoard(updates);
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

    private boolean updateBoard(TurnUpdate tu) {
        LOG.debug("Updating board");
        myBoardGrid.updateTiles(tu.updatedSquares());
        if (getGameBackend().getChessBoard().isGameOver()) {
           gameOver();
           return false;
        }
        return true;
    }

    private void updateBoard(Collection<TurnUpdate> tu) {
        //tu.forEach(this::updateBoard);
        for(TurnUpdate t : tu){
            if(!updateBoard(t)){
                break;
            }
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
        TopSection top = new TopSection();

        top.setExitButton(e -> {
            getView(HomeView.class).ifPresent(this::changeScene);
        });

        bp.setTop(top.getGP());

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
        ChoiceDialog cd = new ChoiceDialog(possPromotions.get(0), possPromotions);
        Optional<Piece> p = cd.showAndWait();
        return p.orElse(null);
    }
}
