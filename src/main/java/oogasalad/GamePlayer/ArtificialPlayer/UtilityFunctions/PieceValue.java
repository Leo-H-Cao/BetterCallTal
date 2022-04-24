package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.GamePiece.Piece;

public class PieceValue extends Utility {

  public PieceValue(){
    super();
  }

  public double getUtility(int player, ChessBoard board){
    double totalValue = 0;
    List<Piece> teamPieces = board.getPieces();
    for(Piece p : board.getPieces()){
      if(p.getTeam()==player){
        totalValue += p.getPieceValue();
      }
      else{
        totalValue -= p.getPieceValue();
      }
    }
    return totalValue;
  }

}


