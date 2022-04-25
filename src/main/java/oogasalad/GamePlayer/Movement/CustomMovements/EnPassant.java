package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates a movement object that represents en passant: if a piece of the same name has just
 * moved and only moved once and is on the (height-3) rank relative to the piece doing en passant,
 * then the piece can capture the other piece by moving diagonally one square behind it
 *
 * @author Vincent Chen
 */
public class EnPassant implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(EnPassant.class);

  public static final String EP_CONFIG_FILE_HEADER = "doc/GameEngineResources/Other/";
  public static final String EP_DEFAULT_FILE = "EnPassantRowVal";

  private static final int EP_DEFAULT_VAL = 3;
  private static final int EP_DISTANCE = 1;
  private static final int ONE_MOVE_HISTORY_LENGTH = 2;

  private int epRowSubtract;

  /***
   * Constructs EnPassant with default file
   */
  public EnPassant() {
    this(EP_DEFAULT_FILE);
  }

  /***
   * Constructs EnPassant with provided config file
   *
   * @param configFile to read
   */
  public EnPassant(String configFile) {
    epRowSubtract = FileReader.readOneInt(EP_CONFIG_FILE_HEADER + configFile,
        EP_DEFAULT_VAL);
  }

  /**
   * No applicable moves
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("En passant does not support move, only capture");
    throw new InvalidMoveException("En passant does not support move, only capture");
  }

  /**
   * @param piece         to move
   * @param captureSquare end square
   * @param board         to move on
   * @return square of piece captured, original piece square, and square piece moved to
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if (!getCaptures(piece, board).contains(board.getTile(captureSquare))) {
      LOG.warn("Illegal en passant move attempted");
      throw new InvalidMoveException(captureSquare.toString());
    }
    Set<ChessTile> updatedSquares = new HashSet<>(List.of(board.getTile(piece.getCoordinates())));
    ChessTile removalSquare = board.getTile(Coordinate.of(piece.getCoordinates().getRow(),
        captureSquare.getCol()));
    updatedSquares.add(removalSquare);
    updatedSquares.addAll(piece.updateCoordinates(board.getTile(captureSquare), board));
    updatedSquares.add(board.getTile(piece.getCoordinates()));
    removalSquare.clearPieces();
    return updatedSquares;
  }

  /**
   * @param piece to get en passant captures from
   * @param board to move on
   * @return possible en passants
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return getPossibleEnPassantTiles(piece, board);
  }

  /**
   * En passant can only happen on the (boardHeight - 3) row
   *
   * @return row for possible en passant
   */
  private int getEpRow(Piece piece, int boardHeight) {
    if (movingUp(piece)) {
      return epRowSubtract;
    } else {
      return (boardHeight - epRowSubtract) - 1;
    }
  }

  /**
   * @param piece to check
   * @return if the given piece is moving up the board
   */
  private boolean movingUp(Piece piece) {
    return piece.getRelativeMoveCoords().stream().anyMatch(c -> c.getRow() < 0);
  }

  /**
   * Finds tiles that are possible to capture with en passant - there is a piece on the square, it
   * has just moved, it has the same name as the en passant exacter, and it only has moved once Due
   * ot hypothetical moves, special case for history.size() == 0
   *
   * @param enPassantExacter to get en passant tiles from
   * @param board            to search on
   * @return tiles where en passant is possible
   */
  private Set<ChessTile> getPossibleEnPassantTiles(Piece enPassantExacter, ChessBoard board) {
    if (getEpRow(enPassantExacter, board.getBoardHeight()) != enPassantExacter.getCoordinates()
        .getRow()) {
      return Collections.emptySet();
    }
    Coordinate base = enPassantExacter.getCoordinates();
    List<ChessTile> possibleCaptures = new ArrayList<>();
    try {
      possibleCaptures.add(
          board.getTile(Coordinate.of(base.getRow(), base.getCol() - EP_DISTANCE)));
    } catch (Exception ignored) {}
    try {
      possibleCaptures.add(
          board.getTile(Coordinate.of(base.getRow(), base.getCol() + EP_DISTANCE)));
    } catch (Exception ignored) {}
    Set<ChessTile> capSquares = possibleCaptures.stream().filter(t -> t.getPiece().isPresent() &&
            (board.getHistory().isEmpty() || board.getHistory().get(board.getHistory().size() - 1)
                .movedPieces().contains(t.getPiece().get())) &&
            t.getPiece().get().getName().equals(enPassantExacter.getName()) &&
            (board.getHistory().isEmpty()
                || t.getPiece().get().getHistory().size() == ONE_MOVE_HISTORY_LENGTH))
        .collect(Collectors.toSet());
    return capSquares.stream().map(c -> {
      try {
        return board.getTile(
            Coordinate.of(c.getCoordinates().getRow() + (movingUp(enPassantExacter) ? -1 : 1),
                c.getCoordinates().getCol()));
      } catch (OutsideOfBoardException e) {return null;}
    }).collect(Collectors.toSet());
  }

  /**
   * @return nothing, no applicable moves
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }


  /**
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
