package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class Swap implements TileAction {

  private static final Random DICE = new Random();

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public Swap() {
    super();
  }

  /***
   * Swaps two random pieces
   *
   * @return swapped tiles
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    List<Piece> nonTargetPieces = tile.getPieces().stream()
        .filter(piece -> !piece.isTargetPiece()).toList();

    Set<ChessTile> updatedTiles = new HashSet<>();
    updatedTiles.add(tile);

    for (Piece p : nonTargetPieces) {
      List<Piece> pieces = board.getPieces().stream().filter(piece -> !piece.equals(p)).toList();
      Piece p2 = pieces.get(DICE.nextInt(pieces.size()));
      ChessTile secondTile = swap(p, p2, board);
      updatedTiles.add(secondTile);
    }

    return updatedTiles;
  }

  /***
   * Swaps two pieces on the board
   *
   * @param p1 to swap
   * @param p2 to swap
   * @param board to swap on
   * @return second tile swapped
   */
  private ChessTile swap(Piece p1, Piece p2, ChessBoard board)
      throws EngineException {

    Coordinate c1 = p1.getCoordinates();
    Coordinate c2 = p2.getCoordinates();

    board.getTile(c1).removePiece(p1);
    board.getTile(c2).removePiece(p2);

    board.placePiece(c2, p1);
    board.placePiece(c1, p2);

    p1.forceUpdateCoordinates(c2);
    p2.forceUpdateCoordinates(c1);

    return board.getTile(c2);
  }
}
