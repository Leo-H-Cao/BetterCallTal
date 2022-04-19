package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * End condition where first person to reach the middle of the board wins
 */
public class KingOfTheHill implements EndCondition {

  private static final Logger LOG = LogManager.getLogger(KingOfTheHill.class);

  /***
   * @param board to get scores from
   * @return WIN for king in the middle, LOSS for others, empty map if not applicable
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    Set<ChessTile> middleTiles = getMiddleTiles(board);
    Map<Integer, Double> scores = new HashMap<>();

    middleTiles.stream().filter((t) -> t.getPiece().isPresent() && t.getPiece().get().isTargetPiece())
        .findFirst().ifPresent((t) -> {
          int winningTeam = t.getPiece().get().getTeam();
          scores.put(winningTeam, WIN);
          LOG.debug(String.format("Players: %s" , Arrays.toString(board.getPlayers())));
          LOG.debug(String.format("Winning team put: %d, %f", winningTeam, scores.get(winningTeam)));
          LOG.debug(String.format("Losers: %s", Arrays.toString(board.getPlayer(winningTeam).opponentIDs())));
          Arrays.stream(board.getPlayer(winningTeam).opponentIDs()).forEach(team ->
              scores.put(team, LOSS));
        });

    if(scores.isEmpty()) return scores;

    LOG.debug(String.format("Non-draw scores: %s", scores));
    Arrays.stream(board.getPlayers()).filter((p) -> !scores.containsKey(p.teamID())).forEach(pl ->
        scores.put(pl.teamID(), DRAW));

    LOG.debug(String.format("Final scores: %s", scores));
    return scores;
  }

  /***
   * @return the middle tiles in the board
   */
  private Set<ChessTile> getMiddleTiles(ChessBoard board) {
    int[] colRange = getRange(board.getBoardLength());
    int[] rowRange = getRange(board.getBoardHeight());
    Set<ChessTile> middleTiles = new HashSet<>();

    IntStream.range(rowRange[0], rowRange[1]+1).forEach((i) ->
        IntStream.range(colRange[0], colRange[1]+1).forEach((j) ->
        { try {
            middleTiles.add(board.getTile(Coordinate.of(i, j)));
          } catch (OutsideOfBoardException ignored) {
            LOG.debug("Middle tile out of bounds");
          }
        }));
    LOG.debug(String.format("Middle tiles: %s", middleTiles));
    return middleTiles;
  }

  /**
   * @param max length of section
   * @return middle element if odd, middle two if even
   */
  private int[] getRange(int max) {
    return max % 2 == 0 ? new int[]{max/2-1, max/2} : new int[]{max/2, max/2};
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
