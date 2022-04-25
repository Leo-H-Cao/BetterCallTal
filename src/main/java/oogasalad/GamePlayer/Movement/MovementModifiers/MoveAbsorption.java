package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.util.Collections;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Movement;

/***
 * Movement modifier that allows a piece to absorb the moves of the piece that was just taken
 *
 * @author Vincent Chen
 */
public class MoveAbsorption implements MovementModifier {

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public MoveAbsorption() {
    super();
  }

  /***
   * Adds taken piece's moves to the current piece
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return tile the piece is on
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    try {
      Piece justTaken = board.getHistory().get(board.getHistory().size() - 1).board().getTile(
          piece.getCoordinates()).getPiece().orElse(
          board.findTakenPiece(piece.getTeam()));
      piece.addNewMovements(Movement.invertMovements(justTaken.getMoves()),
          Movement.invertMovements(justTaken.getCaptures()));
      return Set.of(board.getTile(piece.getCoordinates()));
    } catch (OutsideOfBoardException e) {return Collections.emptySet();}
  }
}
