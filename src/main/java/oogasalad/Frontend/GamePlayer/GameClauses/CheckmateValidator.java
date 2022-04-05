package oogasalad.Frontend.GamePlayer.GameClauses;

import oogasalad.Frontend.GamePlayer.Board.ChessBoard;
import oogasalad.Frontend.GamePlayer.EngineExceptions.OutsideOfBoardException;

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

    boolean inCheck = CheckValidator.isInCheck(board, id);

    boolean staleMate = StalemateValidator.isStaleMate(board, id);
    return inCheck && staleMate;
  }
}
