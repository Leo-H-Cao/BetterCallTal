package oogasalad.GamePlayer.Movement;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessTile;
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
   * @return set of updated tiles
   */
  public Set<ChessTile> movePiece(Piece piece, ChessTile finalSquare) {
    return null;
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece) {
    return null;
  }
}
