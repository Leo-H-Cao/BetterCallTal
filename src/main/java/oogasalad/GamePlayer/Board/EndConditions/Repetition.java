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
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Repetition implements EndCondition {

  private static final String REP_NUM_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_REP_FILE = "Repetition";
  private static final Logger LOG = LogManager.getLogger(Repetition.class);
  private static final int DEFAULT_REP_NUM = 3;

  private int numRep;

  /***
   * Creates Repetition with default config file
   */
  public Repetition() {
    this(DEFAULT_REP_FILE);
  }

  /***
   * Creates Repetition with given config file
   *
   * @param configFile to read
   */
  public Repetition(String configFile) {
    numRep = FileReader.readOneInt(REP_NUM_FILE_PATH_HEADER + configFile, DEFAULT_REP_NUM);
  }

  /***
   * Return draw if same position repeated n times
   *
   * @param board to get rep from
   * @return draws for everyone if repetition reached
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return numRep == board.getHistory().stream().filter(h -> h.board().equals(board)).count() ?
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
