package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.Board.BoardSetup.JSON_EXTENSION;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates a movement modifier that pushes everything to the given coordinate
 *
 * @author Vincent Chen
 */
public class GravityMovementModifier implements MovementModifier {

  private static final Logger LOG = LogManager.getLogger(GravityMovementModifier.class);

  private static final String G_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String G_DEFAULT_FILE = "GravityRight";
  private static final List<Coordinate> DEFAULT = List.of(Coordinate.of(0, 1));

  private List<Coordinate> relativeCoordinates;

  /***
   * Create gravity movement modifier with default config file
   */
  public GravityMovementModifier() {
    this(G_DEFAULT_FILE);
  }

  /***
   * Create gravity movement modifier with given config file
   *
   * @param configFile to read
   */
  public GravityMovementModifier(String configFile) {
    relativeCoordinates = FileReader.readCoordinates(
        G_FILE_PATH_HEADER + configFile + JSON_EXTENSION, "relativeCoordinates", DEFAULT);
  }

  /***
   * Moves the piece as far as possible in the given direction if needed
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return square the piece moved from and its end square
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    Set<ChessTile> updatedTiles = new HashSet<>();
    AtomicBoolean updated = new AtomicBoolean(true);
    while (updated.get()) {
      updated.set(false);
      board.getPieces().forEach(p -> {
        final Coordinate[] currentPieceTile = {p.getCoordinates()};
        relativeCoordinates.forEach(rc ->
            currentPieceTile[0] = findNewSpot(board, currentPieceTile[0], rc));
        if (p.getCoordinates() != currentPieceTile[0]) {
          updated.set(true);
          updatePieceCoordinates(updatedTiles, board, p, currentPieceTile[0]);
        }
      });
    }
    return updatedTiles;
  }

  /***
   * Continues to update the startCoordinate by checkCoordinate until a piece is reached
   *
   * @param board to move on
   * @param startCoordinate is the starting coordinate
   * @param checkCoordinate is the direction to move in
   * @return above
   */
  private Coordinate findNewSpot(ChessBoard board, Coordinate startCoordinate,
      Coordinate checkCoordinate) {
    try {
      while (board.isTileEmpty(Coordinate.add(startCoordinate, checkCoordinate))) {
        startCoordinate = Coordinate.add(startCoordinate, checkCoordinate);
      }
    } catch (Exception e) {
      LOG.debug(String.format("Out of bounds for tile: %s", startCoordinate));
    }
    return startCoordinate;
  }

  /***
   * Updates a piece's coordinates according to the new coordinate, removing the last element in
   * history because gravity movement should not count as piece history
   *
   * @param changedTiles set of modified tiles to add to
   * @param board to reference
   * @param piece to reference
   * @param newCoordinate to move the piece to
   */
  private void updatePieceCoordinates(Set<ChessTile> changedTiles, ChessBoard board,
      Piece piece, Coordinate newCoordinate) {
    try {
      changedTiles.add(board.getTile(piece.getCoordinates()));
      changedTiles.add(board.getTile(newCoordinate));
      piece.updateCoordinates(board.getTile(newCoordinate), board);
      piece.getHistory().remove(piece.getHistory().size() - 1);
    } catch (OutsideOfBoardException e) {LOG.debug(String.format("Out of bounds for tile: %s", newCoordinate));}
  }

  /***
   * @return relative coordinates list for testing
   */
  List<Coordinate> getRelativeCoordinates() {
    return relativeCoordinates;
  }
}
