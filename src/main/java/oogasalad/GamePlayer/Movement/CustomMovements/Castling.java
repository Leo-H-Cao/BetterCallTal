package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.ArrayList;
import java.util.Collections;
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
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Castling implements CustomMovement{

  private static final Logger LOG = LogManager.getLogger(Castling.class);

  private static final int MAIN_SQUARE_MOVES = 2;
  private static final int SUPPORTER_RELATIVE_SQUARE = 1;

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
      List<Piece> supporters = new ArrayList<>();
      board.getTile(new Coordinate(piece.getCoordinates().getRow(),
          board.getBoardLength() - 1)).getPieces().stream().filter((p) -> p.onSameTeam(piece)).findAny().ifPresent(supporters::add);
      board.getTile(new Coordinate(piece.getCoordinates().getRow(),
          0)).getPieces().stream().filter((p) -> p.onSameTeam(piece)).findAny().ifPresent(supporters::add);

      return supporters.stream().filter((s) -> canCastle(piece, s, board)).map(this::generateMovement).toList();
    } catch(OutsideOfBoardException e) {
      //TODO: maybe don't deal silently with this error
      return List.of();
    }
  }

  /**
   * @return places i between 0 inclusive and maxSize not inclusive
   */
  private int adjustToRange(int i, int maxSize) {
    return i<0 ? 0 : (i >= maxSize) ? maxSize - 1 : i;
  }

  /***
   * @return custom castling movement between piece and supporter on board
   */
  private MovementInterface generateMovement(Piece supporter) {
    return new MovementInterface() {

      /***
       * @return piece moving to finalSquare and supporter moving to the opposite side
       */
      @Override
      public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
          throws InvalidMoveException, OutsideOfBoardException {

        if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
          LOG.warn("Illegal castling move attempted");
          throw new InvalidMoveException(finalSquare.toString());
        }
        Set<ChessTile> updatedSquares = new HashSet<>(piece.move(
            board.getTile(finalSquare)));

        int supporterPieceNewCol = piece.getCoordinates().getCol() < supporter.getCoordinates().getCol() ? -SUPPORTER_RELATIVE_SQUARE : SUPPORTER_RELATIVE_SQUARE;
        updatedSquares.addAll(supporter.move(board.getTile(Coordinate.of(supporter.getCoordinates().getRow(), supporterPieceNewCol))));

        return updatedSquares;
      }

      /**
       * @return empty set because no capture possible
       */
      @Override
      public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board) {
        return new HashSet<>();
      }

      /**
       * @return empty set because no capture possible
       */
      @Override
      public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
        return new HashSet<>();
      }

      /***
       * @return king-side or queen-side castling move, with piece referencing the king
       */
      @Override
      public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
        int mainMovement = piece.getCoordinates().getCol() < supporter.getCoordinates().getCol() ? MAIN_SQUARE_MOVES : -MAIN_SQUARE_MOVES;
        int mainPieceNewCol = adjustToRange(piece.getCoordinates().getCol() + mainMovement,
            board.getBoardLength());
        try {
          return Set.of(board.getTile(Coordinate.of(piece.getCoordinates().getRow(), mainPieceNewCol)));
        }
        catch(OutsideOfBoardException e) {
          return Collections.emptySet();
        }
      }
    };
  }

  /***
   * @return gets pieces that the given piece can castle with
   */
  private boolean canCastle(Piece piece, Piece supporter, ChessBoard board) {
     return piece.isTargetPiece() && piece.getHistory().isEmpty() && piece.onSameTeam(supporter) && pathIsClear(piece, supporter, board) && supporter.getHistory().isEmpty();
  }

  /***
   * @return if there's a clear path between the main piece and supporter piece on the board
   */
  private boolean pathIsClear(Piece main, Piece supporter, ChessBoard board) {
    if(main.getCoordinates().getRow() != supporter.getCoordinates().getRow()) return false;

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
