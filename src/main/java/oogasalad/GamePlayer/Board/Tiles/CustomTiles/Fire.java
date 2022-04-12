package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Fire implements TileAction {

  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) {
    Set<Piece> pieceList = tile.getPieces().stream()
        .filter(Piece::burn)
        .collect(Collectors.toSet());

    tile.clearPieces();
    pieceList.forEach(tile::addPiece);

    return Set.of(tile);
  }
}
