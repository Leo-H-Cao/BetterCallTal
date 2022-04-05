package oogasalad.GamePlayer.GameClauses;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;

/**
 * This class
 * @author Jose Santillan
 */
public class CheckmateValidator {


  /**
   * Public method that returns whether the board has reached
   * checkmate
   * @return Whether the board is in checkmate or not
   */
  public static boolean isInMate(ChessBoard board, int id) throws OutsideOfBoardException {

    return CheckValidator.isInCheck(board, id) && StalemateValidator.isStaleMate(board, id);
  }
}
