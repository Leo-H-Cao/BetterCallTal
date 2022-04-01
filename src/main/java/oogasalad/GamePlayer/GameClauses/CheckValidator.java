package oogasalad.GamePlayer.GameClauses;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.MovementModifier;

/**
 * @author Jose Santillan
 * @author Jed Yang
 */
public class CheckValidator implements MovementModifier {

  /**
   * This method checks if the target piece of the current team is in check
   * @param board The current board being played
   * @param id The id of the pieces that are being attacked
   * @return whether the target-piece is under attack
   */
  public boolean isInCheck(ChessBoard board, int id) throws OutsideOfBoardException {

    List<Piece> targetPieces = board.targetPiece(id);
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .anyMatch(piece -> piece.canCapture(targetPieces));

//    boolean check;
//    for (ChessTile tile : board) {
//      check = tile.getPieces()
//          .stream()
//          .filter(piece -> !piece.checkTeam(id))
//          .anyMatch(piece -> piece.canCapture(targetPieces));
//
//    }
//    return false;
  }

  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    return null;
  }
}
