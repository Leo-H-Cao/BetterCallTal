package oogasalad.GamePlayer.Movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Basic piece movement given relative coordinates
 *
 * @author Vincent Chen
 */
public class BasicCapture extends BasicMovementInterface {

  private static final Logger LOG = LogManager.getLogger(BasicCapture.class);

  /***
   * Captures piece on captureSquare
   *
   * @param piece to move
   * @param captureSquare end square
   * @param board to move on
   * @return set of updated tiles
   * @throws InvalidMoveException if the piece cannot move to the given square
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {

    ChessTile captureTile = convertCordToTile(captureSquare, board);
    if(getMoves(piece, board).contains(captureTile)) {
      Set<ChessTile> updatedSquares = new HashSet<>(Set.of(board.getTile(piece.getCoordinates()), board.getTile(captureSquare)));
      captureTile.clearPieces();
      updatedSquares.addAll(piece.updateCoordinates(board.getTile(captureSquare), board));
      return updatedSquares;
    }
    LOG.warn(String.format("Invalid move made: (%d, %d)", captureSquare.getRow(), captureSquare.getCol()));
    throw new InvalidMoveException(piece + ": " + captureSquare);
  }

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, CAPTURE_KEY);
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, MOVE_KEY);
  }
}
