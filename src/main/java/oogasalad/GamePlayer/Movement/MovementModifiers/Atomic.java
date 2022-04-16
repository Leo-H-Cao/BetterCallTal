package oogasalad.GamePlayer.Movement.MovementModifiers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Creates an movement modifier that explodes pieces around it
 */
public class Atomic implements MovementModifier{

  private static final String ATOMIC_IMMUNE_FILE_PATH = "doc/GameEngineResources/Other/AtomicImmune";
  private static final Logger LOG = LogManager.getLogger(Atomic.class);

  private final static List<String> EXPLOSION_IMMUNE_NAMES = assignImmune();
  private static final int surroundDistance = 1;

  /***
   * Assigns piece immune to explosions, default is just pawn
   */
  private static List<String> assignImmune() {

    try {
      List<String> immuneNames = new ArrayList<>();
      File immuneFile = new File(ATOMIC_IMMUNE_FILE_PATH);
      Scanner reader = new Scanner(immuneFile);
      while (reader.hasNext()) {
        immuneNames.add(reader.next().trim());
      }
      reader.close();
      return immuneNames;
    } catch (Exception e) {
      LOG.warn("Could not find atomic file");
      return List.of("Pawn");
    }
  }

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
      getSurroundingTiles(board.getTile(piece.getCoordinates()), board).stream().filter(
          (t) -> t.getPiece().isPresent()).filter((t) -> EXPLOSION_IMMUNE_NAMES.stream().noneMatch(
              (n) -> t.getPiece().get().getName().equalsIgnoreCase(n) && !t.getCoordinates().equals(piece.getCoordinates())
          )).forEach((t) -> {
            LOG.debug("Exploded piece name: " + t.getPiece().get().getName());
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
