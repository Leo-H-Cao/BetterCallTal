package oogasalad.GamePlayer.Board.EndConditions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Movement.MovementModifiers.Atomic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PawnReachesEnd implements EndCondition {

  private static final String ENDZONE_FILE_PATH = "doc/GameEngineResources/Other/FootballEndzonePieces";
  private static final Logger LOG = LogManager.getLogger(PawnReachesEnd.class);

  private static List<String> ENDZONE_PIECES;

  public PawnReachesEnd() {
    if(ENDZONE_PIECES == null) {
      assignEndzonePieceNames();
    }
    LOG.debug("Endzone pieces: " + ENDZONE_PIECES);
  }

  private void assignEndzonePieceNames() {
    ENDZONE_PIECES = new ArrayList<>();
    try {
      File immuneFile = new File(ENDZONE_FILE_PATH);
      Scanner reader = new Scanner(immuneFile);
      while (reader.hasNext()) {
        ENDZONE_PIECES.add(reader.next().trim());
      }
      reader.close();
    } catch (Exception e) {
      LOG.warn("Could not find file: " + ENDZONE_FILE_PATH);
      ENDZONE_PIECES = List.of("Pawn");
    }
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
            ENDZONE_PIECES.contains(t.getPiece().get().getName())).findFirst().ifPresent(
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
