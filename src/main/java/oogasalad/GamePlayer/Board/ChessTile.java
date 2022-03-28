package oogasalad.GamePlayer.Board;

import java.util.List;
import java.util.Optional;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class ChessTile {

  private Coordinate coordinate;
  private List<Piece> pieces;

  /***
   * Creates a chess tile with a given coordinate
   */
  public ChessTile(Coordinate coordinate) {
    this(coordinate, List.of());
  }

  /***
   * Creates a chess tile with one piece and a given coordinate
   */
  public ChessTile(Coordinate coordinate, Piece piece) {
    this(coordinate, List.of(piece));
  }

  /***
   * Creates a chess tile with multiple pieces on it and a given coordinate
   */
  public ChessTile(Coordinate coordinate, List<Piece> pieces) {
    this.coordinate = coordinate;
    this.pieces = pieces;
  }

  /***
   * @return coordinates of tile
   */
  public Coordinate getCoordinates() {
    return coordinate;
  }

  /***
   * Gets the piece on the square or the most recent piece on the square
   *
   * @return piece on the square or empty Optional if no piece is on the square
   */
  public Optional<Piece> getPiece() {
    if(pieces == null || pieces.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(pieces.get(pieces.size() - 1));
  }

  /***
   * @return all pieces on the tile
   */
  public List<Piece> getPieces() {
    return pieces;
  }
}
