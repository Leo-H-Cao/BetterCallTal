package oogasalad.GamePlayer.Board.Tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.CustomTiles.TileAction;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;

public class ChessTile implements Tile, Cloneable {

  private Coordinate coordinate;
  private List<Piece> pieces;
  private List<TileAction> specialActions;
  private Optional<String> customImg;

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
   * Sets up the special actions list
   *
   * @param tileActions is this tile's special actions
   */
  public void setSpecialActions(List<TileAction> tileActions) {
    specialActions = tileActions;
  }

  /***
   * @param img to set customImg to
   */
  public void setCustomImg(String img) {
    customImg = Optional.of(img);
  }

  /***
   * @return custom img
   */
  public Optional<String> getCustomImg() {
    return customImg;
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
    this(coordinate, pieces, new ArrayList<TileAction>());
  }

  /**
   * Create a chess tile with coordinate, pieces, and special actions
   */
  public ChessTile(Coordinate coordinate, List<Piece> pieces, List<TileAction> actions) {
    this.coordinate = coordinate;
    this.pieces = pieces;
    this.specialActions = actions;
    this.specialActions = new ArrayList<>();
    this.customImg = Optional.empty();
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

  public Set<ChessTile> executeActions(ChessBoard board) throws OutsideOfBoardException {
    return specialActions.stream().flatMap(t -> t.executeAction(this, board).stream()).collect(Collectors.toSet());
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
}
