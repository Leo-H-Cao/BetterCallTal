package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;

/***
 * Interface for custom tile functionality
 *
 * @author Vincent Chen
 */
public interface TileAction {

  /***
   * @return updated chess tiles based on execution of action
   */
  Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException;

}
