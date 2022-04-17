package oogasalad.GamePlayer.Board.EndConditions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Movement.MovementModifiers.Atomic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Repetition implements EndCondition {

  private static final String REP_NUM_FILE_PATH = "doc/GameEngineResources/Other/Repetition";
  private static final Logger LOG = LogManager.getLogger(Repetition.class);
  private static final int DEFAULT_REP_NUM = 3;

  private static final int NUM_REP = getRepNum();

  /***
   * Reads in file to get number of repetitions before draw
   *
   * @return number of repetitions needed based on file read
   */
  private static int getRepNum() {
    try {
      File repFile = new File(REP_NUM_FILE_PATH);
      Scanner reader = new Scanner(repFile);
      int repNum = reader.nextInt();
      reader.close();
      return repNum;
    } catch (Exception e) {
      LOG.warn("Could not find repetition file");
      return DEFAULT_REP_NUM;
    }
  }

  /***
   * Return draw if same position repeated n times
   *
   * @param board to get rep from
   * @return draws for everyone if repetition reached
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return NUM_REP == board.getHistory().stream().filter(h -> h.board().equals(board)).count() ?
        Arrays.stream(board.getPlayers()).collect(Collectors.toMap(Player::teamID, p -> DRAW)) :
        Collections.emptyMap();
  }

  /***
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
