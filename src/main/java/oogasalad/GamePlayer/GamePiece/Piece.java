package oogasalad.GamePlayer.GamePiece;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.ChessTile;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifier;
import oogasalad.GamePlayer.Movement.MovementSetModifier;

public class Piece {

  private Coordinate coordinates;
  private String name;
  private double pointValue;
  private int team;
  private boolean mainPiece;

  private List<Movement> movements;
  private List<Movement> captures;
  private List<MovementSetModifier> movementSetModifiers;
  private List<MovementModifier> movementModifiers;
  private List<MovementModifier> onInteractionModifiers;

  private ChessBoard board;

  /***
   * Creates a chess piece with all of its attributes
   */
  public Piece(PieceData pieceData, ChessBoard board) {
    this.coordinates = pieceData.startingLocation();
    this.name = pieceData.name();
    this.pointValue = pieceData.pointValue();
    this.team = pieceData.team();
    this.mainPiece = pieceData.mainPiece();
    this.movements = pieceData.movements();
    this.captures = pieceData.captures();
    this.movementSetModifiers = pieceData.movementSetModifiers();
    this.movementModifiers = pieceData.movementModifiers();
    this.onInteractionModifiers = pieceData.onInteractionModifiers();
    this.board = board;
  }

  /***
   * Moves this piece to a given square
   *
   * @param finalSquare to move the piece to
   * @return set of updated chess tiles
   */
  public Set<ChessTile> move(ChessTile finalSquare) {
    return null;
  }

  public Coordinate getCoordinates() {
    return coordinates;
  }
}
