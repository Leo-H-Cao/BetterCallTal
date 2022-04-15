package oogasalad.Frontend.Menu;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.Board.ChessBoard;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;


public class HostGame extends GameView {
    private StackPane myStackPane;
    private static final String TITLE = "Title";
    private static final String Load_Button_ID = "Upload";
    private static final String Confirm_Button_ID = "Confirm";
    private static final String LOAD = "Load";
    private static final String PROMPT = "Prompt";
    private final Integer TITLE_SIZE = 64;
    private final Integer PROMPT_SIZE = 40;
    private static final String WHITE = "White";
    private static final String BLACK = "Black";
    private static final String ROOM = "Room";
    private Map<String, Integer> piececolors;
    private Integer DEFAULT_COLOR = 1;
    private Integer myTeam;
    private static final String COLORPROMPT = "COLORPROMPT";
    private static final String SELECTEDCOLOR = "SELECTEDCOLOR";
    private static final Double VBOXSPACING = 5.0;
    private ChoiceBox<String> colorchoice;
    private TextArea RoomName;
    private static final Double TEXTAREAWIDTH = 100.0;
    private static final Double TEXTAREAHEIGHT = 20.0;


    public HostGame(Stage stage) {
        super(stage);
    }

    @Override
    protected Node makeNode() {
        return MakeStackPane();
    }

    private StackPane MakeStackPane() {
        piececolors = new HashMap<>();
        myStackPane = new StackPane();
        myStackPane.setPrefSize(myScreenSize.getWidth(), myScreenSize.getHeight());
        Node exit = makeExitGroup();
        Node title = makeLabelGroup(BackendConnector.getFrontendWord(TITLE, getClass()), TITLE_SIZE);
        Node prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT, getClass()), PROMPT_SIZE);
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
        vb.setAlignment(Pos.CENTER);
        RoomName.setPrefHeight(TEXTAREAHEIGHT);
        RoomName.setPrefWidth(TEXTAREAWIDTH);

        Button Confirm = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(Confirm_Button_ID, getClass()),
                Confirm_Button_ID, (e) -> {
            if (! RoomName.getText().equals("")) {
                Node start = makeStartGroup(f);
                StackPane.setAlignment(start, Pos.BOTTOM_CENTER);
                myStackPane.getChildren().add(start);
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
        myTeam = DEFAULT_COLOR;
        colorchoice = new ChoiceBox<>();
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
        Button start = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord("Start"), "start",
                (e) -> {
            Optional<ChessBoard> cbOp = getGameBackend().initalizeChessBoard(f);
            if(cbOp.isPresent()) {
                getView(GameView.class).ifPresent((c) -> ((GameView)c).SetUpBoard(cbOp.get(), piececolors.get(colorchoice.getValue())));
            } else {
                View.LOG.debug("Invalid JSON");
            }
            getView(GameView.class).ifPresent(this::changeScene);
        });
        start.setPrefWidth(150);
        start.setPrefHeight(50);
        return new Group(start);
    }

    private Node makeExitGroup() {
        Button Exit = makeExitButton();
        return new Group(Exit);
    }

    private Node makeLabelGroup(String s, Integer size){
        Label l = new Label(s);
        l.setFont(new Font(size));
        l.setTextAlignment(TextAlignment.CENTER);
        return new Group(l);
    }
}
