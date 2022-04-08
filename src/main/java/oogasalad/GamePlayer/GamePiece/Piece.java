package oogasalad.GamePlayer.GamePiece;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

/**
 * @author Vincent Chen
 * @author Jed Yang
 * @author Jose Santillan
 */
public class Piece implements Cloneable {

  private static final Logger LOG = LogManager.getLogger(Piece.class);

  private static final boolean VALID_SQUARE = true;
  private static final boolean INVALID_SQUARE = false;

  private Coordinate coordinates;
  private String name;
  private double pointValue;
  private int team;
  private boolean mainPiece;
  private String img;
  private List<Coordinate> history;

  private List<MovementInterface> movements;
  private List<MovementInterface> captures;
  private List<MovementInterface> customMovements;
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

    boolean valid = getMoves().stream()
        .anyMatch(tile -> tile.getCoordinates().equals(finalSquare.getCoordinates()));

    if (!valid) {
      throw new InvalidMoveException("Tile is not a valid move!");
    }

    //TODO: need to know whether a move is a capture or not for OIM, things like atomic
    Set<ChessTile> updatedSquares = new HashSet<>(findMovements(finalSquare, movements));
    updatedSquares.addAll(findMovements(finalSquare, customMovements));
    updatedSquares.addAll(findCaptures(finalSquare, customMovements));
    updatedSquares.addAll(findCaptures(finalSquare, captures));

    movementModifiers.forEach((mm) -> updatedSquares.addAll(mm.updateMovement(this, finalSquare, board)));
    return updatedSquares;
  }

  private Set<ChessTile> findMovements(ChessTile finalSquare, List<MovementInterface> movements) {
    Set<ChessTile> updatedSquares = new HashSet<>();
    movements.stream().filter((m) -> m.getMoves(this, board).contains(finalSquare)).findFirst().ifPresent((m) ->
    {try{updatedSquares.addAll(m.movePiece(this, finalSquare.getCoordinates(), board));} catch (Exception ignored){}});
    return updatedSquares;
  }

  private Set<ChessTile> findCaptures(ChessTile finalSquare, List<MovementInterface> captures) {
    Set<ChessTile> updatedSquares = new HashSet<>();
    captures.stream().filter((m) -> m.getCaptures(this, board).contains(finalSquare)).findFirst().ifPresent((m) ->
    {try{updatedSquares.addAll(m.capturePiece(this, finalSquare.getCoordinates(), board));} catch (Exception ignored){}});
    return updatedSquares;
  }

  /***
   * Updates this piece's coordinates to the parameter passed and updates that tile to hold the
   * new piece
   *
   * @param tile to put piece on
   */
  public void updateCoordinates(ChessTile tile) throws OutsideOfBoardException {
    try {
      board.getTile(coordinates).removePiece(this);
      coordinates = tile.getCoordinates();
      tile.appendPiece(this);
      history.add(tile.getCoordinates());
    } catch(OutsideOfBoardException e) {
      LOG.warn("Couldn't update piece coordinates");
      throw new OutsideOfBoardException(coordinates.toString());
    }
  }

  /***
   * Gets all possible moves the piece can make
   *
   * @return set of possible moves
   */
  public Set<ChessTile> getMoves() {
    Set<ChessTile> allMoves = new HashSet<>();
    Map<MovementInterface, Set<ChessTile>> movementSquaresMap = getMovementSquaresMap();

    movementSquaresMap.keySet().forEach((k) -> allMoves.addAll(movementSquaresMap.get(k)));
    LOG.debug(String.format("%s has the following moves: %s", name, allMoves));
    return allMoves;
  }

  /***
   * @return map of movement object to squares to move to
   */
  private Map<MovementInterface, Set<ChessTile>> getMovementSquaresMap() {
    Map<MovementInterface, Set<ChessTile>> movementSquaresMap = new HashMap<>();

    movementSquaresMap.putAll(streamMapMovements(movements));
    movementSquaresMap.putAll(streamMapMovements(customMovements));

    movementSquaresMap.putAll(streamMapCaptures(captures));
    movementSquaresMap.putAll(streamMapCaptures(customMovements));

    return movementSquaresMap;
  }

  /***
   * @param movements to map
   * @return map of movements to movement squares
   */
  private Map<MovementInterface, Set<ChessTile>> streamMapMovements(List<MovementInterface> movements) {
    return movements.stream().collect(Collectors.toMap((m) -> m, (m) -> m.getMoves(this, board)));
  }

  /**
   * @param captures to map
   * @return map of captures to capture squares
   */
  private Map<MovementInterface, Set<ChessTile>> streamMapCaptures(List<MovementInterface> captures) {
    return captures.stream().collect(Collectors.toMap((m) -> m, (m) -> m.getCaptures(this, board)));
  }

  /***
   * @param coordinate to check for captures
   * @return if this piece can capture a piece on the given coordinate
   */
  private boolean validCapture(Coordinate coordinate) {
    //TODO MOVEMENTS IS A PLACEHOLDER, MUST IMPLEMENT CAPTURES AS IT IS NULL WHEN THE PIECE IS FIRST INITIALED AS WELL AS THE GETMOVES METHOD
    Set<Set<ChessTile>> set = new HashSet<>();
    for (MovementInterface move : movements) {
      set.add(move.getCaptures(this, board));
    }
    return movements.stream()
        .map(move -> move.getCaptures(this, board))
        .collect(Collectors.toSet())
        .stream().flatMap(Set::stream)
        .collect(Collectors.toSet()).stream()
        .anyMatch(tile -> tile.getCoordinates().equals(coordinate));
  }

  /***
   * @param coordinates to check for captures
   * @return if this piece can capture a piece on the given coordinates
   */
  public boolean validCapture(List<Coordinate> coordinates) {
    return coordinates.stream().anyMatch(this::validCapture);
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
   * @return team of piece
   */
  public int getTeam(){
    return team;
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
