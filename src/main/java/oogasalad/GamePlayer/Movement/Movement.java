package oogasalad.GamePlayer.Movement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Movement {

  private List<Coordinate> possibleMoves;
  private boolean infinite;

  /***
   * Creates a class representing a basic piece movement
   */
  public Movement(List<Coordinate> possibleMoves, boolean infinite) {
    this.possibleMoves = possibleMoves;
    this.infinite = infinite;
  }

  /***
   * Moves the piece on fromSquare to finalSquare
   *
   * @param piece to move
   * @param finalSquare end square
   * @param board to move on
   * @return set of updated tiles
   * @throws InvalidMoveException if the piece cannot move to the given square
   */
  public Set<ChessTile> movePiece(Piece piece, ChessTile finalSquare, ChessBoard board)
      throws InvalidMoveException {
    if(getMoves(piece, board).contains(finalSquare)) {
      return piece.move(finalSquare);
    }
    throw new InvalidMoveException(piece + ": " + finalSquare);
  }

  /***
   * Captures piece on captureSquare
   *
   * @param piece to move
   * @param captureSquare end square
   * @param board to move on
   * @return set of updated tiles
   * @throws InvalidMoveException if the piece cannot move to the given square
   */
  public Set<ChessTile> capturePiece(Piece piece, ChessTile captureSquare, ChessBoard board) throws InvalidMoveException {
    if(getCaptures(piece, board).contains(captureSquare)) {
      return piece.move(captureSquare);
    }
    throw new InvalidMoveException(piece + ": " + captureSquare);
  }

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    Set<ChessTile> captures = new HashSet<>();
    Coordinate baseCoordinates = piece.getCoordinates();
    for (Coordinate delta : possibleMoves) {
      Stack<ChessTile> moveStack = generateMoveStack(baseCoordinates, delta, board);
      if(!moveStack.isEmpty()) {
        ChessTile captureSquare = moveStack.peek();
        if (piece.canCapture(captureSquare.getPieces())) {
          moveStack.add(captureSquare);
        }
      }
    }
    return captures;
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    Set<ChessTile> moves = new HashSet<>();
    Coordinate baseCoordinates = piece.getCoordinates();
    for (Coordinate delta : possibleMoves) {
      moves.addAll(generateMoveStack(baseCoordinates, delta, board));
    }
    return moves;
  }

  /***
   * Generates stack of possible moves, with the furthest being at the top
   *
   * @param base to move from
   * @param delta to change
   * @param board to move on
   * @return stack of possible moves
   */
  private Stack<ChessTile> generateMoveStack(Coordinate base, Coordinate delta, ChessBoard board) {
    Stack<ChessTile> moveStack = new Stack<>();
    if(infinite) {
      moveStack = getMoveBeam(base, delta, board);
    } else{
      Optional<ChessTile> nextTile = getNextTile(base, delta, board);
      if(nextTile.isPresent()) {
        moveStack.add(nextTile.get());
      }
    }
    return moveStack;
  }

  /***
   * Gets all the coordinates shooting out from the central square given a delta
   *
   * @param base coordinate
   * @param delta change in coordinate
   * @return Stack of Chess Tiles extending in that direction up until an occupied square is reached
   */
  private Stack<ChessTile> getMoveBeam(Coordinate base, Coordinate delta, ChessBoard board) {
    Stack<ChessTile> beam = new Stack<>();
    Coordinate currentCoords = new Coordinate(base.getRow() + delta.getRow(), base.getCol() + delta.getCol());
    while (board.inBounds(currentCoords) && isTileEmpty(board, currentCoords)) {
      Optional<ChessTile> nextTile = getNextTile(base, delta, board);
      if(nextTile.isPresent()) {
        beam.add(nextTile.get());
        currentCoords = new Coordinate(currentCoords.getRow() + delta.getRow(), currentCoords.getCol() + delta.getCol());
      } else {
        break;
      }
    }
    return beam;
  }

  /***
   * Gets the next tile given a base and delta
   *
   * @param base to start from
   * @param delta to add to
   * @param board to go on
   * @return next tile
   */
  private Optional<ChessTile> getNextTile(Coordinate base, Coordinate delta, ChessBoard board) {
    try {
      return Optional.of(board.getTile(Coordinate.add(base, delta)));
    } catch(OutsideOfBoardException e) {
      return Optional.empty();
    }
  }

  /***
   * Handles exception from board empty method
   *
   * @param board to check for coordinate on
   * @param coordinate to check for emptiness
   * @return if the coordinate on the board is empty
   */
  private boolean isTileEmpty(ChessBoard board, Coordinate coordinate) {
    try {
      return board.isTileEmpty(coordinate);
    } catch (OutsideOfBoardException e) {
      return false;
    }
  }
}
