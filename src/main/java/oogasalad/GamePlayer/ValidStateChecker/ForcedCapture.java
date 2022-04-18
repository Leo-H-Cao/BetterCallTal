package oogasalad.GamePlayer.ValidStateChecker;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class ForcedCapture implements ValidStateChecker {

  /***
   * If a capture exists on the board, only other captures are valid.
   *
   * @param board to check
   * @param piece to check
   * @param move to check
   * @return true if no captures or if the move is a capture, false otherwise
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece, ChessTile move) throws EngineException {
    if(move.getPiece().isPresent()) return true;

    return captureExists(piece.getTeam(), board);
  }

  /***
   * Returns if the given team has a capture on the board
   *
   * @param team that is moving
   * @param board to move on
   * @return read above
   */
  public boolean captureExists(int team, ChessBoard board) {
    return board.getPieces().stream().filter(p -> p.getTeam() == team).anyMatch(p -> {
      Set<ChessTile> moves = p.getMoves(board);
      return moves.stream().anyMatch(t -> t.getPiece().isPresent());
    });
  }
}
