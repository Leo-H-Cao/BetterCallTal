package oogasalad.GamePlayer.GamePiece;

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

public class MovementHandler {

  private static final Logger LOG = LogManager.getLogger(MovementHandler.class);

  private final List<MovementInterface> movements;
  private final List<MovementInterface> captures;
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
      throws InvalidMoveException, OutsideOfBoardException {

    if (!getMoves(piece, board).contains(finalSquare)) {
      throw new InvalidMoveException("Tile is not a valid move!");
    }

    //TODO: need to know whether a move is a capture or not for OIM, things like atomic
    Set<ChessTile> updatedSquares = new HashSet<>(findMovements(piece, finalSquare, board));
    updatedSquares.addAll(findCaptures(piece, finalSquare, board));

    movementModifiers.forEach((mm) -> updatedSquares.addAll(mm.updateMovement(piece, finalSquare, board)));
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
    movements.stream().filter((m) -> m.getMoves(piece, board).contains(finalSquare)).findFirst().ifPresent((m) ->
    {try{updatedSquares.addAll(m.movePiece(piece, finalSquare.getCoordinates(), board));} catch (Exception ignored){}});
    LOG.debug("Updated squares: " + updatedSquares);
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
    captures.stream().filter((m) -> m.getCaptures(piece, board).contains(captureSquare)).findFirst().ifPresent((m) ->
    {try{updatedSquares.addAll(m.capturePiece(piece, captureSquare.getCoordinates(), board));} catch (Exception ignored){}});
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
    movements.forEach((m) -> allMoves.addAll(m.getMoves(piece, board)));
    captures.forEach((m) -> allMoves.addAll(m.getCaptures(piece, board)));

    LOG.debug(String.format("%s has the following moves: %s", piece.getName(), allMoves));
    return allMoves;
  }

  /***
   * @param coordinate to check for captures
   * @return if this piece can capture a piece on the given coordinate
   */
  private boolean validCapture(Piece piece, Coordinate coordinate, ChessBoard board) {
    //TODO MOVEMENTS IS A PLACEHOLDER, MUST IMPLEMENT CAPTURES AS IT IS NULL WHEN THE PIECE IS FIRST INITIALED AS WELL AS THE GETMOVES METHOD
    return movements.stream().map(move -> move.getMoves(piece, board))
        .collect(Collectors.toSet())
        .stream().flatMap(Set::stream)
        .collect(Collectors.toSet()).stream()
        .anyMatch(tile -> tile.getCoordinates().equals(coordinate));
  }

  /***
   * @param coordinates to check for captures
   * @return if this piece can capture a piece on the given coordinates
   */
  public boolean validCapture(Piece piece, List<Coordinate> coordinates, ChessBoard board) {
    return coordinates.stream().anyMatch((c) -> validCapture(piece, c, board));
  }

  /***
   * @param piece to do the capture
   * @param opponent to capture
   * @return if this piece can capture another piece
   */
  public boolean canCapture(Piece piece, Piece opponent, ChessBoard board) {
    boolean sameTeam = piece.checkTeam(opponent.getTeam());

    //TODO MOVEMENTS IS A PLACEHOLDER, MUST IMPLEMENT CAPTURES AS IT IS NULL WHEN THE PIECE IS FIRST INITIALED
    boolean canCap = movements.stream()
        .map(capture -> capture.getCaptures(piece, board))
        .flatMap(Set::stream)
        .map(ChessTile::getCoordinates)
        .anyMatch(coords -> coords.equals(piece.getCoordinates()));

    return !sameTeam && canCap;
  }

  /***
   * @param opponentPieces to potentially capture
   * @return if this piece can capture any piece in a list of pieces
   */
  public boolean canCapture(Piece piece, List<Piece> opponentPieces, ChessBoard board) {
    return opponentPieces.stream().anyMatch((op) -> canCapture(piece, op, board));
  }

  /***
   * @return relative coordinates for all regular moves
   */
  public List<Coordinate> getRelativeMoveCoords() {
    return movements.stream().flatMap((m) -> m.getRelativeCoords().stream()).toList();
  }
}
