package oogasalad.Frontend.Menu;

import static oogasalad.Frontend.Game.GameView.SERVER;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class HostGame extends View {
    private StackPane myStackPane;
    private static final String TITLE = "Title";
    private static final String Load_Button_ID = "Upload";
    private static final String Confirm_Button_ID = "HostConfirm";
    private static final String Start_Button_ID = "HostStart";
    private static final String CONFIRM = "Confirm";
    private static final String LOAD = "Load";
    private static final String PROMPT = "Prompt";
    private static final String START = "Start";
    private final Integer TITLE_SIZE = 64;
    private final Integer PROMPT_SIZE = 40;
    private static final String WHITE = "White";
    private static final String BLACK = "Black";
    private static final String ROOM = "Room";
    private Map<String, Integer> piececolors;
    private static final String COLORPROMPT = "COLORPROMPT";
    private static final Double VBOXSPACING = 5.0;
    private ChoiceBox<String> colorchoice;
    private TextArea RoomName;
    private static final Double TEXTAREAWIDTH = 100.0;
    private static final Double TEXTAREAHEIGHT = 20.0;
    private String RoomID;
    private Boolean StartShowing;
    private Node prompt; // MADE GLOBAL FOR TESTING!!!


    public HostGame(Stage stage) {
        super(stage);}

    @Override
    protected Node makeNode() {
        return MakeStackPane();
    }

    private StackPane MakeStackPane() {
        StartShowing = false;
        piececolors = new HashMap<>();
        myStackPane = new StackPane();
        myStackPane.setPrefSize(myScreenSize.getWidth(), myScreenSize.getHeight());
        Node exit = makeExitGroup();
        Node title = makeLabelGroup(BackendConnector.getFrontendWord(TITLE, getClass()), TITLE_SIZE);
        prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT, getClass()), PROMPT_SIZE);
        Node load = makeFileUploadGroup(prompt);

        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(prompt, Pos.CENTER);
        StackPane.setAlignment(load, Pos.CENTER_RIGHT);

        myStackPane.getChildren().addAll(exit, title, prompt, load);
        return myStackPane;
    }

    private Node makeFileUploadGroup(Node prompt) {
        // TODO: Fix method to set up the board before the screen switches
        Button load = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(LOAD, getClass()), Load_Button_ID,
                (e) -> {
                    File f = chooseLoadFile();

                    myStackPane.getChildren().remove(prompt);
                    Node team = makeChooseTeamBox();
                    Node Room = makeRoomStringBox(f);
                    StackPane.setAlignment(team, Pos.CENTER);
                    StackPane.setAlignment(Room, Pos.CENTER_LEFT);
                    myStackPane.getChildren().addAll(team, Room);
                });
        load.setPrefWidth(150);
        load.setPrefHeight(50);
        return new Group(load);
    }

    private Node makeRoomStringBox(File f) {
        VBox vb = new VBox();
        vb.setSpacing(VBOXSPACING);

        Label hostID = new Label(BackendConnector.getFrontendWord(ROOM, getClass()));
        hostID.setFont(new Font(50.0));

        RoomName = new TextArea();
        RoomName.setId("RoomName"); //test
        vb.setAlignment(Pos.CENTER);
        RoomName.setPrefHeight(TEXTAREAHEIGHT);
        RoomName.setPrefWidth(TEXTAREAWIDTH);

        Button Confirm = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(CONFIRM, getClass()),
                Confirm_Button_ID, (e) -> {
            if (! RoomName.getText().equals("") && ! StartShowing) {
                RoomID = RoomName.getText();
                Node start = makeStartGroup(f);
                StackPane.setAlignment(start, Pos.BOTTOM_CENTER);
                myStackPane.getChildren().add(start);
                StartShowing = true;
            } else if (! RoomName.getText().equals("")){
                RoomID = RoomName.getText();
            }
                });
        vb.getChildren().addAll(hostID, RoomName, Confirm);
        return new Group(vb);
    }

    private Node makeChooseTeamBox() {
        VBox vb = new VBox();
        vb.setSpacing(VBOXSPACING);
        StackPane.setAlignment(vb, Pos.CENTER);
        Label prompt = new Label();
        prompt.setText(BackendConnector.getFrontendWord(COLORPROMPT, getClass()));
        prompt.setFont(new Font(50));
        colorchoice = new ChoiceBox<>();
        colorchoice.setId("colorchoice"); // for testing
        String white = BackendConnector.getFrontendWord(WHITE, getClass());
        String black = BackendConnector.getFrontendWord(BLACK, getClass());
        colorchoice.getItems().addAll(white, black);
        colorchoice.setValue(white);

        piececolors.put(white, 0);
        piececolors.put(black, 1);
        vb.getChildren().addAll(prompt, colorchoice);
        return new Group(vb);
    }

    private Node makeStartGroup(File f) {
        Button start = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(START), Start_Button_ID,
                (e) -> {
            Optional<ChessBoard> cbOp = getGameBackend().initalizeHostServerChessBoard(f, RoomID, piececolors.get(colorchoice.getValue()));
            if(cbOp.isPresent()) {
                getView(GameView.class).ifPresent((c) -> ((GameView)c).SetUpBoard(cbOp.get(), cbOp.get().getThisPlayer(), SERVER));
            } else {
                View.LOG.debug("Invalid JSON");
            }
            getView(GameView.class).ifPresent(this::changeScene);
        });
        start.setPrefWidth(150);
        start.setPrefHeight(50);
        return new Group(start);
    }
    /**
     * PURELY FOR TESTING.
     * @param path filepath to example file for testing
     */
    public void injectBoard(String path) {
        File f = new File(path);
        myStackPane.getChildren().remove(prompt);
        Node team = makeChooseTeamBox();
        Node Room = makeRoomStringBox(f);
        StackPane.setAlignment(team, Pos.CENTER);
        StackPane.setAlignment(Room, Pos.CENTER_LEFT);
        myStackPane.getChildren().addAll(team, Room);
    }
}
