package oogasalad.GamePlayer.Board.EndConditions;

import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.ValidStateChecker.Check;

/**
 * This class
 * @author Jose Santillan
 */
public class Checkmate implements EndCondition {

  /**
   * Public method that returns whether the board has reached checkmate
   *
   * @return Whether the board is in checkmate or not
   */

  private Check check = new Check();
  private Stalemate stalemate = new Stalemate();

  public boolean isInMate(ChessBoard board) throws EngineException {
    for(int i : board.getTeams()){
      if(stalemate.hasNoLegalMoves(board, i) && !check.isValid(board, i)){
        return true;
      }
    }
    return false;
  }


  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    HashMap<Integer, Double> scores = new HashMap<>();
    try {
      if(isInMate(board)){
        scores.put(1,1.0);
        scores.put(0, 0.0);
        return scores;
      }
    } catch (EngineException e) {
      e.printStackTrace();//TODO: handle the exception
    }
    return new HashMap<>();
  }
}