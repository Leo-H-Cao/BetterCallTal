package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.BLOCK_COL;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates a custom movement that allows pieces to leave the bank
 */
public class BankLeaver implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(BankLeaver.class);


  /**
   * @return updated squares when a piece leaves the bank
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
      LOG.warn("Illegal bank leaving move attempted");
      throw new InvalidMoveException(finalSquare.toString());
    }
    Set<ChessTile> updatedSquares = new HashSet<>(
        Arrays.asList(board.getTile(piece.getCoordinates()), board.getTile(finalSquare)));
    updatedSquares.addAll(piece.updateCoordinates(board.getTile(finalSquare), board));
    return updatedSquares;
  }

  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("Bank leaving does not support captures");
    throw new InvalidMoveException("Bank leaving does not support captures");
  }

  /***
   * @return nothing, not applicable
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /***
   * @return all open squares on board to the left of the bank, if piece is in the bank
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    if(piece.getCoordinates().getCol() < BLOCK_COL) return Set.of();
    return board.stream().flatMap(Collection::stream).toList().stream().filter(t ->
        t.getCoordinates().getCol() < BLOCK_COL && t.getPieces().isEmpty())
        .collect(Collectors.toSet());
  }

  /***
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
