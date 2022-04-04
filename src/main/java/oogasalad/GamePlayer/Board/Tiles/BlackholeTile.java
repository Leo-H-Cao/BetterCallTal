package oogasalad.GamePlayer.Board.Tiles;

import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;

public class BlackholeTile extends ChessTile {

  public void executeAction(ChessBoard board) throws OutsideOfBoardException {
    while(!this.getPieces().isEmpty()){
      this.removePiece(getPieces().get(0));
    }
  }
}
