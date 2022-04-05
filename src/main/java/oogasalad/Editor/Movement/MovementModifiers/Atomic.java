package oogasalad.Editor.Movement.MovementModifiers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import oogasalad.Editor.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Board.Tiles.GamePiece.Piece;

public class Atomic implements MovementModifier{

  private static final int surroundDistance = 1;
  /***
   * Explodes all pieces if a capture happens
   *
   * @param piece that is referenced
   * @param finalTile the tile the piece is moving to
   * @param board that piece is on
   * @return set of updated tiles after explosion
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessTile finalTile, ChessBoard board) {
    Set<ChessTile> explodedSquares = new HashSet<>();
    getSurroundingTiles(finalTile, board).stream().filter((t) -> !t.getPieces().isEmpty()).forEach((t) -> {
      t.clearPieces();
      explodedSquares.add(t);
    });
    return explodedSquares;
  }

  /***
   * @param center is the center tile of the surrounding circle
   * @param board to get the tiles on
   * @return tiles one square away from the center
   */
  private Set<ChessTile> getSurroundingTiles(ChessTile center, ChessBoard board) {
    Set<ChessTile> surroundingTiles = new HashSet<>();
    IntStream.range(-surroundDistance, surroundDistance).forEach((i) -> {
      IntStream.range(-surroundDistance, surroundDistance).forEach((j) -> {
        try {
          surroundingTiles.add(board.getTile(Coordinate.of(center.getCoordinates().getRow()+i, center.getCoordinates().getCol()+j)));
        } catch(OutsideOfBoardException ignored) {}
      });
    });
    return surroundingTiles;
  }
}
