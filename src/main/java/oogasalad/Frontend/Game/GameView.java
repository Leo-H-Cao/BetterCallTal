package oogasalad.Frontend.Game;

import static oogasalad.Frontend.Game.TurnKeeper.AI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.History.BoardHistory;
import oogasalad.Frontend.Game.History.BoardHistoryPanel;
import oogasalad.Frontend.Game.Sections.BoardGrid;
import oogasalad.Frontend.Game.Sections.GameOverDisplay;
import oogasalad.Frontend.Game.Sections.LeftSection;
import oogasalad.Frontend.Game.Sections.TopSection;
import oogasalad.Frontend.Menu.HomeView;
import oogasalad.Frontend.Menu.LocalPlay.RemotePlayer.RemotePlayer;
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
    public static final String SERVER = "server";
    public static final String MULTIPLAYER = "multiplayer";
    public static final String SINGLEPLAYER = "singleplayer";
    public static final String HUMAN = "human";

    private BoardGrid myBoardGrid;
    private static Integer myID;
    private Consumer<Piece> lightUpCons;
    private Consumer<Coordinate> MoveCons;
    private Consumer<Node> removeGOCons;
    private StackPane myCenterBoard;
    private Runnable flipRun;
    private LeftSection myLeftSide;
    private TopSection myTopSection;
    private Consumer<TurnUpdate> servUpRun;
    private BiConsumer<String, String> errorRun;
    private Boolean isServer;
    private Button Flip;

    private TurnKeeper turnKeeper;
    private List<RemotePlayer> remotePlayers;

    private BoardHistory myBoardHistory;
    private BoardHistoryPanel myHistoryPanel;


    public GameView(Stage stage) {
        super(stage);
        remotePlayers = new ArrayList<>();
    }

    /**
     * setUpBoard method will be called when the user hosts a game and uploads a JSon game file.
     * The back-end will parse the information and call this method with objects that contain
     * where the starting pieces, power ups, etc. go, and any other information relevant to
     * setting up the board.
     */

    public void SetUpBoard(ChessBoard chessboard, int id, String mode) {

        myID = chessboard.getThisPlayer();
        isServer = false;  // getGameBackend().getChessBoard().getGameType().equals("SERVER");
        makeConsandRuns();
        myBoardHistory = new BoardHistory();
        myBoardGrid = new BoardGrid(chessboard, lightUpCons, MoveCons, errorRun, myBoardHistory); //TODO: Figure out player ID stuff
        //myBoardGrid = new BoardGrid(lightUpCons, id, MoveCons); // for testing
        myBoardGrid.getBoard().setAlignment(Pos.CENTER);
        remotePlayers = new ArrayList<>();
        String[] splitMode = mode.split(" ");
        switch (splitMode[0]) {
            case SERVER -> turnKeeper = new TurnKeeper(new String[]{HUMAN, SERVER});
            case SINGLEPLAYER -> {
                turnKeeper = new TurnKeeper(new String[]{HUMAN, AI});
                remotePlayers.add(new Bot(turnKeeper, splitMode[1]));
            }
            case MULTIPLAYER -> turnKeeper = new TurnKeeper(new String[]{HUMAN, HUMAN});
        }
        chessboard.setShowAsyncError(this::showmyError);
        chessboard.setPerformAsyncTurnUpdate(this::updateBoard);
    }


    private void makeConsandRuns() {
        lightUpCons = this::lightUpSquares;
        MoveCons = this::makeMove;
        removeGOCons = this::removeGameOverNode;
        flipRun = this::flipBoard;
    }

    private void makeMove(Coordinate c) {
        makeKeyListener();
        LOG.debug("makeMove in GameView reached\n");
        try {
            if (!myBoardHistory.isOnRecent()) throw new Exception("Please make sure your board is the most current! Hint, press D until no noticeable visual changes occur");
            Collection<TurnUpdate> updates = new ArrayList<>();
            TurnUpdate tu = getGameBackend().getChessBoard().move(myBoardGrid.getSelectedPiece(), c);
            updates.add(tu);
            if (turnKeeper.hasRemote()) {
                remotePlayers.forEach(e -> {
                    try {
                        updates.add(e.getRemoteMove(getGameBackend().getChessBoard(), 1));
                    } catch (Throwable ex) {
                        getGameBackend().showError(ex.getClass().getSimpleName(), ex.getMessage());
                    }
                });
            }
            updateBoard(updates);
            myHistoryPanel.add(updates);
        } catch (Exception e){
            getGameBackend().showError(e.getClass().getSimpleName(), e.getMessage());
            LOG.warn("Move failed");
        } catch (Throwable t) {
            LOG.warn(t.getMessage());
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

        if (isServer) {
            if (myID != tu.nextPlayer()) {
                myLeftSide.dispServWait(true);
            } else {
                myLeftSide.dispServWait(false);
            }
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
        updateHistory(getGameBackend().getChessBoard().getHistory().getCurrentBoard());
    }

    private void updateHistory(ChessBoard board) {
        myBoardHistory.update(board);
    }

    private void gameOver(){
        Map<Integer, Double> scores = getGameBackend().getChessBoard().getScores();
        GameOverDisplay godisp = new GameOverDisplay(scores, removeGOCons);
        StackPane.setAlignment(godisp.getDisplay(), Pos.CENTER);
        myCenterBoard.getChildren().add(godisp.getDisplay());
    }


    @Override
    protected Node makeNode() {
        BorderPane bp = new BorderPane();

        myTopSection = new TopSection();

        myTopSection.setExitButton(e -> {
            getView(HomeView.class).ifPresent(this::changeScene);
        });

        bp.setTop(myTopSection.getGP());

        myCenterBoard = new StackPane();
        myCenterBoard.getChildren().add(myBoardGrid.getBoard());
        bp.setCenter(myCenterBoard);

        myLeftSide = new LeftSection(flipRun);
        bp.setLeft(myLeftSide.getVbox());

        myHistoryPanel = new BoardHistoryPanel();
        bp.setRight(myHistoryPanel.makeNode());

        setFlipButton(); //ONLY FOR TESTING GAMEVIEW, IGNORE THIS
        return bp;
    }

    private void removeGameOverNode(Node n) {
        myCenterBoard.getChildren().remove(n);
    }

    private void flipBoard() {
        myBoardGrid.flip();
    }

    private void showmyError(String classname, String message){
        getGameBackend().showError(classname, message);
    }

    private void makeKeyListener() {
        myScene.setOnKeyPressed(e -> {
            System.out.println(e.getCode());
            switch (e.getCode()) {
                case A -> onLeftKey();
                case D -> onRightKey();
            }
        });
    }

    private void onLeftKey() {
        LOG.debug("LEFT KEY PRESSED");
        myBoardGrid.updateTiles(myBoardHistory.previous().getBoard().stream().flatMap(List::stream).toList());

    }

    private void onRightKey() {
        LOG.debug("RIGHT KEY PRESSED");
        myBoardGrid.updateTiles(myBoardHistory.next().getBoard().stream().flatMap(List::stream).toList());
    }

    /**
     * RECEIVED PERMISSION FROM DUVALL TO DO THIS
     */
    public static Piece promotionPopUp(List<Piece> possPromotions) {
        ChoiceDialog cd = new ChoiceDialog(possPromotions.get(0), possPromotions);
        Optional<Piece> p = cd.showAndWait();
        return p.orElse(null);
    }

    /**
     * PURELY FOR TESTING:::
     */
    public void setFlipButton() {
        Flip = myLeftSide.getFlip();
    }
}
