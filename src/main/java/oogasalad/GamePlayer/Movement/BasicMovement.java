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
 * Basic piece movement given relative coordinates
 *
 * @author Vincent Chen
 */
public class BasicMovement extends BasicMovementInterface {

  /***
   * Creates a class representing a basic piece movement
   */
  public BasicMovement(List<Coordinate> possibleMoves, boolean infinite) {
    super(possibleMoves, infinite);
  }

  /***
   * Creates a class representing a basic piece movement with one coordinate provided
   */
  public BasicMovement(Coordinate possibleMove, boolean infinite) {
    super(possibleMove, infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  public BasicMovement(){
    super();
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
}
