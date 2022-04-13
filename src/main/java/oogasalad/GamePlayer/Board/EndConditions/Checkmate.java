package oogasalad.GamePlayer.Board.EndConditions;

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

  public boolean isInMate(ChessBoard board, int team) throws EngineException {
    if(stalemate.hasNoLegalMoves(board, team) && !check.isValid(board, team)){
      return true;
    }
    return false;
  }


  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    HashMap<Integer, Double> scores = new HashMap<>();
    try {
      for(int i : board.getTeams()){
        if(isInMate(board, i)){
          return generateScores(board, i);
        }
      }

    } catch (EngineException e) {
      e.printStackTrace();//TODO: handle the exception
    }
    return new HashMap<>();
  }

  private Map<Integer, Double> generateScores(ChessBoard board, int winner){
    HashMap<Integer, Double> finalScores = new HashMap<>();
    for(int i : board.getTeams()){
      if(i==winner){
        finalScores.put(i, 0.0);
      }
      else{
        finalScores.put(i, 1.0);
      }
    }
    return finalScores;
  }

}


