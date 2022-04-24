package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.Checkmate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;

public class CheckmateLoss extends Utility {

  public CheckmateLoss(){
    super();
  }
  public double getUtility(int player, ChessBoard board){
    Checkmate c = new Checkmate();
    try {
      if(c.isInMate(board, player)){
        return Integer.MIN_VALUE;
      }
    } catch (EngineException e) {
      return Integer.MIN_VALUE;
    }
    return Integer.MAX_VALUE;
  }

}
