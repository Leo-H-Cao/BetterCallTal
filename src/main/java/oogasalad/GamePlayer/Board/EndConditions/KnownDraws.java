package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Board.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * Creates an end condition for known draws, such as K+K+B
 *
 * @author Vincent Chen
 */
public class KnownDraws implements EndCondition {

  private static final String KD_CONFIG_FILE_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_KD_CONFIG = "KnownDraws";
  private static final Logger LOG = LogManager.getLogger(KnownDraws.class);

  private List<List<String>> drawConfigs;

  /***
   * Creates KnownDraws with default config file
   */
  public KnownDraws() {
    this(DEFAULT_KD_CONFIG);
  }

  /***
   * Creates KnownDraws with given config file
   *
   * @param configFile to read
   */
  public KnownDraws(String configFile) {
    drawConfigs = getDrawConfigs(KD_CONFIG_FILE_HEADER + configFile + JSON_EXTENSION);
  }

  /**
   * @return list of piece names that represent a draw situation
   */
  private List<List<String>> getDrawConfigs(String configFile) {
    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
      JSONArray JSONConfigs = new JSONObject(content).getJSONArray("drawPieces");
      List<List<String>> allConfigs = new ArrayList<>();

      for(int i=0; i<JSONConfigs.length(); i++) {
        JSONArray pieceNames = JSONConfigs.getJSONObject(i).getJSONArray("pieces");
        List<String> currentConfig = new ArrayList<>(pieceNames.length());
        for(int j=0; j<pieceNames.length(); j++) {
          currentConfig.add(pieceNames.getString(j).toLowerCase());
        }
        allConfigs.add(currentConfig);
      }
      return allConfigs;
    } catch (JSONException | IOException e) { return Collections.emptyList();}
  }
  /**
   * Returns draw if a known draw position is reached
   *
   * @param board to get draws from
   * @return 0.5 for all players if a known draw is reached
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return drawConfigs.contains(board.getPieces().stream().map(p ->
        p.getName().toLowerCase()).toList()) ? Arrays.stream(board.getPlayers()).collect(
        Collectors.toMap(Player::teamID, p -> DRAW)) : Collections.emptyMap();
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }

  /***
   * @return draw configs for testing
   */
  List<List<String>> getDrawConfigs() {
    return drawConfigs;
  }
}
