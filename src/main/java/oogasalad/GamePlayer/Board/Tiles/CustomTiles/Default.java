package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.HashSet;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Default implements TileAction{

  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    tile.getPieces()
        .forEach(Piece::clearActions);

    Set<ChessTile> set = new HashSet<>();
    set.add(tile);
    return set;
  }
}
