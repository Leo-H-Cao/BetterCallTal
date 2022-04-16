package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Collections;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;

/**
 * Promotion but it just adds the reverse of possible moves
 */
public class PromotionReverse implements TileAction{

  /**
   * Adds the reverse of all moves and captures to the given piece
   *
   * @return tile parameter, only tile that is updated
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    return Collections.emptySet();
  }
}
