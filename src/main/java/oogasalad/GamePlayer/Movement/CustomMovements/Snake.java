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
  private boolean enactedSecondaryMove;

  /***
   * @return original tile and tile moved to
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
      LOG.warn("Illegal snake move attempted");
      throw new InvalidMoveException(finalSquare.toString());
    }

    if(enactedSecondaryMove) {
      primaryMoveDirection = Coordinate.multiply(primaryMoveDirection, primaryMultiplier);
    }

    ChessTile oldTile = board.getTile(piece.getCoordinates());
    piece.updateCoordinates(board.getTile(finalSquare), board);
    return Set.of(oldTile, board.getTile(piece.getCoordinates()));
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
    Coordinate currentCoordinate = piece.getCoordinates();
    Coordinate newCoordinate = Coordinate.add(currentCoordinate, primaryMoveDirection);
    enactedSecondaryMove = false;

    if(!board.inBounds(newCoordinate)) {
      Coordinate delta = Coordinate.of(
          findDelta(0, board.getBoardHeight(), newCoordinate.getRow()),
          findDelta(0, board.getBoardLength(), newCoordinate.getCol()));
      newCoordinate = Coordinate.add(Coordinate.add(newCoordinate, secondaryMoveDirection), delta);
      enactedSecondaryMove = true;
    }
    try {
      return Set.of(board.getTile(newCoordinate));
    } catch (OutsideOfBoardException e) {
      return Collections.emptySet();
    }
  }

  /***
   * Given a range min to max, find the difference needed to scale target into [min, max)
   *
   * @return above
   */
  private int findDelta(int min, int max, int target) {
    return target < min ? min - target : (target >= max ? max - target - 1 : 0);
  }

  /***
   * @return empty list, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
