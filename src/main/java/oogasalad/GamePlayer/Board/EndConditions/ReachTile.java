package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Board.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * Winner reaches given tile
 *
 * @author Vincent Chen
 */
public class ReachTile implements EndCondition {

  private static final String RT_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_RT_FILE = "ReachTileDefault";
  private static final Coordinate DEFAULT_GOAL = Coordinate.of(0, 0);
  private static final List<String> DEFAULT_PIECES = List.of("pawn", "checker");

  private Coordinate goal;
  private List<String> eligiblePieces;

  /***
   * Creates ReachTile with default config file
   */
  public ReachTile() {
    this(DEFAULT_RT_FILE);
  }

  /***
   * Creates ReachTile with given config file
   *
   * @param configFile to read
   */
  public ReachTile(String configFile) {
    configFile = RT_FILE_PATH_HEADER + configFile + JSON_EXTENSION;
    try {
      String content = new String(Files.readAllBytes(
          Path.of(configFile)));
      JSONObject data = new JSONObject(content);

      goal = Coordinate.of(data.getJSONArray("tile").getInt(0),
          data.getJSONArray("tile").getInt(1));
      JSONArray pieceArray = data.getJSONArray("eligiblePieces");
      eligiblePieces = new ArrayList<>(pieceArray.length());

      for(int i=0; i< pieceArray.length(); i++) {
        eligiblePieces.add(pieceArray.getString(i).toLowerCase());
      }

    } catch (IOException e) {
      goal = DEFAULT_GOAL;
      eligiblePieces = DEFAULT_PIECES;
    }
  }

  /***
   * Winner reaches the given tile, losers are winner's opponent, draw for everyone else
   *
   * @param board to check
   * @return map of winners, losers, other based on the rule above; empty map if not applicable
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    try {
      Map<Integer, Double> scores = new HashMap<>();
      if(board.getTile(goal).getPiece().isPresent() &&
          board.getTile(goal).getPieces().stream().anyMatch(p ->
              eligiblePieces.contains(p.getName().toLowerCase()))) {

        board.getTile(goal).getPieces().stream().filter(p ->
            eligiblePieces.contains(p.getName().toLowerCase())).forEach(p -> scores.put(p.getTeam(), WIN));
        scores.keySet().stream().toList().forEach(t -> Arrays.stream(board.getPlayer(t).opponentIDs())
            .filter(o -> !scores.containsKey(o)).forEach(o -> scores.put(o, LOSS)));
        Arrays.stream(board.getPlayers()).filter(t -> !scores.containsKey(t.teamID())).forEach(t ->
            scores.put(t.teamID(), DRAW));
      }
      return scores;
    } catch (OutsideOfBoardException e) {
      return new HashMap<>();
    }
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
