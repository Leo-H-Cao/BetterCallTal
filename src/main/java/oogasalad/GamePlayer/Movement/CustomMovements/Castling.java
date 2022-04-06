package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Castling implements MovementInterface {

  public static final int NO_MOVEMENT_HISTORY_LENGTH = 1;

  private static final Logger LOG = LogManager.getLogger(Castling.class);

  private static final int MAIN_SQUARE_MOVES = 2;
  private static final int SUPPORTER_RELATIVE_SQUARE = 1;

  /***
   * Creates a custom movement that allows the king to castle with a rook
   */

  public Castling() {}

  /**
   * @return places i between 0 inclusive and maxSize not inclusive
   */
  private int adjustToRange(int i, int maxSize) {
    return i<0 ? 0 : (i >= maxSize) ? maxSize - 1 : i;
  }

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

    Piece supporter = getSupporter(piece, finalSquare, board);
    int supporterPieceDelta = piece.getCoordinates().getCol() < supporter.getCoordinates().getCol() ? -SUPPORTER_RELATIVE_SQUARE : SUPPORTER_RELATIVE_SQUARE;

    board.getTile(piece.getCoordinates()).removePiece(piece);
    board.getTile(finalSquare).addPiece(piece);
    piece.updateCoordinates(board.getTile(finalSquare));

    board.getTile(supporter.getCoordinates()).removePiece(supporter);
    board.getTile(Coordinate.of(supporter.getCoordinates().getRow(), supporterPieceDelta + piece.getCoordinates().getCol())).addPiece(supporter);

    return Set.of(board.getTile(piece.getCoordinates()), board.getTile(supporter.getCoordinates()));
  }

  /**
   * Gets the rook to the king based on which direction the king moves
   *
   * @param mainPiece is the king
   * @param mainPieceMove is where the king intends to castle
   * @param board the king is on
   * @return the corresponding rook
   * @throws InvalidMoveException if supporter doesn't exist
   */
  private Piece getSupporter(Piece mainPiece, Coordinate mainPieceMove, ChessBoard board)
      throws InvalidMoveException {
    try {
      return mainPieceMove.getCol() - mainPiece.getCoordinates().getCol() > 0 ? board.getTile(new Coordinate(mainPiece.getCoordinates().getRow(),
          board.getBoardLength() - 1)).getPiece().get() : board.getTile(new Coordinate(mainPiece.getCoordinates().getRow(),
          0)).getPiece().get();
    } catch(Exception e) {
      throw new InvalidMoveException(mainPiece.toString());
    }
  }

  /**
   * @return empty set because no capture possible
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException {
    LOG.warn("Castling does not support captures");
    throw new InvalidMoveException("Castling does not support captures");
  }

  /**
   * @return empty set because no capture possible
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return new HashSet<>();
  }

  /**
   * @param mainPiece is the king
   * @param board to get supporters from
   * @return supporters to main piece (i.e. rooks) if applicable
   */
  private List<Piece> generateSupporters(Piece mainPiece, ChessBoard board) {
    List<Piece> supporters = new ArrayList<>();
    try {
      board.getTile(new Coordinate(mainPiece.getCoordinates().getRow(),
              board.getBoardLength() - 1)).getPieces().stream().filter((p) -> p.onSameTeam(mainPiece))
          .findAny().ifPresent(supporters::add);
      board.getTile(new Coordinate(mainPiece.getCoordinates().getRow(),
              0)).getPieces().stream().filter((p) -> p.onSameTeam(mainPiece)).findAny()
          .ifPresent(supporters::add);
    } catch (OutsideOfBoardException ignored) {}
    return supporters;
  }

  /***
   * Allows an unmoved main piece to castle with a "rook" on the corners horizontal to it
   *
   * @return king-side or queen-side castling move, with piece referencing the king
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {

    if(!piece.isTargetPiece()) return Collections.emptySet();

    Set<ChessTile> possibleSquares = new HashSet<>();
    List<Piece> supporters = generateSupporters(piece, board);

    supporters.stream().filter((supporter) -> canCastle(piece, supporter, board)).forEach( (supporter) -> {
      int mainMovement = piece.getCoordinates().getCol() < supporter.getCoordinates().getCol() ? MAIN_SQUARE_MOVES : -MAIN_SQUARE_MOVES;
      int mainPieceNewCol = adjustToRange(piece.getCoordinates().getCol() + mainMovement,
          board.getBoardLength());
      try {
        possibleSquares.add(board.getTile(Coordinate.of(piece.getCoordinates().getRow(), mainPieceNewCol)));
      }
      catch(OutsideOfBoardException ignored) {}
    });

    return possibleSquares;
  }

  /***
   * @return gets pieces that the given piece can castle with
   */
  private boolean canCastle(Piece piece, Piece supporter, ChessBoard board) {
     return piece.isTargetPiece() && piece.getHistory().size() == NO_MOVEMENT_HISTORY_LENGTH && piece.onSameTeam(supporter) && pathIsClear(piece, supporter, board) && supporter.getHistory().size() == NO_MOVEMENT_HISTORY_LENGTH;
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
}
