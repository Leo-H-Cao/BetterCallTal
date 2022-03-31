package oogasalad.GamePlayer.GamePiece;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifier;
import oogasalad.GamePlayer.Movement.MovementSetModifier;

public class Piece {

  private static final boolean VALID_SQUARE = true;
  private static final boolean INVALID_SQUARE = false;
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

  private String img;

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
    this.img = pieceData.img();
    this.board = board;
  }

  /***
   * Moves this piece to a given square
   *
   * @param finalSquare to move the piece to
   * @return set of updated chess tiles
   */
  public Set<ChessTile> move(ChessTile finalSquare) {
    List<Movement> allMoves = Stream.concat(movements.stream(), captures.stream()).toList();
    Set<ChessTile> validMoves = new HashSet<>();

    allMoves.forEach(move -> validMoves.addAll(move.getMoves(this, board)));

    if (validMoves.contains(finalSquare)) {
      coordinates = finalSquare.getCoordinates();
      finalSquare.appendPiece(this);
    }
    return validMoves;
  }

  /***
   * Gets all possible moves the piece can make
   *
   * @return set of possible moves
   */
  public Set<ChessTile> getMoves() {
    return null;
  }

  /***
   * @return coordinate of piece
   */
  public Coordinate getCoordinates() {
    return coordinates;
  }

  /***
   * @param piece to capture
   * @return if this piece can capture another piece
   */
  public boolean canCapture(Piece piece) {
    int[] opponentIDs = board.getPlayer(this.team).opponentIDs();
    return Arrays.stream(opponentIDs).anyMatch((o) -> piece.team == board.getPlayer(o).teamID());
  }

  /***
   * @param pieces to potentially capture
   * @return if this piece can capture any piece in a list of pieces
   */
  public boolean canCapture(List<Piece> pieces) {
    return pieces.stream().anyMatch(this::canCapture);
  }

  /***
   * @return file path to image file representing piece
   */
  public String getImgFile() {
    return img;
  }

  /***
   * Checks if a given team matches the piece team
   *
   * @param team to check
   * @return if the provided team is the same as this piece's team
   */
  public boolean checkTeam(int team) {
    return this.team == team;
  }
}
