package oogasalad.GamePlayer.Board.EndConditions;

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
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class KnownDraws implements EndCondition {

  private static final String KD_CONFIG_FILE = "doc/GameEngineResources/Other/KnownDraws.json";
  private static final Logger LOG = LogManager.getLogger(KnownDraws.class);

  private static final List<List<String>> DRAW_CONFIGS = getDrawConfigs();

  /**
   * @return list of piece names that represent a draw situation
   */
  private static List<List<String>> getDrawConfigs() {
    try {
      String content = new String(Files.readAllBytes(Path.of(KD_CONFIG_FILE)));
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
    } catch (IOException e) {
      LOG.warn("Draw config read failed");
      return Collections.emptyList();
    }
  }
  /**
   * Returns draw if a known draw position is reached
   *
   * @param board to get draws from
   * @return 0.5 for all players if a known draw is reached
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return DRAW_CONFIGS.contains(board.getPieces().stream().filter(p -> !p.isTargetPiece()).map(
        p -> p.getName().toLowerCase()).toList()) ? Arrays.stream(board.getPlayers()).collect(
        Collectors.toMap(Player::teamID, p -> DRAW)) : Collections.emptyMap();
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
