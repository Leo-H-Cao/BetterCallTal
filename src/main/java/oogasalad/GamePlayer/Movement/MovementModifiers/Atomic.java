package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Atomic implements MovementModifier{

  private static final Logger LOG = LogManager.getLogger(Atomic.class);

  private static final int surroundDistance = 1;

  /***
   * Explodes all pieces if a capture happens
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return set of updated tiles after explosion
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    Set<ChessTile> explodedSquares = new HashSet<>();
    try {
      getSurroundingTiles(board.getTile(piece.getCoordinates()), board).forEach((t) -> {
            t.clearPieces();
            explodedSquares.add(t);
          });
      return explodedSquares;
    } catch (OutsideOfBoardException e) {
     return Collections.emptySet();
    }
  }

  /***
   * @param center is the center tile of the surrounding circle
   * @param board to get the tiles on
   * @return tiles one square away from the center
   */
  private Set<ChessTile> getSurroundingTiles(ChessTile center, ChessBoard board) {
    Set<ChessTile> surroundingTiles = new HashSet<>();
    IntStream.range(-surroundDistance, surroundDistance+1).forEach((i) -> IntStream.range(-surroundDistance, surroundDistance+1).forEach((j) -> {
      try {
        surroundingTiles.add(board.getTile(
            Coordinate.of(center.getCoordinates().getRow()+i, center.getCoordinates().getCol()+j)));
      } catch(OutsideOfBoardException ignored) {
        LOG.debug(String.format("Invalid coordinate detected: (%d, %d)", center.getCoordinates().getRow()+i, center.getCoordinates().getCol()+j));
      }
    }));
    LOG.debug("Surrounding tiles: " + surroundingTiles);
    return surroundingTiles;
  }
}
