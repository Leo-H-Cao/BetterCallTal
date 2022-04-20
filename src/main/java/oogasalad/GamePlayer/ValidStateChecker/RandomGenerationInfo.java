package oogasalad.GamePlayer.ValidStateChecker;

import java.util.Set;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Info related to a previously created random generation
 *
 * @author Vincent Chen
 */
public record RandomGenerationInfo(Set<ChessTile> allMoves,
                                   Set<ChessTile> acceptedMoves) {

  /***
   * Constructor with of notation
   *
   * @return new RandomGenerationInfo object with given parameters
   */
  public static RandomGenerationInfo of(Set<ChessTile> allMoves,
      Set<ChessTile> acceptedMoves) {
    return new RandomGenerationInfo(allMoves, acceptedMoves);
  }
}
