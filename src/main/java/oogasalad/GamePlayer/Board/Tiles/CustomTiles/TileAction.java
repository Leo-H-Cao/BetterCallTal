package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;

public interface TileAction {

  /***
   * @return updated chess tiles based on execution of action
   */
  Set<ChessTile> executeAction(ChessTile tile, ChessBoard board);

}
