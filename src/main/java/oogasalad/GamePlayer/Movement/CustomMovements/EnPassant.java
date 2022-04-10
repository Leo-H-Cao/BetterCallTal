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

public class EnPassant implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(EnPassant.class);

  /***
   * Creates a movement object that represents en passant: if a piece of the same name has moved
   * once and is on the 5th rank relative to the piece doing en passant, then the piece can
   * capture the other piece by moving diagonally one square behind it
   */
  public EnPassant() {}

  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("En passant does not support move, only capture");
    throw new InvalidMoveException("En passant does not support move, only capture");
  }

  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return null;
  }

  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return null;
  }

  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  @Override
  public List<Coordinate> getRelativeCoords() {
    return null;
  }
}
