package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

/***
 * Tile action that clears pieces that land on a square
 *
 * @author Vincent Chen
 */
public class BlackHoleAction implements TileAction {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public BlackHoleAction(){
    super();
  }

  /***
   * Clears all pieces on tile
   *
   * @return this tile
   */
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    tile.clearPieces();
    return Set.of(tile);
  }
}
