package oogasalad.GamePlayer.Movement;

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
public class BasicCapture extends BasicMovementInterface {

  /***
   * Creates a class representing a basic piece capture
   */
  public BasicCapture(List<Coordinate> possibleMoves, boolean infinite) {
    super(possibleMoves, infinite);
  }

  /***
   * Creates a class representing a basic piece capture with one coordinate provided
   */
  public BasicCapture(Coordinate possibleMove, boolean infinite) {
    super(possibleMove, infinite);
  }

  /**
   * Constructor for Jackson serialization and deserialization
   */
  public BasicCapture(){
    super();
  }

  /***
   * Returns all possible captures a piece can make
   *
   * @param piece to get captures from
   * @param board to move on
   * @return set of tiles the piece can capture on
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    return getFromMovesMap(piece, board, CAPTURE_KEY);
  }
}
