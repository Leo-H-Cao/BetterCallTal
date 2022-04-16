package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckersCapture implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(CheckersCapture.class);

  /**
   * @return exception because no capture possible
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("Checkers capture does not support moving");
    throw new InvalidMoveException("Checkers capture does not support moving");
  }

  /**
   * Captures piece by jumping over it, multiple jumps possible if applicable
   *
   * @return set of updated chess tiles
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return Collections.emptySet();
  }

  /**
   * @return possible captures by jumping over pieces
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /**
   * @return empty set because no moves possible
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /**
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
