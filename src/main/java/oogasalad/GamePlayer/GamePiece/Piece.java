package oogasalad.GamePlayer.GamePiece;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifier;
import oogasalad.GamePlayer.Movement.CustomMovement;

/**
 * @author Vincent Chen
 * @author Jed Yang
 * @author Jose Santillan
 */
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
  private List<CustomMovement> movementSetModifiers;
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
  public Set<ChessTile> move(ChessTile finalSquare)
      throws InvalidMoveException, OutsideOfBoardException {
    Set<ChessTile> moves = getMoves();

    if (!moves.contains(finalSquare)) {
      throw new InvalidMoveException("Tile is not a valid move!");
    }
    //TODO NOT COMPLETELY FINISHED, AM WAITING TO SEE HOW DIFFERENT TILES ARE IMPLEMENTED TO FINISH
    ChessTile firstSquare = board.getTile(coordinates);
    coordinates = finalSquare.getCoordinates();
    finalSquare.appendPiece(this);
    return new HashSet<>(Set.of(firstSquare, finalSquare));
  }

  /***
   * Gets all possible moves the piece can make
   *
   * @return set of possible moves
   */
  public Set<ChessTile> getMoves() {
    Set<ChessTile> allMoves = new HashSet<>();

    movements.stream()
        .map(move -> move.getMoves(this, board))
        .collect(Collectors.toSet())
        .forEach(allMoves::addAll);

    movements.stream()
        .map(move -> move.getCaptures(this, board))
        .collect(Collectors.toSet())
        .forEach(allMoves::addAll);

    return allMoves;
  }

  private boolean validCapture(Coordinate coordinate) {
    //TODO MOVEMENTS IS A PLACEHOLDER, MUST IMPLEMENT CAPTURES AS IT IS NULL WHEN THE PIECE IS FIRST INITIALED AS WELL AS THE GETMOVES METHOD
    return movements.stream().map(move -> move.getMoves(this, board))
        .collect(Collectors.toSet())
        .stream().flatMap(Set::stream)
        .collect(Collectors.toSet()).stream()
        .anyMatch(tile -> tile.getCoordinates().equals(coordinate));
  }
  /**
   *
   */
  public boolean validCapture(List<Coordinate> coordinate) {
    return coordinate.stream().anyMatch(this::validCapture);
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
    boolean sameTeam = Arrays.stream(opponentIDs).anyMatch((o) -> piece.team == board.getPlayer(o).teamID());

    return !sameTeam;
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


  public int getTeam(){
    return team;
  }

  public boolean isTargetPiece() {
    return mainPiece;
  }
  public String getName(){
    return name;
  }
}
