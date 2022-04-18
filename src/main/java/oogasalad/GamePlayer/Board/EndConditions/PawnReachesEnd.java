package oogasalad.GamePlayer.Board.EndConditions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PawnReachesEnd implements EndCondition {

  private static final String ENDZONE_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_ENDZONE_FILE = "FootballEndzonePieces";
  private static final Logger LOG = LogManager.getLogger(PawnReachesEnd.class);
  private static final List<String> DEFAULT_ENDZONE_PIECES = List.of("Pawn");

  private List<String> endzonePieces;

  /***
   * Creates PawnReachesEnd with default config file
   */
  public PawnReachesEnd() {
    this(DEFAULT_ENDZONE_FILE);
  }

  /***
   * Creates PawnReachesEnd with given config file
   *
   * @param configFile to read
   */
  public PawnReachesEnd(String configFile) {
    endzonePieces = FileReader.read(ENDZONE_FILE_PATH_HEADER + configFile, DEFAULT_ENDZONE_PIECES);
  }

  /***
   * @param board to get scores on
   * @return win for pawn that reaches end, loss for other team
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    Map<Integer, Double> scores = new HashMap<>();
    int[] teams = board.getTeams();

    board.stream().filter((l) -> l.get(0).getCoordinates().getRow() == 0 ||
        l.get(0).getCoordinates().getRow() == board.getBoardHeight() - 1).forEach((l) ->
        l.stream().filter((t) -> t.getPiece().isPresent() &&
            endzonePieces.contains(t.getPiece().get().getName())).findFirst().ifPresent(
            (t) -> {
              scores.put(t.getPiece().get().getTeam(), WIN);
              Arrays.stream(teams).filter((team) -> team != t.getPiece().get().getTeam()).forEach(
                  (team) -> scores.put(team, LOSS)
              );
            })
    );
    LOG.debug("Scores: " + scores);
    return scores;
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
