package oogasalad.GamePlayer.Movement;

import static oogasalad.GamePlayer.util.ClassCreator.createInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Abstraction of movements that follow the "basic" pattern: a collection of relative coordinates
 * and infinite/not infinite extension.
 *
 * @author Vincent Chen
 */
public abstract class BasicMovementAbstract implements MovementInterface{

  private static final Logger LOG = LogManager.getLogger(BasicMovementAbstract.class);

  protected static final String MOVE_KEY = "move";
  protected static final String CAPTURE_KEY = "capture";


  private List<Coordinate> possibleMoves;
  private boolean infinite;

  /***
   * Creates a class representing a basic piece movement
   */
  protected BasicMovementAbstract(List<Coordinate> possibleMoves, boolean infinite) {
    this.possibleMoves = possibleMoves;
    this.infinite = infinite;
  }

  /***
   * Creates a class representing a basic piece movement with one coordinate provided
   */
  protected BasicMovementAbstract(Coordinate possibleMove, boolean infinite) {
    this(List.of(possibleMove), infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  protected BasicMovementAbstract(){
    this.possibleMoves = new ArrayList<>();
    this.infinite = false;
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
      Set<ChessTile> updatedSquares = new HashSet<>(Set.of(board.getTile(piece.getCoordinates()), finalTile));
      updatedSquares.addAll(piece.updateCoordinates(finalTile, board));
      return updatedSquares;
    }

    LOG.warn(String.format("Invalid move made: (%d, %d)", finalSquare.getRow(), finalSquare.getCol()));
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

    possibleMoves.forEach(delta -> {
      Stack<ChessTile> moveStack = generateMoveStack(baseCoordinates, delta, board);
      allMoves.get(MOVE_KEY).addAll(moveStack);
      Optional<ChessTile> capTile = moveStack.isEmpty() ? getNextTile(baseCoordinates, delta, board)
          : (infinite ? getNextTile(moveStack.peek().getCoordinates(), delta, board)
              : Optional.of(moveStack.peek()));
      capTile.ifPresent(t -> {
        if (piece.isOpposing(t.getPieces(), board)) {
          allMoves.get(CAPTURE_KEY).add(t);
        }
      });
    });
    return allMoves;
  }

  /***
   * @return relative coordinates
   */
  public List<Coordinate> getRelativeCoords() {
    return possibleMoves;
  }

  /***
   * Helper method for getting set from moves map
   * @param key in map
   * @return Set in allMoves map associated with key
   */
  protected Set<ChessTile> getFromMovesMap(Piece piece, ChessBoard board, String key) {
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
      getNextTile(base, delta, board).filter(t -> t.getPieces().isEmpty()).ifPresent(moveStack::add);
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
  boolean isTileEmpty(ChessBoard board, Coordinate coordinate) {
    try {
      return board.isTileEmpty(coordinate);
    } catch (OutsideOfBoardException e) {
      return false;
    }
  }

  /**
   * @return if infinite
   */
  public boolean isInfinite() {
    return infinite;
  }

  /***
   * @return String of all relative coordinates
   */
  public String toString() {
    return possibleMoves.toString() + ": " + infinite;
  }

  /**
   * Utility function for inverting movements
   *
   * @param movements to invert
   * @return inverted movements
   */
  public static Set<MovementInterface> invertMovements(Set<MovementInterface> movements) {
    Set<MovementInterface> inverted = new HashSet<>();
    movements.forEach(mi -> {
      if(mi.getClass().isAssignableFrom(BasicMovementAbstract.class)) {
        BasicMovementAbstract movement = (BasicMovementAbstract) mi;
        List<Coordinate> invertedCoords = new ArrayList<>();
        movement.getRelativeCoords().forEach(c -> {
          Coordinate invertedCoord = Coordinate.of(-c.getRow(), -c.getCol());
          invertedCoords.add(invertedCoord);
          LOG.debug(String.format("Movement inverted: %s", invertedCoord));
        });
        try {
          inverted.add((BasicMovementAbstract) createInstance(
              mi.getClass().getName(), new Object[]{invertedCoords, movement.infinite}));
        } catch (IOException e) {
          inverted.add(mi);
        }
      } else {
        inverted.add(mi);
      }
    });
    return inverted;
  }

  /***
   * @return equal if both movements have the same possible moves and infinite is the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicMovementAbstract movement = (BasicMovementAbstract) o;
    return infinite == movement.infinite && Objects.equals(possibleMoves,
        movement.possibleMoves);
  }

  /***
   * @return hash of this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(possibleMoves, infinite);
  }
}
