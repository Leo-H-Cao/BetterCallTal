package oogasalad.GamePlayer.Movement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Movement {

  private List<Coordinate> possibleMoves;
  private boolean infinite;

  /***
   * Creates a class representing a basic piece movement
   */
  public Movement(List<Coordinate> possibleMoves, boolean infinite) {
    this.possibleMoves = possibleMoves;
    this.infinite = infinite;
  }

  /***
   * Moves the piece on fromSquare to finalSquare
   *
   * @param piece to move
   * @param finalSquare end square
   * @param board to move on
   * @return set of updated tiles
   */
  public Set<ChessTile> movePiece(Piece piece, ChessTile finalSquare, ChessBoard board) {

  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @param isCapture to decide whether to return a
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board, boolean isCapture) {
    Set<ChessTile> moves = new HashSet<>();
    Coordinate baseCoordinates = piece.getCoordinates();
    for (Coordinate delta : possibleMoves) {
      if(infinite) {
        moves.addAll(getMoveBeam(baseCoordinates, delta, board));
      } else{
        moves.add(board.getTile(Coordinate.add(baseCoordinates, delta)));
      }
    }
    return null;
  }

  /***
   * Gets all the coordinates shooting out from the central square given a delta
   *
   * @param base coordinate
   * @param delta change in coordinate
   * @return Set of Chess Tiles extending in that direction
   */
  private Set<ChessTile> getMoveBeam(Coordinate base, Coordinate delta, ChessBoard board) {
    Set<ChessTile> beam = new HashSet<>();
    Coordinate currentCoords = new Coordinate(base.getRow() + delta.getRow(), base.getCol() + delta.getCol());
    while (board.inBounds(currentCoords) && isTileEmpty(board, currentCoords)) {
      try {
        beam.add(board.getTile(currentCoords));
        currentCoords = new Coordinate(currentCoords.getRow() + delta.getRow(), currentCoords.getCol() + delta.getCol());
      } catch (OutsideOfBoardException e) {
        break;
      }
    }
    return beam;
  }

  /***
   * Handles exception from board empty method
   *
   * @param board to check for coordinate on
   * @param coordinate to check for emptiness
   * @return if the coordinate on the board is empty
   */
  private boolean isTileEmpty(ChessBoard board, Coordinate coordinate) {
    try {
      return board.isTileEmpty(coordinate);
    } catch (OutsideOfBoardException e) {
      return false;
    }
  }
}
