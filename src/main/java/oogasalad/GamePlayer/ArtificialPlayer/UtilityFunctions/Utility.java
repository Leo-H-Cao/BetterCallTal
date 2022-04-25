package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import oogasalad.GamePlayer.Board.ChessBoard;

public abstract class Utility {

  public Utility(){

  }

  public abstract double getUtility(int player, ChessBoard board);


}
