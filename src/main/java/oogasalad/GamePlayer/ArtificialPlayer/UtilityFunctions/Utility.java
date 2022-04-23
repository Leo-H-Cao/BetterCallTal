package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;

public abstract class Utility {

  public Utility(){

  }

  public abstract double getUtility(int player, ChessBoard board);


}
