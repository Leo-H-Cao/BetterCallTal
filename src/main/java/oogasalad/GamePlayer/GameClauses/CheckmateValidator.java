package oogasalad.GamePlayer.GameClauses;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

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
