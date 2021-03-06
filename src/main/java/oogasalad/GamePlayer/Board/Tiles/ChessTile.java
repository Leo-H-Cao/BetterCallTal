package oogasalad.GamePlayer.Board.Tiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.TileAction;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

/***
 * Class representing a tile on a chessboard
 *
 * @author Vincent Chen
 */
public class ChessTile implements Cloneable {

  private Coordinate coordinate;
  private List<Piece> pieces;
  private List<TileAction> specialActions;
  private String customImg;

  public ChessTile() {
    this(new Coordinate(0, 0), new ArrayList<>());
  }

  /**
   * Creates a chess tile with a given coordinate
   */
  public ChessTile(Coordinate coordinate) {
    this(coordinate, new ArrayList<>());
  }

  /**
   * Creates a chess tile with one piece and a given coordinate
   */
  public ChessTile(Coordinate coordinate, Piece piece) {
    this(coordinate, new ArrayList<>(List.of(piece)));
  }

  /**
   * Creates a chess tile with multiple pieces on it and a given coordinate
   */
  public ChessTile(Coordinate coordinate, List<Piece> pieces) {
    this(coordinate, pieces, new ArrayList<>());
  }

  /**
   * Create a chess tile with coordinate, pieces, and special actions
   */
  public ChessTile(Coordinate coordinate, List<Piece> pieces, List<TileAction> actions) {
    this.coordinate = coordinate;
    this.pieces = pieces;
    this.specialActions = actions;
    this.customImg = null;
  }

  /**
   * Sets up the special actions list
   *
   * @param tileActions is this tile's special actions
   */
  public void setSpecialActions(List<TileAction> tileActions) {
    specialActions = tileActions;
  }

  /***
   * @return custom img
   */
  public Optional<String> getCustomImg() {
    return customImg == null ? Optional.empty() : Optional.of(customImg);
  }

  /***
   * @param img to set customImg to
   */
  public void setCustomImg(String img) {
    customImg = img;
  }

  /**
   * Adds a piece to the given square
   *
   * @param piece to add
   */
  public void addPiece(Piece piece) {
    pieces.add(piece);
  }

  /**
   * @return coordinates of tile
   */
  public Coordinate getCoordinates() {
    return coordinate;
  }

  /**
   * Gets the piece on the square or the most recent piece on the square
   *
   * @return piece on the square or empty Optional if no piece is on the square
   */
  public Optional<Piece> getPiece() {
    if (pieces == null || pieces.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(pieces.get(pieces.size() - 1));
  }

  /**
   * @return all pieces on the tile
   */
  public List<Piece> getPieces() {
    return pieces;
  }

  /**
   * Appends a piece to the list of pieces
   */
  public boolean appendPiece(Piece piece) {
    return pieces.add(piece);
  }

  /**
   * Removes a piece from the list of pieces
   */
  public boolean removePiece(Piece piece) {
    return pieces.remove(piece);
  }

  /**
   * Executes tile actions
   *
   * @param board to execute actions on
   * @return set of updated tiles
   */
  public Set<ChessTile> executeActions(ChessBoard board) {

    return specialActions.stream().flatMap(t -> {
      try {
        return t.executeAction(this, board).stream();
      } catch (EngineException e) {
        return new HashSet<ChessTile>().stream();
      }
    }).collect(Collectors.toSet());
  }


  /**
   * @return coordinates of tile and pieces on it
   */
  public String toString() {
    return coordinate.toString() + ": " + pieces;
  }

  /**
   * @return copy of tile and its pieces
   */
  @Override
  public ChessTile clone() {
    return new ChessTile(new Coordinate(this.coordinate.getRow(), this.coordinate.getCol()),
        clonePieces());
  }

  /**
   * @return clone of all pieces in this tile
   */
  private List<Piece> clonePieces() {
    return pieces.stream().map(Piece::clone).collect(Collectors.toList());
  }

  /**
   * Clears pieces on tile
   */
  public void clearPieces() {
    pieces = new ArrayList<>();
  }

  /***
   * Compare chess tiles
   *
   * @param o to compare
   * @return if both chess tiles have the same coordinates
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChessTile chessTile = (ChessTile) o;
    return coordinate.equals(chessTile.coordinate);
  }

  /***
   * @return hashcode of tile
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
