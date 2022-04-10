package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnPassant implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(EnPassant.class);
  private static final int EP_DISTANCE = 1;
  private static final int EP_ROW_SUBTRACT = 3;
  private static final int ONE_MOVE_HISTORY_LENGTH = 2;

  /***
   * Creates a movement object that represents en passant: if a piece of the same name has just
   * moved and only moved once and is on the (height-3) rank relative to the piece doing en passant,
   * then the piece can capture the other piece by moving diagonally one square behind it
   */
  public EnPassant() {}

  /***
   * No applicable moves
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("En passant does not support move, only capture");
    throw new InvalidMoveException("En passant does not support move, only capture");
  }

  /***
   * @param piece to move
   * @param captureSquare end square
   * @param board to move on
   * @return square of piece captured, original piece square, and square piece moved to
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getCaptures(piece, board).contains(board.getTile(captureSquare))) {
      LOG.warn("Illegal en passant move attempted");
      throw new InvalidMoveException(captureSquare.toString());
    }
    ChessTile originalSquare = board.getTile(piece.getCoordinates());
    ChessTile removalSquare = board.getTile(Coordinate.of(piece.getCoordinates().getRow(),
        captureSquare.getCol()));
    piece.updateCoordinates(board.getTile(captureSquare), board);
    removalSquare.clearPieces();
    return Set.of(originalSquare, removalSquare, board.getTile(piece.getCoordinates()));
  }

  /***
   * @param piece to get en passant captures from
   * @param board to move on
   * @return possible en passants
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return getPossibleEnPassantTiles(piece, board);
  }

  /***
   * En passant can only happen on the (boardHeight - 3) row
   *
   * @return row for possible en passant
   */
  private int getEpRow(Piece piece, int boardHeight) {
    if(movingUp(piece)) {
      LOG.debug("En passant row for current piece (1): " + EP_ROW_SUBTRACT);
      return EP_ROW_SUBTRACT;
    } else {
      LOG.debug("En passant row for current piece (2): " + (boardHeight - EP_ROW_SUBTRACT - 1));
      return (boardHeight - EP_ROW_SUBTRACT) - 1;
    }
  }

  /***
   * @param piece to check
   * @return if the given piece is moving up the board
   */
  private boolean movingUp(Piece piece) {
    //FIXME: getRow() instead of get col for some reason i have no idea why this happens
    return piece.getRelativeMoveCoords().stream().anyMatch((c) -> c.getRow() < 0);
  }

  /***
   * Finds tiles that are possible to capture with en passant - there is a piece on the square,
   * it has just moved, it has the same name as the en passant exacter, and it only has moved once
   * Due ot hypothetical moves, special case for history.size() == 0
   *
   * @param enPassantExacter to get en passant tiles from
   * @param board to search on
   * @return tiles where en passant is possible
   */
  private Set<ChessTile> getPossibleEnPassantTiles(Piece enPassantExacter, ChessBoard board) {
    if(getEpRow(enPassantExacter, board.getBoardHeight()) != enPassantExacter.getCoordinates().getRow()) {return Collections.emptySet();}
    Coordinate base = enPassantExacter.getCoordinates();
    List<ChessTile> possibleCaptures = new ArrayList<>();
    try {possibleCaptures.add(board.getTile(Coordinate.of(base.getRow(), base.getCol() - EP_DISTANCE)));} catch (Exception ignored) {}
    try {possibleCaptures.add(board.getTile(Coordinate.of(base.getRow(), base.getCol() + EP_DISTANCE)));} catch (Exception ignored) {}
    LOG.debug("Preliminary capture squares: " + possibleCaptures);
    Set<ChessTile> capSquares =  possibleCaptures.stream().filter((t) -> t.getPiece().isPresent() &&
        (board.getHistory().size() == 0 || board.getHistory().get(board.getHistory().size() - 1)
            .movedPieces().contains(t.getPiece().get())) &&
        t.getPiece().get().getName().equals(enPassantExacter.getName()) &&
        (board.getHistory().size() == 0 || t.getPiece().get().getHistory().size() == ONE_MOVE_HISTORY_LENGTH)).collect(Collectors.toSet());
    LOG.debug("Actual capture squares: " + capSquares);
    return capSquares.stream().map((c) -> {
      try {
        return board.getTile(Coordinate.of(c.getCoordinates().getRow() + (movingUp(enPassantExacter) ? -1 : 1), c.getCoordinates().getCol()));
      } catch (OutsideOfBoardException ignored) {
        return null;
      }
    }).collect(Collectors.toSet());
  }

  /***
   * @return nothing, no applicable moves
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }


  /***
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
