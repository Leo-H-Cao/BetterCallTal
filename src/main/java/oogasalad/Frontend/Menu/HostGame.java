package oogasalad.Frontend.Menu;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.ViewManager;
import oogasalad.Frontend.util.View;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;

import java.io.File;


public class HostGame extends View {

    private static final String TITLE = "Title";
    private static final String Load_Button_ID = "Upload";
    private static final String LOAD = "Load";
    private static final String PROMPT = "Prompt";
    private final Integer TITLE_SIZE = 64;
    private final Integer PROMPT_SIZE = 40;

    public HostGame(ViewManager mainview) {
        super(mainview);
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
        Node load = makeFileUploadGroup();
        Node start = makeStartGroup();

        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(prompt, Pos.CENTER);
        StackPane.setAlignment(load, Pos.CENTER_RIGHT);
        StackPane.setAlignment(start, Pos.CENTER_LEFT);

        sp.getChildren().addAll(exit, title, prompt, load, start);
        return sp;
    }

    private Node makeStartGroup() {
        Button start = ButtonFactory.makeButton(ButtonType.TEXT, ViewManager.getLanguage().getString("Start"), "start",
                (e) -> getViewManager().getViews().stream().filter((c) -> c.getClass() == GameView.class).forEach((c) ->
                        getViewManager().changeScene(c)));
        start.setPrefWidth(150);
        start.setPrefHeight(50);
        return new Group(start);
    }

    private Node makeExitGroup() {
        Button Exit = makeExitButton();
        return new Group(Exit);
    }

    private Node makeFileUploadGroup() {
        Button load = ButtonFactory.makeButton(ButtonType.TEXT, getLanguageResource(LOAD), Load_Button_ID,
                (e) -> {
                    File f = getViewManager().chooseLoadFile();
                    getViewManager().getMyGameBackend().initalizeChessBoard(f);
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
