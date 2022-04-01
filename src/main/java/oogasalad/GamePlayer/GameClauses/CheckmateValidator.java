package oogasalad.GamePlayer.GameClauses;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/**
 * This class
 * @author Jose Santillan
 */
public class CheckmateValidator {


  /**
   * Public method that returns whether the board has reached
   * checkmate
   * @return Whether the board is in checkmate or not
   */
  public boolean isInMate(ChessBoard board, int id) {

    Set<Piece> friendlyPieces = friendlyPieces(board, id);
    List<Piece> targetPieces = board.targetPiece(id);

    boolean cantMove = targetPieces.stream()
        .noneMatch(piece -> piece.getMoves().size() == 0);
    //boolean cantBlock = friendlyPieces.stream();

    return cantMove;// && cantBlock;
  }

  private Set<Piece> friendlyPieces(ChessBoard board, int id) {
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(id))
        .collect(Collectors.toSet());
  }

}
