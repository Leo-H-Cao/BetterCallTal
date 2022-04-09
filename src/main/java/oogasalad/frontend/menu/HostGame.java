package oogasalad.frontend.menu;

import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.frontend.game.GameBackend;
import oogasalad.frontend.game.GameView;
import oogasalad.frontend.util.View;
import oogasalad.frontend.util.ButtonFactory;
import oogasalad.frontend.util.ButtonType;
import oogasalad.GamePlayer.Board.ChessBoard;
import java.io.File;


public class HostGame extends View {

    private static final String TITLE = "Title";
    private static final String Load_Button_ID = "Upload";
    private static final String LOAD = "Load";
    private static final String PROMPT = "Prompt";
    private final Integer TITLE_SIZE = 64;
    private final Integer PROMPT_SIZE = 40;

    public HostGame(Stage stage) {
        super(stage);
    }

    @Override
    protected Node makeNode() {
        return MakeStackPane();
    }

    private StackPane MakeStackPane() {
        StackPane sp = new StackPane();
        sp.setPrefSize(myScreenSize.getWidth(), myScreenSize.getHeight());
        Node exit = makeExitGroup();
        Node title = makeLabelGroup(getLanguageResource(TITLE), TITLE_SIZE);
        Node prompt = makeLabelGroup(getLanguageResource(PROMPT), PROMPT_SIZE);
        Node load = makeFileUploadGroup(sp);

        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(prompt, Pos.CENTER);
        StackPane.setAlignment(load, Pos.CENTER_RIGHT);

        sp.getChildren().addAll(exit, title, prompt, load);
        return sp;
    }

    private Node makeStartGroup() {
        Button start = ButtonFactory.makeButton(ButtonType.TEXT, getLangBundle().getString("Start"), "start",
                (e) -> getView(GameView.class).ifPresent(this::changeScene));
        start.setPrefWidth(150);
        start.setPrefHeight(50);
        return new Group(start);
    }

    private Node makeExitGroup() {
        Button Exit = makeExitButton();
        return new Group(Exit);
    }

    private Node makeFileUploadGroup(StackPane sp) {
        // TODO: Fix method to set up the board before the screen switches
        Button load = ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource(LOAD), Load_Button_ID,
                (e) -> {
                    File f = chooseLoadFile();
                    getBackend(GameBackend.class).ifPresent((a) -> {
                        Optional<ChessBoard> cbOp = a.initalizeChessBoard(f);
                        if(cbOp.isPresent()) {
                            getView(GameView.class).ifPresent((c) -> ((GameView)c).SetUpBoard(cbOp.get(), 0));
                            // Make the start button
                            Node start = makeStartGroup();
                            StackPane.setAlignment(start, Pos.CENTER_LEFT);
                            sp.getChildren().add(start);
                        }
                    });
                });
        load.setPrefWidth(150);
        load.setPrefHeight(50);
        return new Group(load);
    }

    private Node makeLabelGroup(String s, Integer size){
        Label l = new Label(s);
        l.setFont(new Font(size));
        l.setTextAlignment(TextAlignment.CENTER);
        return new Group(l);
    }
}
