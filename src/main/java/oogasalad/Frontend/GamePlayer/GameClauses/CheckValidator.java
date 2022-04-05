package oogasalad.Frontend.GamePlayer.GameClauses;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.Frontend.GamePlayer.GamePiece.Piece;
import oogasalad.Frontend.GamePlayer.Movement.MovementModifier;


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
  public static boolean isInCheck(ChessBoard board, int id) throws OutsideOfBoardException {
    List<Piece> targetPieces = board.targetPiece(id);

    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> !piece.checkTeam(id))
        .anyMatch(piece -> piece.validCapture(targetPieces.stream()
            .map(Piece::getCoordinates)
            .collect(Collectors.toList())));

  }

  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessTile finalTile, ChessBoard board) {
    return null;
  }
}
