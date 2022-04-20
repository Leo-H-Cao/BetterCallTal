package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * Custom move that snakes around the board based on given directions
 *
 * @author Vincent Chen
 */
public class Snake implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(Snake.class);
  private static final String SNAKE_FILE_PATH_HEADER = "doc/GameEngineResources/Other/snakeFiles";
  private static final String DEFAULT_SNAKE_FILE = "MoveUpSnake1";

  private static final Coordinate DEFAULT_PMD = Coordinate.of(0, 1);
  private static final Coordinate DEFAULT_SMD = Coordinate.of(-1, 0);
  private static final Coordinate DEFAULT_PM = Coordinate.of(1, -1);

  private Coordinate primaryMoveDirection;
  private Coordinate secondaryMoveDirection;
  private Coordinate primaryMultiplier;
  private boolean enactedSecondaryMove;

  /***
   * Creates Snake with default config file
   */
  public Snake() {
    this(DEFAULT_SNAKE_FILE);
  }

  /***
   * Creates Snake with given config file
   *
   * @param configFile to read
   */
  public Snake(String configFile) {
    configFile = SNAKE_FILE_PATH_HEADER + configFile + JSON_EXTENSION;
    try {
      String content = new String(Files.readAllBytes(
          Path.of(configFile)));
      JSONObject data = new JSONObject(content);

      primaryMoveDirection = getCoordinateFromJSONArray(data.getJSONArray("primary"));
      secondaryMoveDirection = getCoordinateFromJSONArray(data.getJSONArray("secondary"));
      primaryMultiplier = getCoordinateFromJSONArray(data.getJSONArray("multiplier"));

    } catch (IOException e) {
      LOG.debug(String.format("Snake file read failed: %s", configFile));
      primaryMoveDirection = DEFAULT_PMD;
      secondaryMoveDirection = DEFAULT_SMD;
      primaryMultiplier = DEFAULT_PM;
    }
  }

  /***
   * Converts a JSON array into a Coordinate object
   *
   * @param coordinateArray to read
   * @return Coordinate based on JSON array
   */
  private Coordinate getCoordinateFromJSONArray(JSONArray coordinateArray) {
    return Coordinate.of(coordinateArray.getInt(0), coordinateArray.getInt(1));
  }

  /***
   * @return original tile and tile moved to
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
      LOG.warn("Illegal snake move attempted");
      throw new InvalidMoveException(finalSquare.toString());
    }

    if(enactedSecondaryMove) {
      primaryMoveDirection = Coordinate.multiply(primaryMoveDirection, primaryMultiplier);
    }

    ChessTile oldTile = board.getTile(piece.getCoordinates());
    piece.updateCoordinates(board.getTile(finalSquare), board);
    return Set.of(oldTile, board.getTile(piece.getCoordinates()));
  }

  /**
   * @throws InvalidMoveException because no capture possible
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException {
    LOG.warn("Snake does not support captures");
    throw new InvalidMoveException("Snake does not support captures");
  }

  /***
   * @return empty set, not applicable
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /***
   * @return all moves, including ones that snake around the board
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    Coordinate currentCoordinate = piece.getCoordinates();
    Coordinate newCoordinate = Coordinate.add(currentCoordinate, primaryMoveDirection);
    enactedSecondaryMove = false;

    if(!board.inBounds(newCoordinate)) {
      Coordinate delta = Coordinate.of(
          findDelta(0, board.getBoardHeight(), newCoordinate.getRow()),
          findDelta(0, board.getBoardLength(), newCoordinate.getCol()));
      newCoordinate = Coordinate.add(Coordinate.add(newCoordinate, secondaryMoveDirection), delta);
      enactedSecondaryMove = true;
    }
    try {
      return Set.of(board.getTile(newCoordinate));
    } catch (OutsideOfBoardException e) {
      return Collections.emptySet();
    }
  }

  /***
   * Given a range min to max, find the difference needed to scale target into [min, max)
   *
   * @return above
   */
  private int findDelta(int min, int max, int target) {
    return target < min ? min - target : (target >= max ? max - target - 1 : 0);
  }

  /***
   * @return empty list, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }
}
