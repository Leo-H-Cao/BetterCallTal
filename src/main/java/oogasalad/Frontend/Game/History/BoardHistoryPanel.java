package oogasalad.Frontend.Game.History;

import javafx.scene.Node;
import oogasalad.GamePlayer.Board.ChessBoard;

public class BoardHistoryPanel extends Node implements History{


  @Override
  public ChessBoard getBoard() {
    return null;
  }

  @Override
  public void add(ChessBoard board) {

  }

  @Override
  public History next() {
    return this;
  }

  @Override
  public History previous() {
    return this;
  }
}
