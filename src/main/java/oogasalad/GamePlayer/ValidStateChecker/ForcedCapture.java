package oogasalad.GamePlayer.ValidStateChecker;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Capture is forced if applicable
 *
 * @author Vincent Chen
 */
public class ForcedCapture implements ValidStateChecker {

  private static final Logger LOG = LogManager.getLogger(ForcedCapture.class);

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public ForcedCapture() {
    super();
  }

  /***
   * If a capture exists on the board, only other captures are valid.
   *
   * @param board to check
   * @param piece to check
   * @param move to check
   * @return true if no captures or if the move is a capture, false otherwise
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece, ChessTile move) throws EngineException {
    if (piece.getCaptures().stream().anyMatch(m ->
        m.getCaptures(piece, board).contains(move))) {
      return true;
    }

    LOG.debug(String.format("Capture exists: %s", captureExists(piece.getTeam(), board)));
    return !captureExists(piece.getTeam(), board);
  }

  /***
   * Returns if the given team has a capture on the board
   *
   * @param team that is moving
   * @param board to move on
   * @return read above
   */
  public boolean captureExists(int team, ChessBoard board) {
    return board.getPieces().stream().filter(p -> p.getTeam() == team).anyMatch(p ->
        p.getCaptures().stream().anyMatch(m -> !m.getCaptures(p, board).isEmpty()));
  }
}
