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

/***
 * Custom move that snakes around the board based on given directions
 */
public class Snake implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(Snake.class);

  private Coordinate primaryMoveDirection;
  private Coordinate secondaryMoveDirection;
  private Coordinate primaryMultiplier;

  /***
   * @return original tile and tile moved to
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return null;
  }

  /**
   * @throws InvalidMoveException because no capture possible
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException {
    LOG.warn("Snake does not support captures");
    throw new InvalidMoveException("Snake does not support captures");
  }

  /***
   * @return empty set, not applicable
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /***
   * @return all moves, including ones that snake around the board
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return null;
  }

  /***
   * @return empty list, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
