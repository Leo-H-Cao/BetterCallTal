package oogasalad.GamePlayer.ValidStateChecker;

import static oogasalad.GamePlayer.Board.BoardSetup.JSON_EXTENSION;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * VSC that only allows moves that don't have an empty square in the given direction
 *
 * @author Vincent Chen
 */
public class GravityVSC implements ValidStateChecker {

  private static final Logger LOG = LogManager.getLogger(ValidStateChecker.class);

  private static final String G_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String G_DEFAULT_FILE = "GravityC4";
  private static final List<Coordinate> DEFAULT = List.of(Coordinate.of(1, 0));

  private List<Coordinate> relativeCoordinates;

  /***
   * Create GravityVSC with default config file
   */
  public GravityVSC() {
    this(G_DEFAULT_FILE);
  }

  /***
   * Create GravityVSC with given config file
   *
   * @param configFile to read
   */
  public GravityVSC(String configFile) {
    configFile = G_FILE_PATH_HEADER + configFile + JSON_EXTENSION;
    LOG.debug(String.format("Config file: %s", configFile));
    relativeCoordinates = FileReader.readCoordinates(configFile, "relativeCoordinates", DEFAULT);
  }

  /***
   * Returns if the given move does not have an empty space in the given direction
   *
   * @param board to check
   * @param piece to check
   * @param move to check
   * @return above
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece, ChessTile move) throws EngineException {
    return relativeCoordinates.stream().anyMatch(c -> {
      try {
        return board.getTile(Coordinate.add(c, move.getCoordinates())).getPiece().isPresent();
      } catch (OutsideOfBoardException e) {
        return true;
      }
    });
  }

  /***
   * @return relative coordinates for testing
   */
  List<Coordinate> getRelativeCoordinates() {
    return relativeCoordinates;
  }
}
