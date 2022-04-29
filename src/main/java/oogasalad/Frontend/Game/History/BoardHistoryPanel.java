package oogasalad.Frontend.Game.History;

import java.util.Collection;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;

public class BoardHistoryPanel {

  private List<TurnUpdate> updates;
  private ScrollPane pane;
  private VBox container;
  private VBox white;
  private VBox black;
  private VBox move;
  private int idx;

  public BoardHistoryPanel() {
    setupNodes();
    idx = 0;
  }

  private void setupNodes() {
    container = new VBox();
    move = new VBox();
    white = new VBox();
    black = new VBox();
    HBox temp = new HBox();
    temp.setSpacing(25);
    temp.getChildren().addAll(move, white, black);
    container.getChildren().addAll(new Label("HistoryPanel"), temp);

  }


  public void add(TurnUpdate tu) {
    if (idx % 2 == 0) {
      move.getChildren().add(new Label(String.format("%d.", idx / 2)));
      white.getChildren().add(makeLabel(tu));
    } else {
      black.getChildren().add(makeLabel(tu));
    }
    idx += 1;
  }

  private Label makeLabel(TurnUpdate tu) {
//    List<ChessTile> tiles = tu.updatedSquares().stream()
//        .filter(tile -> tile.getPieces().size() != 0).toList();
//    Optional<Piece> piece = tiles.get(0).getPiece();
//    String name =  piece.isPresent() ? piece.get().getName() : "Empty";
//    return new Label(String.format("%s, %s", name, tiles.get(0).getCoordinates()));
    return new Label(tu.notation());
  }

  public void add(Collection<TurnUpdate> tu) {
    tu.forEach(this::add);
  }

  public Node makeNode() {
    pane = new ScrollPane();
    pane.setPrefViewportWidth(225);
    pane.setPrefViewportHeight(300);
    pane.setContent(container);
    return pane;
  }
}
