package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Board.EndConditions.Stalemate.isStaleMate;

import java.util.Map;
import java.util.Optional;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.ValidStateChecker.Check;

/**
 * This class
 * @author Jose Santillan
 */
public class Checkmate implements EndCondition{

  /**
   * Public method that returns whether the board has reached
   * checkmate
   * @return Whether the board is in checkmate or not
   */
  public static boolean isInMate(ChessBoard board, int id) throws OutsideOfBoardException {
    return new Check().isValid(board, id) && Stalemate.isStaleMate(board, id);
  }

  public static boolean isInMate2(ChessBoard board, int id) throws OutsideOfBoardException {
    if (!new Check().isValid(board, id) || isStaleMate(board, id)) return false;

    boolean mainCanMove = board.getPieces().stream()
        .anyMatch(p -> p.checkTeam(id) && p.isTargetPiece());
    if (mainCanMove) return false;

    //TODO finish implementing checkmate
    return true;
  }

  @Override
  public Optional<Map<Integer, Double>> getScores(ChessBoard board) {
    return Optional.empty();
  }
}