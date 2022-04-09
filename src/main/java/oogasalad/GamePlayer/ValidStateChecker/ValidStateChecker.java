package oogasalad.GamePlayer.ValidStateChecker;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;

public interface ValidStateChecker {

  /***
   * Checks if a board state is valid based on some pre-defined rule set, such as check
   *
   * @return if the move is valid
   */
  boolean isValid(ChessBoard board, int id) throws OutsideOfBoardException;
}
