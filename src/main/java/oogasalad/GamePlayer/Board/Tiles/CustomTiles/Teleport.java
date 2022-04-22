package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * Teleports piece to given square
 *
 * @author Vincent Chen
 */
public class Teleport implements TileAction {

  private static final String TP_FILE_PATH_HEADER = "doc/GameEngineResources/Other/teleportLocations/";
  private static final String DEFAULT_TP_FILE = "teleport00";
  private static final Coordinate DEFAULT_TP = Coordinate.of(0, 0);

  private Coordinate teleportLocation;

  /***
   * Creates Teleport with default config file
   */
  public Teleport() {
    this(DEFAULT_TP_FILE);
  }

  /***
   * Creates Teleport with given config file
   *
   * @param configFile to read
   */
  public Teleport(String configFile) {
    configFile = TP_FILE_PATH_HEADER + configFile + JSON_EXTENSION;
    try {
      String content = new String(Files.readAllBytes(
          Path.of(configFile)));
      JSONObject data = new JSONObject(content);
      teleportLocation = Coordinate.of(data.getJSONArray("tile").getInt(0),
          data.getJSONArray("tile").getInt(1));
    } catch (IOException e) {
      teleportLocation = DEFAULT_TP;
    }
  }

  /***
   * Teleports piece on the tile to the location determined by file read if the teleport square
   * is open
   *
   * @param tile that was activated
   * @param board to teleport on
   * @return empty set if no teleportation, parameter tile and teleport location if teleported
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    if(tile.getPiece().isPresent() && board.getTile(teleportLocation).getPiece().isEmpty()) {
      tile.getPiece().get().updateCoordinates(board.getTile(teleportLocation), board);
      return Set.of(tile, board.getTile(teleportLocation));
    }
    return Collections.emptySet();
  }
}
