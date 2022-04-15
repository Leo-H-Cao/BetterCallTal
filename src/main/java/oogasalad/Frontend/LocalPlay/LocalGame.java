package oogasalad.Frontend.LocalPlay;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import oogasalad.Frontend.Menu.HostGame;
import oogasalad.Frontend.util.BackendConnector;
import oogasalad.Frontend.util.ButtonFactory;
import oogasalad.Frontend.util.ButtonType;
import oogasalad.Frontend.util.View;

public class LocalGame extends View {

  private static final String TITLE = "Title";
  private static final String Load_Button_ID = "Upload";
  private static final String LOAD = "Load";
  private static final String PROMPT_GAME = "PromptGame";
  private static final String PROMPT_AI_DIFFICULTY = "PromptAIDiff";
  private static final String PROMPT_ORDER = "PromptOrderSelection";
  private static final String EASY_AI = "EasyAI";
  private static final String MEDIUM_AI = "MediumAI";
  private static final String HARD_AI = "HardAI";

  private final Integer TITLE_SIZE = 10;
  private final Integer PROMPT_SIZE = 30;
  private BorderPane root;

  private GridPane singleplayerButtons;
  private GridPane multiplayerButtons;

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
    BorderPane.setAlignment(exit, Pos.TOP_LEFT);
    root.setTop(exit);

    root.setLeft(makeGamemodeSelection());
    root.setCenter(makeSingleplayerSelection());

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
    box.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    return box;
  }

  private Node makeSingleplayerSelection() {
    VBox sp = new VBox();

    GridPane buttons = makeSinglePlayerButtons();
    Node prompt = makeLabelGroup(BackendConnector.getFrontendWord(PROMPT_AI_DIFFICULTY, getClass()), PROMPT_SIZE);
    sp.setAlignment(Pos.CENTER);
    buttons.setAlignment(Pos.CENTER);
    sp.getChildren().addAll(prompt, buttons);
    sp.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    BorderPane.setAlignment(sp, Pos.CENTER);
    return sp;
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
        e -> getView(HostGame.class).ifPresent(this::changeScene),
        e -> toggleButtons(true, false) //e -> getView(SinglePlayerGame.class).ifPresent(this::changeScene)
    );

    return makeButtonHolder(resourceNames, ids, actions);
  }

  private GridPane makeSinglePlayerButtons() {

    List<String> resourceNames = List.of(EASY_AI, MEDIUM_AI, HARD_AI);
    List<String> ids = List.of("easyAI", "mediumAI", "hardAI");
    List<EventHandler<ActionEvent>> actions = List.of(
        e -> System.out.println("Easy Mode selected"),
        e -> System.out.println("Medium Mode selected"),
        e -> System.out.println("Hard Mode selected")
        );
    singleplayerButtons = makeButtonHolder(resourceNames, ids, actions);
    singleplayerButtons.setVisible(false);
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
    singleplayerButtons.setVisible(b1);
    multiplayerButtons.setVisible(b2);
  }

}
