package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementInterface;

public class Castling implements CustomMovement{

  /***
   * Creates a custom movement that allows the king to castle with a rook
   */

  public Castling() {}

  /***
   * Allows an unmoved main piece to castle with a "rook" on the corners horizontal to it
   *
   * @param piece that is moving
   * @param board referenced
   * @return list of castling moves if possible
   */
  @Override
  public List<MovementInterface> getMovements(Piece piece, ChessBoard board) {
    if(!piece.isTargetPiece()) return List.of();

    try {
      List<MovementInterface> castleMovements = new ArrayList<>();

      Piece rightSupporter = board.getTile(new Coordinate(piece.getCoordinates().getRow(),
          board.getBoardLength() - 1)).getPiece().orElse(null);
      Piece leftSupporter = board.getTile(new Coordinate(piece.getCoordinates().getRow(),
          0)).getPiece().orElse(null);

      if(canCastle(piece, rightSupporter, board)) {
        castleMovements.add(generateMovement(piece, rightSupporter, board));
      }
      if(canCastle(piece, leftSupporter, board)) {
        castleMovements.add(generateMovement(piece, rightSupporter, board));
      }
      return castleMovements;
    } catch(OutsideOfBoardException e) {
      //TODO: maybe don't deal silently with this error
      return List.of();
    }
  }

  /***
   * @return custom castling movement between piece and supporter on board
   */
  private MovementInterface generateMovement(Piece piece, Piece supporter, ChessBoard board) {
    return new MovementInterface() {
      @Override
      public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
          throws InvalidMoveException, OutsideOfBoardException {
        return null;
      }

      @Override
      public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board) {
        return new HashSet<>();
      }

      @Override
      public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
        return new HashSet<>();
      }

      @Override
      public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
        return null;
      }
    };
  }

  /***
   * @return gets pieces that the given piece can castle with
   */
  private boolean canCastle(Piece piece, Piece supporter, ChessBoard board) {
     return piece.isTargetPiece() && piece.getHistory().isEmpty() && clearPath(piece, supporter, board) && supporter.getHistory().isEmpty();
  }

  /***
   * @return if there's a clear path between the main piece and supporter piece on the board
   */
  private boolean clearPath(Piece main, Piece supporter, ChessBoard board) {
    if(supporter == null) return false;

    int startCol = Math.min(main.getCoordinates().getCol(), supporter.getCoordinates().getCol()) + 1;
    int endCol = Math.max(main.getCoordinates().getCol(), supporter.getCoordinates().getCol()) - 1;
    return IntStream.range(startCol, endCol).noneMatch((c) -> {
      try {
        return !board.getTile(new Coordinate(main.getCoordinates().getRow(), c)).getPieces().isEmpty();
      } catch (OutsideOfBoardException e) {
        return true;
      }
    });
  }

  /***
   * @return nothing, no applicable captures for castling
   */
  @Override
  public List<MovementInterface> getCaptures(Piece piece, ChessBoard board) {
    return List.of();
  }
}
