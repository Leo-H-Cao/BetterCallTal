package oogasalad.GamePlayer.Movement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GameClauses.CheckValidator;
import oogasalad.GamePlayer.GameClauses.CheckmateValidator;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Movement implements MovementInterface{

  private static final Logger LOG = LogManager.getLogger(Movement.class);

  private static final String MOVE_KEY = "move";
  private static final String CAPTURE_KEY = "capture";

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
   * Creates a class representing a basic piece movement with one coordinate provided
   */
  public Movement(Coordinate possibleMove, boolean infinite) {
    this(List.of(possibleMove), infinite);
  }

  /**
   * Moves the piece on fromSquare to finalSquare
   *
   * @param piece to move
   * @param finalSquare end square
   * @param board to move on
   * @return set of updated tiles
   * @throws InvalidMoveException if the piece cannot move to the given square
   * @throws OutsideOfBoardException if the provided square is outside the board
   */
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    ChessTile finalTile = convertCordToTile(finalSquare, board);
    if(getMoves(piece, board).contains(finalTile)) {
      return piece.move(finalTile);
    }
    LOG.warn("Invalid move made");
    throw new InvalidMoveException(piece + ": " + finalSquare);
  }

  /**
   * @param coordinates that the tile is on
   * @param board to get tile on
   * @return corresponding ChessTile to given coordinates
   * @throws OutsideOfBoardException if coord is outside of board
   */
  private ChessTile convertCordToTile(Coordinate coordinates, ChessBoard board)
      throws OutsideOfBoardException {
    try {
      return board.getTile(coordinates);
    } catch (OutsideOfBoardException e) {
      LOG.warn("Coordinate outside of board");
      throw new OutsideOfBoardException(coordinates.toString());
    }
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
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    ChessTile captureTile = convertCordToTile(captureSquare, board);
    if(getCaptures(piece, board).contains(captureTile)) {
      return piece.move(captureTile);
    }
    LOG.warn("Invalid move made");
    throw new InvalidMoveException(piece + ": " + captureSquare);
  }

  /***
   * Gets all moves for a piece, including potential captures
   *
   * @param piece to get moves from
   * @param board to move on
   * @return Map of all moves, with moves mapped to "move" and captures mapped to "captures"
   */
  private Map<String, Set<ChessTile>> getAllMoves(Piece piece, ChessBoard board) {
    Map<String, Set<ChessTile>> allMoves = new HashMap<>();
    allMoves.put(MOVE_KEY, new HashSet<>());
    allMoves.put(CAPTURE_KEY, new HashSet<>());

    Coordinate baseCoordinates = piece.getCoordinates();

    possibleMoves.forEach((delta) -> {
      Stack<ChessTile> moveStack = generateMoveStack(baseCoordinates, delta, board);
      allMoves.get(MOVE_KEY).addAll(moveStack);
      Optional<ChessTile> capTile = moveStack.isEmpty() ? getNextTile(baseCoordinates, delta, board)
          : (infinite ? getNextTile(moveStack.peek().getCoordinates(), delta, board)
              : Optional.of(moveStack.peek()));
      LOG.debug(capTile);
      capTile.ifPresent((t) -> {
        if (piece.isOpposing(t.getPieces())) {
          allMoves.get(CAPTURE_KEY).add(t);
        }
      });
    });
    return allMoves;
  }

  public Map<String, Set<ChessTile>> getLegalMoves(Piece piece, ChessBoard board)
      throws EngineException {
    Map<String, Set<ChessTile>> allMoves = getAllMoves(piece, board);
    for(String moveType : allMoves.keySet()){
      for(ChessTile move : allMoves.get(moveType)){
        ChessBoard deepCopy = board.deepCopy();
        deepCopy.move(piece, move.getCoordinates());
        if(CheckValidator.isInCheck(board, piece.getTeam())){
          allMoves.get(moveType).remove(move);
        }
      }
    }
    return null;
  }

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, CAPTURE_KEY);
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @param board to move on
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, MOVE_KEY);
  }
  
  /***
   * Helper method for getting set from moves map
   * @param key in map
   * @return Set in allMoves map associated with key
   */
  private Set<ChessTile> getFromMovesMap(Piece piece, ChessBoard board, String key) {
    return getAllMoves(piece, board).get(key);
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
      getNextTile(base, delta, board).ifPresent(moveStack::add);
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
      ChessTile currentTile;
      try {
        currentTile = board.getTile(currentCoords);
      } catch(OutsideOfBoardException e) {break;}
      beam.add(currentTile);
        currentCoords = new Coordinate(currentCoords.getRow() + delta.getRow(), currentCoords.getCol() + delta.getCol());
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
