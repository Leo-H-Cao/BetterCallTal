package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

public class Fire implements TileAction {

  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {

    return Set.of(tile);
  }
}
