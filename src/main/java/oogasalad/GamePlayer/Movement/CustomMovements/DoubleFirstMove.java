package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Movement.CustomMovements.Castling.NO_MOVEMENT_HISTORY_LENGTH;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoubleFirstMove implements MovementInterface {

  private static final int MULT = 2;

  private static final Logger LOG = LogManager.getLogger(DoubleFirstMove.class);

  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
      LOG.warn("Illegal double move attempted");
      throw new InvalidMoveException(finalSquare.toString());
    }
    ChessTile oldTile = board.getTile(piece.getCoordinates());
    piece.updateCoordinates(board.getTile(finalSquare), board);
    return Set.of(oldTile, board.getTile(piece.getCoordinates()));
  }

  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("Double first move does not support captures");
    throw new InvalidMoveException("Double first move does not support captures");
  }

  /***
   * @return nothing, this custom move does not apply to captures
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Set.of();
  }

  /***
   * @return double of move if first move
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    if(piece.getHistory().size() != NO_MOVEMENT_HISTORY_LENGTH) return Set.of();
    LOG.debug("Getting double moves: " + piece.getName());
    List<MovementInterface> newMovements = new ArrayList<>();
    piece.getRelativeMoveCoords().stream().filter((c) -> {
      try {
        LOG.debug(String.format("Relative coordinate: (%d, %d)", c.getRow(), c.getCol()));
        LOG.debug("Pieces in way: " +  board.getTile(Coordinate.add(c, piece.getCoordinates())).getPieces());
        return board.getTile(Coordinate.add(c, piece.getCoordinates())).getPieces().isEmpty();
      } catch (OutsideOfBoardException e) {
        LOG.debug("Out of bounds");
        return false;
      }
    }).forEach((c) -> {
      LOG.debug("Double move coord: " + Coordinate.of(c.getRow()*MULT, c.getCol()*MULT));
      newMovements.add(new Movement(Coordinate.of(c.getRow()*MULT, c.getCol()*MULT), false));
    });
    return newMovements.stream().flatMap((m) -> m.getMoves(piece, board).stream()).collect(Collectors.toSet());
  }

  /***
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return List.of();
  }
}
