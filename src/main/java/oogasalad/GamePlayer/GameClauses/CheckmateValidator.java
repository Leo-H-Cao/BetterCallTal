package oogasalad.GamePlayer.GameClauses;

import static oogasalad.GamePlayer.GameClauses.CheckValidator.isInCheck;
import static oogasalad.GamePlayer.GameClauses.StalemateValidator.isStaleMate;

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

  public static boolean isInMate2(ChessBoard board, int id) throws OutsideOfBoardException {
    if (!isInCheck(board, id) || isStaleMate(board, id)) return false;

    boolean mainCanMove = board.getPieces().stream()
        .anyMatch(p -> p.checkTeam(id) && p.isTargetPiece());
    if (mainCanMove) return false;

    //TODO finish implementing checkmate
    return true;
  }

}