package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import oogasalad.GamePlayer.Board.ChessBoard;

public class Checkmate extends Utility {

  public Checkmate(){
    super();
  }
  public double getUtility(int player, ChessBoard board){
    return Integer.MAX_VALUE;
  }

}
