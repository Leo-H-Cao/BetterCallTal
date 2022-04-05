package oogasalad.Frontend.GamePlayer.GameClauses;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;

/**
 * @author Jose Santillan
 */
public class StalemateValidator {

  public static boolean isStaleMate(ChessBoard board, int id){
    Set<Piece> friendlyPieces = friendlyPieces(board, id);

    return friendlyPieces.stream()
        .allMatch(p -> p.getMoves().isEmpty());
  }


  private static Set<Piece> friendlyPieces(ChessBoard board, int id) {
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(id))
        .collect(Collectors.toSet());
  }
}
