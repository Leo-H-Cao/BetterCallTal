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

import java.util.Optional;

public class JoinGame extends View {
    private StackPane myStackPane;
    private static final String TITLE = "Title";
    private static final String START= "Start";
    private final Integer TITLE_SIZE = 64;
    private TextArea RoomName;
    private String ROOMID;
    private static final Double TEXTAREAWIDTH = 100.0;
    private static final Double TEXTAREAHEIGHT = 20.0;
    private static final Double VBOXSPACING = 5.0;
    private static final String Confirm_Button_ID = "JoinConfirm";
    private static final String ROOM = "Room";
    private static final String CONFIRM = "Confirm";
    private static final String Start_Button_ID = "JoinStart";
    private boolean StartShowing;
    private ChoiceBox<String> packages;

    public JoinGame(Stage stage) {super(stage);}

    @Override
    protected Node makeNode() {
        return MakeStackPane();
    }

    private StackPane MakeStackPane() {
        StartShowing = false;
        myStackPane = new StackPane();
        myStackPane.setPrefSize(myScreenSize.getWidth(), myScreenSize.getHeight());
        Node exit = makeExitGroup();
        Node title = makeLabelGroup(BackendConnector.getFrontendWord(TITLE, getClass()), TITLE_SIZE);
        Node input = makeRoomStringBox();

        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(input, Pos.CENTER);


        myStackPane.getChildren().addAll(exit, title, input);
        return myStackPane;
    }

    private Node makeRoomStringBox() {
        VBox vb = new VBox();
        packages = makePackageSelectGroup();

        vb.setSpacing(VBOXSPACING);

        Label hostID = new Label(BackendConnector.getFrontendWord(ROOM, getClass()));
        hostID.setFont(new Font(50.0));

        RoomName = new TextArea();
        RoomName.setId("RoomName");
        vb.setAlignment(Pos.CENTER);
        RoomName.setPrefHeight(TEXTAREAHEIGHT);
        RoomName.setPrefWidth(TEXTAREAWIDTH);

        Button Confirm = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(CONFIRM, getClass()),
                Confirm_Button_ID, (e) -> {
            if (! RoomName.getText().equals("") && ! StartShowing) {
                ROOMID = RoomName.getText();
                Node start = makeStartGroup();
                StackPane.setAlignment(start, Pos.BOTTOM_CENTER);
                myStackPane.getChildren().add(start);
                StartShowing = true;
            } else if (! RoomName.getText().equals("")){
                ROOMID = RoomName.getText();
            }
                });
        vb.getChildren().addAll(hostID, RoomName, packages, Confirm);
        return new Group(vb);
    }

    private Node makeStartGroup() {
        Button start = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(START), Start_Button_ID,
                (e) -> {
                    Optional<ChessBoard> cb = getGameBackend().initalizeJoinServerChessBoard(ROOMID);
                    if(cb.isPresent()) {
                        getView(GameView.class).ifPresent((c) -> ((GameView)c).SetUpBoard(cb.get(), cb.get().getThisPlayer(), SERVER, packages.getValue()));
                    } else {
                        View.LOG.debug("Invalid JSON");
                    }
            getView(GameView.class).ifPresent(this::changeScene);
                });
        start.setPrefWidth(150);
        start.setPrefHeight(50);
        return new Group(start);
    }

}
