package oogasalad.GamePlayer.Board.EndConditions;

import java.util.HashMap;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.ValidStateChecker.Check;

/**
 * This class
 * @author Jose Santillan
 */
public class Checkmate implements EndCondition {

  /**
   * Public method that returns whether the board has reached checkmate
   *
   * @return Whether the board is in checkmate or not
   */
  public boolean isInMate(ChessBoard board, int id) throws EngineException {
    return new Check().isValid(board, id) && new Stalemate().hasNoLegalMoves(board, id);
  }

  public static boolean isInMate2(ChessBoard board, int id) throws EngineException {
    if (!new Check().isValid(board, id) || new Stalemate().hasNoLegalMoves(board, id))
      return false;

    boolean mainCanMove = board.getPieces().stream()
        .anyMatch(p -> p.checkTeam(id) && p.isTargetPiece());
    if (mainCanMove)
      return false;

    //TODO finish implementing checkmate
    return true;
  }

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return new HashMap<>();
  }
}