package oogasalad.Frontend.LocalPlay;

import java.io.File;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.Frontend.Game.GameView;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;
import oogasalad.GamePlayer.Board.ChessBoard;

public class LocalGame extends View {

  private static final String TITLE = "Title";
  private static final String UPLOAD = "Upload";
  private static final String PROMPT_GAME = "PromptGame";
  private static final String PROMPT_AI_DIFFICULTY = "PromptAIDiff";
  private static final String PROMPT_ORDER = "PromptOrderSelection";
  private static final String FIRST = "First";
  private static final String SECOND = "Second";
  private static final String EASY_AI = "EasyAI";
  private static final String MEDIUM_AI = "MediumAI";
  private static final String HARD_AI = "HardAI";
  private static final String DONE = "Done";
  private static final String PLAYER_SELECTED = "PlayerSelected";
  private static final String DIFF_SELECTED = "DiffSelected";

  private final Integer TITLE_SIZE = 70;
  private final Integer PROMPT_SIZE = 30;
  private BorderPane root;

  private VBox singleplayerMenu;
  private VBox orderSelector;
  private HBox donePanel;

  private VBox playerHolder;
  private VBox diffHolder;

  private Label diffSelectionLabel;
  private Label playerSelectionLabel;

  private GridPane multiplayerButtons;
  private String playerSelection;
  private String diffSelection;

  public LocalGame(Stage stage) {
    super(stage);
  }

  @Override
  protected Node makeNode() {
    root = new BorderPane();
    return makeLayout();
  }

  private Node makeLayout() {

    Node exit = makeExitGroup();
    Node title = makeLabelGroup(BackendConnector.getFrontendWord(TITLE, getClass()), TITLE_SIZE);
    BorderPane.setAlignment(exit, Pos.TOP_LEFT);
    HBox top = new HBox(exit, title);

    root.setTop(top);
    root.setLeft(makeGamemodeSelection());
    root.setCenter(makeSingleplayerSelection());
    root.setRight(makePlayerSelection());
    root.setBottom(makeDonePanel());

    selectionHolder();

    return root;
  }

  private Node makeGamemodeSelection() {
    VBox box = new VBox();

    Node prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT_GAME, getClass()), PROMPT_SIZE);
    GridPane buttons = makeGameButtonHolder();
    box.getChildren().addAll(prompt, buttons);

    box.setAlignment(Pos.CENTER);
    buttons.setAlignment(Pos.CENTER);

    BorderPane.setAlignment(box, Pos.CENTER);

    return box;
  }

  private Node makeSingleplayerSelection() {
    singleplayerMenu = new VBox();

    GridPane buttons = makeSinglePlayerButtons();
    Node prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT_AI_DIFFICULTY, getClass()), PROMPT_SIZE);
    singleplayerMenu.setAlignment(Pos.CENTER);
    buttons.setAlignment(Pos.CENTER);
    singleplayerMenu.getChildren().addAll(prompt, buttons);

    BorderPane.setAlignment(singleplayerMenu, Pos.CENTER);
    singleplayerMenu.setVisible(false);
    return singleplayerMenu;
  }

  private Node makePlayerSelection() {
    orderSelector = new VBox();
    Node prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT_ORDER, getClass()), PROMPT_SIZE);
    GridPane buttons = makeButtonHolder(
        List.of(FIRST, SECOND),
        List.of("first", "second"),
        List.of(e -> makePlayerSelectionLabel("First Player"),
            e -> makePlayerSelectionLabel("Second Player"))
    );
    buttons.setAlignment(Pos.CENTER);
    orderSelector.getChildren().addAll(prompt, buttons);
    orderSelector.setVisible(false);
    BorderPane.setAlignment(orderSelector, Pos.CENTER);
    orderSelector.setPadding(new Insets(10));
    return orderSelector;
  }

  private Node makeLabelGroup(String s, Integer size){
    Label l = new Label(s);
    l.setFont(new Font(size));
    l.setTextAlignment(TextAlignment.CENTER);
    return new Group(l);
  }

  private Node makeExitGroup() {
    Button Exit = makeExitButton();
    return new Group(Exit);
  }

  private GridPane makeGameButtonHolder() {
    List<String> resourceNames = List.of("Multiplayer", "Singleplayer");
    List<String> ids = List.of("multiplayerButton", "singleplayerButton");
    List<EventHandler<ActionEvent>> actions = List.of(
        e -> toggleButtons(false, true),//getView(HostGame.class).ifPresent(this::changeScene),
        e -> toggleButtons(true, true) //e -> getView(SinglePlayerGame.class).ifPresent(this::changeScene)
    );

    return makeButtonHolder(resourceNames, ids, actions);
  }

  private GridPane makeSinglePlayerButtons() {

    List<String> resourceNames = List.of(EASY_AI, MEDIUM_AI, HARD_AI);
    List<String> ids = List.of("easyAI", "mediumAI", "hardAI");
    List<EventHandler<ActionEvent>> actions = List.of(
        e -> makeDiffSelectionLabel("Easy"),
        e -> makeDiffSelectionLabel("Medium"),
        e -> makeDiffSelectionLabel("Hard")
        );
    GridPane singleplayerButtons = makeButtonHolder(resourceNames, ids, actions);
    singleplayerButtons.setAlignment(Pos.CENTER);

    return singleplayerButtons;
  }

  private GridPane makeButtonHolder(List<String> resourceNames,
      List<String> ids,
      List<EventHandler<ActionEvent>> actions) {

    GridPane buttonHolder = new GridPane();

    for (int i = 0; i < resourceNames.size(); i++) {
      Button b = ButtonFactory.makeButton(ButtonType.TEXT, BackendConnector.getFrontendWord(resourceNames.get(i), getClass()), ids.get(i),
          actions.get(i));
      b.setPadding(new Insets(5));
      buttonHolder.add(b, 0, i);
    }
    buttonHolder.setVgap(50);

    return buttonHolder;
  }

  private void toggleButtons(boolean b1, boolean b2) {
    singleplayerMenu.setVisible(b1);
    orderSelector.setVisible(b2);
    donePanel.setVisible(true);
  }

  private Node makeDonePanel() {
    donePanel = new HBox();
    GridPane buttons = makeButtonHolder(List.of(UPLOAD, DONE), List.of("upload", "done"),
        List.of((e) -> {
              File f = chooseLoadFile();
              Optional<ChessBoard> cb = getGameBackend().initalizeChessBoard(f);
              if (cb.isPresent()) {
                getView(GameView.class).ifPresent((c) -> ((GameView)c).SetUpBoard(cb.get(), 0)); // hardcoded 0 here for white
              } else {
                View.LOG.debug("INVALID JSON");
              }
            },
            e -> {
              try {
                String player = ((Label) diffHolder.getChildren().get(1)).getText();
                String diff = ((Label) playerHolder.getChildren().get(1)).getText();
                System.out.printf("%s, %s", player, diff);

              } catch (IndexOutOfBoundsException error) {
                View.LOG.warn(error.getMessage());
                System.out.println("ERROR");
              }
            }
        ));

    donePanel.getChildren().addAll(buttons);
    donePanel.setPadding(new Insets(25));
    donePanel.setSpacing(25);
    return donePanel;
  }

  private void makeDiffSelectionLabel(String s) {
    try {
      diffHolder.getChildren().remove(diffSelectionLabel);

    } catch (Exception e) {
      View.LOG.warn(e.getMessage());
    }
    diffSelectionLabel = new Label(s);
    diffHolder.getChildren().add(diffSelectionLabel);
  }

  private void makePlayerSelectionLabel(String s) {
    try {
      playerHolder.getChildren().remove(playerSelectionLabel);
    } catch (Exception e) {
      View.LOG.warn(e.getMessage());
    }
    playerSelectionLabel = new Label(s);
    playerHolder.getChildren().add(playerSelectionLabel);
  }

  private void selectionHolder() {
    playerHolder = new VBox();
    diffHolder = new VBox();

    Node playerLabel = new Label(BackendConnector.getFrontendWord(PLAYER_SELECTED, getClass()));
    Node diffLabel = new Label(BackendConnector.getFrontendWord(DIFF_SELECTED, getClass()));

    playerHolder.getChildren().add(playerLabel);
    diffHolder.getChildren().add(diffLabel);

    donePanel.getChildren().addAll(playerHolder, diffHolder);
  }

}
