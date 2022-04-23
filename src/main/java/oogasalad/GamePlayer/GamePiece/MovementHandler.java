package oogasalad.GamePlayer.GamePiece;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Class that handles a piece's movements
 *
 * @author Vincent Chen
 */
public class MovementHandler {

  private static final Logger LOG = LogManager.getLogger(MovementHandler.class);

  private List<MovementInterface> movements;
  private List<MovementInterface> captures;
  private final List<MovementModifier> movementModifiers;

  /***
   * @param movements list of movements
   * @param captures list of captures
   * @param movementModifiers list of modifiers to movement
   * Creates an object that handles movements, captures for pieces
   */
  public MovementHandler(List<MovementInterface> movements, List<MovementInterface> captures, List<MovementModifier> movementModifiers) {
    this.movements = movements;
    this.captures = captures;
    this.movementModifiers = movementModifiers;
  }

  /***
   * Moves this piece to a given square
   *
   * @param piece to move
   * @param finalSquare to move the piece to
   * @param board to move on
   * @return set of updated chess tiles
   */
  public Set<ChessTile> move(Piece piece, ChessTile finalSquare, ChessBoard board)
      throws InvalidMoveException {

    if (!getMoves(piece, board).contains(finalSquare)) {
      throw new InvalidMoveException("Tile is not a valid move!");
    }

    Set<ChessTile> updatedSquares = new HashSet<>(findMovements(piece, finalSquare, board));
    updatedSquares.addAll(findCaptures(piece, finalSquare, board));

    movementModifiers.forEach(mm -> updatedSquares.addAll(mm.updateMovement(piece, board)));
    return updatedSquares;
  }

  /***
   * @param finalSquare to move to
   * @param piece to find movements for
   * @param board to search on
   * @return finds if a possible movement goes to the finalSquare and makes the move, returning updated squares
   */
  private Set<ChessTile> findMovements(Piece piece, ChessTile finalSquare, ChessBoard board) {
    Set<ChessTile> updatedSquares = new HashSet<>();
    movements.stream().filter(m -> m.getMoves(piece, board).contains(finalSquare)).findFirst().ifPresent((m) ->
    {try{updatedSquares.addAll(m.movePiece(piece, finalSquare.getCoordinates(), board));} catch (InvalidMoveException | OutsideOfBoardException ignored){}});
    LOG.debug(String.format("Updated squares: %s", updatedSquares));
    return updatedSquares;
  }

  /***
   * @param captureSquare to move to
   * @param piece to get captures for
   * @param board to search on
   * @return finds if a possible movement goes to the captureSquare and makes the move, returning updated squares
   */
  private Set<ChessTile> findCaptures(Piece piece, ChessTile captureSquare, ChessBoard board) {
    Set<ChessTile> updatedSquares = new HashSet<>();
    captures.stream().filter(m -> m.getCaptures(piece, board).contains(captureSquare)).findFirst().ifPresent((m) ->
    {try{
      updatedSquares.addAll(m.capturePiece(piece, captureSquare.getCoordinates(), board));
      updatedSquares.addAll(piece.runInteractionModifiers(board));
    } catch (InvalidMoveException | OutsideOfBoardException ignored){}});
    return updatedSquares;
  }

  /***
   * Gets all possible moves the piece can make
   * @param piece to get moves for
   * @param board to move on
   * @return set of possible moves
   */
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    Set<ChessTile> allMoves = new HashSet<>();
    movements.forEach(m -> allMoves.addAll(m.getMoves(piece, board)));
    captures.forEach(m -> allMoves.addAll(m.getCaptures(piece, board)));

    return allMoves;
  }

  /***
   * @return relative coordinates for all regular moves
   */
  public List<MovementInterface> getMovements() {
    return /*getRelativeMoveCoordsFromList(*/movements;
  }

  /***
   * @return relative coordinates for movements
   */
  public List<Coordinate> getRelativeMoveCoords() {
    return getRelativeMoveCoordsFromList(movements);
  }

  /**
   * @return relative coordinates list for given movementList
   */
  private List<Coordinate> getRelativeMoveCoordsFromList(List<MovementInterface> movementList) {
    return movementList.stream().flatMap(m -> m.getRelativeCoords().stream()).collect(Collectors.toList());
  }

  /***
   * @return relative coordinates for captures
   */
  public List<Coordinate> getRelativeCapCoords() {
    return getRelativeMoveCoordsFromList(captures);
  }

  /***
   * @return relative coordinates for all regular moves and captures
   */
  public List<MovementInterface> getCaptures() {
    return /*getRelativeMoveCoordsFromList(*/captures;
  }

  /***
   * @param newMovements new coordinates for all regular moves to set
   * @param newCaptures new coordinates for all regular captures to set
   */
  public void setNewMovements(List<MovementInterface> newMovements, List<MovementInterface> newCaptures) {
    this.movements = newMovements;
    this.captures = newCaptures;
  }

  /***
   * @param newMovements new coordinates for all regular moves to set
   * @param newCaptures new coordinates for all regular captures to set
   */
  public void addNewMovements(List<MovementInterface> newMovements, List<MovementInterface> newCaptures) {
    this.movements.addAll(newMovements);
    this.captures.addAll(newCaptures);
  }
}
