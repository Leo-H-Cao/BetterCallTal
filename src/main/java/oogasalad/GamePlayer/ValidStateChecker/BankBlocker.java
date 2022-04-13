package oogasalad.GamePlayer.ValidStateChecker;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class BankBlocker implements ValidStateChecker {

  private static final String COL_PATH = "doc/GameEngineResources/Other/CrazyhouseSepCol";
  private int bankStartCol;

  /***
   * Creates a custom movement that prevents pieces from going into the piece bank
   */
  public BankBlocker() {

  }

  private void getBlockerColumn() {

  }

  @Override
  public boolean isValid(ChessBoard board, Piece piece,
      ChessTile move) throws OutsideOfBoardException {
    return move.getCoordinates().getCol() < bankStartCol;
  }
}
