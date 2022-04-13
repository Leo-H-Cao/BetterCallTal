package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;

public class Swap implements TileAction {

  private static final Random DICE = new Random();

  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    List<Piece> nonTargetPiece = tile.getPieces().stream()
        .filter(Piece::isTargetPiece).toList();

    Set<ChessTile> updatedTiles = new HashSet<>();
    updatedTiles.add(tile);

    for (Piece p : nonTargetPiece) {
      List<Piece> pieces = board.getPieces();
      Piece p2 = pieces.get(DICE.nextInt(pieces.size()));
      ChessTile secondTile = swap(p, p2, board);
      updatedTiles.add(secondTile);
    }

    return updatedTiles;
  }

  private ChessTile swap(Piece p1, Piece p2, ChessBoard board)
      throws EngineException {

    Coordinate c1 = p1.getCoordinates();
    Coordinate c2 = p2.getCoordinates();

    board.move(p1, c2);
    board.move(p2, c1);
    return board.getTile(c2);
  }

}
