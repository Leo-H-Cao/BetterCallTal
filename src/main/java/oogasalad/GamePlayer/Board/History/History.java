package oogasalad.GamePlayer.Board.History;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public record History(ChessBoard board, Set<Piece> movedPieces, Set<ChessTile> updatedTiles) {

}
