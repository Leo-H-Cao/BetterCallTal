package oogasalad.GamePlayer.GameClauses;

import static oogasalad.GamePlayer.GameClauses.CheckValidator.isInCheck;
import static oogasalad.GamePlayer.GameClauses.StalemateValidator.isStaleMate;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

/**
 * This class
 * @author Jose Santillan
 */
public class CheckmateValidator implements MovementModifier {

  /**
   * Public method that returns whether the board has reached
   * checkmate
   * @return Whether the board is in checkmate or not
   */
  public static boolean isInMate2(ChessBoard board, int id) throws OutsideOfBoardException {

    return CheckValidator.isInCheck(board, id) && StalemateValidator.isStaleMate(board, id);
  }

  public static boolean isInMate(ChessBoard board, int id) throws EngineException {
    boolean check = !isInCheck(board, id);
    if (!isInCheck(board, id) || isStaleMate(board, id)) return false;

    Set<Piece> friendlyPieces = StalemateValidator.friendlyPieces(board, id);

    for (Piece p : friendlyPieces) {
      Set<ChessTile> moves = p.getMoves();
      for (ChessTile move : p.getMoves()) {
        ChessBoard tempBoard = board.deepCopy();
        tempBoard.move(p, move.getCoordinates());
        boolean anotherCheck = !isInCheck(tempBoard, id);
        if (!CheckValidator.isInCheck(tempBoard, id)) return false;
      }
    }

    return true;
  }

  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessTile finalTile, ChessBoard board) {
    return null;
  }
}