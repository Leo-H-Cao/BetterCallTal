package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;

public abstract class Utility {
  ChessBoard board;

  public Utility(ChessBoard board){
    this.board = board;
  }

  public abstract double getUtility(int player);


}
