package oogasalad.GamePlayer.Movement.CustomMovements;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Checkers style capturing
 *
 * @author Vincent Chen
 */
public class CheckersCapture implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(CheckersCapture.class);

  private Map<Coordinate, Set<ChessTile>> capturePaths = new HashMap<>();
  private Set<Coordinate> visited = new HashSet<>();

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public CheckersCapture() {
    super();
  }

  /**
   * Captures piece by jumping over it, multiple jumps possible if applicable
   *
   * @return set of updated chess tiles
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if (!getMoves(piece, board).contains(board.getTile(captureSquare))) {
      LOG.warn("Illegal checkers capture move attempted");
      throw new InvalidMoveException(captureSquare.toString());
    }

    Set<ChessTile> updatedSquares = new HashSet<>(List.of(board.getTile(captureSquare),
        board.getTile(piece.getCoordinates())));
    LOG.debug(String.format("Capture squares : %s", capturePaths.keySet()));
    capturePaths.get(captureSquare).forEach(t -> {
      t.clearPieces();
      updatedSquares.add(t);
    });
    piece.updateCoordinates(board.getTile(captureSquare), board);
    LOG.debug(String.format("Updated squares: %s", updatedSquares));
    return updatedSquares;
  }

  /**
   * @return possible captures by jumping over pieces
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    capturePaths = new HashMap<>();
    visited = new HashSet<>();

    generateCapturesTree(board, piece.getCoordinates(), piece.getRelativeMoveCoords(),
        piece.getTeam(), new HashSet<>(), new HashSet<>());
    LOG.debug(String.format("All end squares: %s", capturePaths.keySet()));
    return capturePaths.keySet().stream().map(c -> {
      try {
        return board.getTile(c);
      } catch (OutsideOfBoardException e) {
        return null;
      }
    }).collect(Collectors.toSet());
  }

  /***
   * Generates capture tree by branching off from starting position outward while captures are
   * possible
   *
   * @param board to get captures on
   * @param startCoordinate of piece
   * @param directions that the piece can move in
   * @param team of piece
   * @param currentJumps is the list of current captures
   *
   * @return all capture squares
   */
  private void generateCapturesTree(ChessBoard board, Coordinate startCoordinate,
      List<Coordinate> directions, int team,
      Set<ChessTile> currentJumps, Set<ChessTile> capTiles) {
    directions.stream().filter(d -> canCapture(board, startCoordinate, d, team) &&
        !visited.contains(Coordinate.add(d, startCoordinate))).forEach(d -> {
      Coordinate capTile = Coordinate.add(d, startCoordinate);
      Coordinate jumpTile = Coordinate.add(d, capTile);
      Set<ChessTile> copyJump = new HashSet<>(currentJumps);
      Set<ChessTile> copyCap = new HashSet<>(capTiles);

      visited.add(capTile);

      try {
        copyJump.add(board.getTile(jumpTile));
        copyCap.add(board.getTile(capTile));
        LOG.debug(
            String.format("End tile, capture path calculation: %s, %s", startCoordinate, capTiles));
        capturePaths.put(jumpTile, copyCap);
      } catch (OutsideOfBoardException ignored) {
        LOG.debug("Capture out of bounds");
      }
      LOG.debug(String.format("Current capture tree jumps: %s", copyJump));
      LOG.debug(String.format("Current capture tree caps: %s", copyCap));
      generateCapturesTree(board, jumpTile, directions, team,
          copyJump, copyCap);
    });
  }

  /**
   * Returns if a capture is possible based on checkers rules
   *
   * @param board     to jump on
   * @param startCord starting square
   * @param direction to jump in
   * @return if a capture is possible
   */
  private boolean canCapture(ChessBoard board, Coordinate startCord, Coordinate direction,
      int team) {

    Coordinate passOverCord = Coordinate.add(direction, startCord);
    Coordinate finalCord = Coordinate.add(direction, passOverCord);

    if (!board.inBounds(passOverCord) || !board.inBounds(finalCord)) {
      return false;
    }

    try {
      return board.getTile(passOverCord).getPiece().isPresent() &&
          board.isOpposing(board.getTile(passOverCord), team) &&
          board.getTile(finalCord).getPiece().isEmpty();
    } catch (OutsideOfBoardException e) {
      return false;
    }
  }

  /***
   * @return equals always if o is of the same class
   */
  @Override
  public boolean equals(Object o) {
    return o != null && getClass() == o.getClass();
  }

  /***
   * @return hash of this object
   */
  @Override
  public int hashCode() {
    return Objects.hash();
  }
}
