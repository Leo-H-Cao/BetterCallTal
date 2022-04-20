package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Collections;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.Movement.Coordinate;

/***
 * Teleports piece to given square
 *
 * @author Vincent Chen
 */
public class Teleport implements TileAction {

  private Coordinate teleportLocation;

  public Teleport
  /***
   * Teleports piece on the tile to the location determined by file read if the teleport square
   * is open
   *
   * @param tile that was activated
   * @param board to teleport on
   * @return empty set if no teleportation, parameter tile and teleport location if teleported
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    if(tile.getPiece().isPresent() && board.getTile(teleportLocation).getPiece().isEmpty()) {
      tile.getPiece().get().updateCoordinates(board.getTile(teleportLocation), board);
      return Set.of(tile, board.getTile(teleportLocation));
    }
    return Collections.emptySet();
  }
}
