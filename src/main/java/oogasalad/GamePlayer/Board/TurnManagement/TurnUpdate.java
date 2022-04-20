package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Set;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

/***
 * Info relevant to the updated state of the board after a turn
 *
 * @author Vincent Chen
 */
public record TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer) {

  public TurnUpdate(Set<ChessTile> updatedSquares, int nextPlayer) {
    this.updatedSquares = updatedSquares;
    this.nextPlayer = nextPlayer;
  }
}
