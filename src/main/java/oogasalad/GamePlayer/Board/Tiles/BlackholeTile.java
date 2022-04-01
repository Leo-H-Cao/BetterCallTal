package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;

public class BlackholeTile extends ChessTile{

  public void executeAction(ChessBoard board) throws OutsideOfBoardException {
    while(!this.getPieces().isEmpty()){
      this.removePiece(getPieces().get(0));
    }
  }
}
