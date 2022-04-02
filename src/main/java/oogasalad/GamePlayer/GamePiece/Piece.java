package oogasalad.GamePlayer.GamePiece;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;
import oogasalad.GamePlayer.Movement.CustomMovements.CustomMovement;

/**
 * @author Vincent Chen
 * @author Jed Yang
 * @author Jose Santillan
 */
public class Piece implements Cloneable {

  private static final boolean VALID_SQUARE = true;
  private static final boolean INVALID_SQUARE = false;

  private Coordinate coordinates;
  private String name;
  private double pointValue;
  private int team;
  private boolean mainPiece;
  private String img;
  private List<Coordinate> history;

  private List<Movement> movements;
  private List<Movement> captures;
  private List<CustomMovement> customMovements;
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
    this.customMovements = pieceData.customMovements();
    this.movementModifiers = pieceData.movementModifiers();
    this.onInteractionModifiers = pieceData.onInteractionModifiers();
    this.img = pieceData.img();
    this.board = board;
    this.history = new ArrayList<>(List.of(pieceData.startingLocation()));
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
    history.add(finalSquare.getCoordinates());
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

    captures.stream()
        .map(capture -> capture.getCaptures(this, board))
        .collect(Collectors.toSet())
        .forEach(allMoves::addAll);

    return allMoves;
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
    //int[] opponentIDs = board.getPlayer(this.team).opponentIDs();
    //boolean sameTeam = Arrays.stream(opponentIDs).anyMatch((o) -> piece.team == board.getPlayer(o).teamID());
    boolean sameTeam = piece.checkTeam(team);

    //TODO MOVEMENTS IS A PLACEHOLDER, MUST IMPLEMENT CAPTURES AS IT IS NULL WHEN THE PIECE IS FIRST INITIALED
    boolean canCap = movements.stream()
        .map(capture -> capture.getCaptures(this, board))
        .flatMap(Set::stream)
        .map(ChessTile::getCoordinates)
        .anyMatch(coords -> coords.equals(piece.getCoordinates()));

    return !sameTeam && canCap;
  }

  /***
   * @return if this piece is opposing any piece in the given list (i.e. player numbers are
   * opposing)
   */
  public boolean isOpposing(List<Piece> opponents) {
//    return Arrays.stream(board.getPlayer(this.team).opponentIDs()).anyMatch((o) -> opponents.stream().anyMatch((t) ->
//        t.checkTeam(o)));
    return opponents.stream().anyMatch(this::isOpposing);
  }

  /***
   * @param piece to check
   * @return if a given piece opposes this piece
   */
  private boolean isOpposing(Piece piece) {
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

  /**
   * @param piece to check
   * @return if this and piece are on the same team
   */
  public boolean onSameTeam(Piece piece) {
    return this.team == piece.team;
  }

  /***
   * @return if this piece is the main piece
   */
  public boolean isTargetPiece() {
    return mainPiece;
  }

  /**
   * @return name of piece
   */
  public String getName(){
    return name;
  }

  /**
   * @return history of piece movement
   */
  public List<Coordinate> getHistory() {
    return history;
  }

  /***
   * @return complete copy of the piece, including copies of all instance variables
   */
  @Override
  public Piece clone() {
    PieceData clonedData = new PieceData(
        new Coordinate(getCoordinates().getRow(), getCoordinates().getCol()),
        name,
        pointValue,
        team,
        mainPiece,
        new ArrayList<>(movements),
        new ArrayList<>(captures),
        new ArrayList<>(customMovements),
        new ArrayList<>(movementModifiers),
        new ArrayList<>(onInteractionModifiers),
        img
    );
    return new Piece(clonedData, board);
  }
}
