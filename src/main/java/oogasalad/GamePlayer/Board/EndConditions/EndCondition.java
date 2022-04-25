package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Map;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.util.FileReader;

/***
 * Interface for custom game end results
 *
 * @author Vincent Chen
 */
public interface EndCondition extends Comparable<EndCondition> {

  String GAME_END_VALUES_FILE_PATH_HEADER = "doc/GameEngineResources/Other/gameEndValues/";
  double WIN = FileReader.readOneDouble(GAME_END_VALUES_FILE_PATH_HEADER + "Win", 1.0);
  double DRAW = FileReader.readOneDouble(GAME_END_VALUES_FILE_PATH_HEADER + "Draw", 0.5);
  double LOSS =  FileReader.readOneDouble(GAME_END_VALUES_FILE_PATH_HEADER + "Loss", 0.0);

  /**
   * Determines if the game is over, and if so, returns the points awarded to each team
   *
   * @return the point values awarded to every team if a game is over, else empty
   */
  Map<Integer, Double> getScores(ChessBoard board);

}
