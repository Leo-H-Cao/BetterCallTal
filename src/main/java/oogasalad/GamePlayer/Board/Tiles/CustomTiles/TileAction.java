package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;

/***
 * Interface for custom tile functionality
 *
 * @author Vincent Chen, Jed Yang
 */
public interface TileAction {

  /***
   * @return updated chess tiles based on execution of action
   */
  Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException;

}
