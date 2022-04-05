package oogasalad.Frontend.GamePlayer.Board;

import java.util.Set;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;

public record TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer) {

  public TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer) {
    this.updatedSquares = updatedSquares;
    this.nextPlayer = nextPlayer;
  }
}
