package oogasalad.GamePlayer.ValidStateChecker;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_CONFIG_FILE_HEADER;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_DEFAULT_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.CustomMovements.BankLeaver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
    configFile =  G_FILE_PATH_HEADER + configFile + JSON_EXTENSION;
    relativeCoordinates = new ArrayList<>();
    LOG.debug(String.format("Config file: %s", configFile));

    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
      JSONObject data = new JSONObject(content);
      JSONArray relCoords = data.getJSONArray("relativeCoordinates");
      for(int i=0; i<relCoords.length(); i++) {
        JSONArray current = relCoords.getJSONArray(i);
        relativeCoordinates.add(Coordinate.of(current.getInt(0), current.getInt(1)));
      }
    } catch (IOException e) {
      relativeCoordinates = DEFAULT;
    }
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
      try { return board.getTile(Coordinate.add(c, move.getCoordinates())).getPiece().isPresent();}
      catch (OutsideOfBoardException e) {return true;}});
  }

  /***
   * @return relative coordinates for testing
   */
  List<Coordinate> getRelativeCoordinates() {
    return relativeCoordinates;
  }
}
