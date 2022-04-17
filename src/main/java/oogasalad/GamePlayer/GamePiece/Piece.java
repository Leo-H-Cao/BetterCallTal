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

  private Coordinate coordinates;
  private SupplementaryPieceData suppPieceData;
  private int team;
  private String name;
  private String img;
  private MovementHandler movementHandler;
  private List<MovementModifier> onInteractionModifiers;
  private List<Coordinate> history;

  /***
   * Creates a chess piece with all of its attributes
   */
  public Piece(PieceData pieceData) {
    this(pieceData, new MovementHandler(pieceData.movements(), pieceData.captures(), pieceData.movementModifiers()));
  }

  /***
   * Helper constructor for clone when a movementHandler is directly provided
   */
  public Piece(PieceData pieceData, MovementHandler movementHandler) {
    this.coordinates = pieceData.startingLocation();
    this.suppPieceData = new SupplementaryPieceData(pieceData.pointValue(),
        pieceData.mainPiece());
    this.name = pieceData.name();
    this.team = pieceData.team();
    this.img = pieceData.img();
    this.movementHandler = movementHandler;
    this.onInteractionModifiers = pieceData.onInteractionModifiers();
    this.history = new ArrayList<>(List.of(pieceData.startingLocation()));
  }

  /***
   * Moves this piece to a given square
   *
   * @param finalSquare to move the piece to
   * @return set of updated chess tiles
   */
  public Set<ChessTile> move(ChessTile finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    return movementHandler.move(this, finalSquare, board);
  }

  /***
   * Updates this piece's coordinates to the parameter passed and updates that tile to hold the
   * new piece
   *
   * @param tile to put piece on
   * @return updated actions based on tile actions and where piece moved
   */
  public Set<ChessTile> updateCoordinates(ChessTile tile, ChessBoard board) throws OutsideOfBoardException {
    try {
      Set<ChessTile> updatedSquares = new HashSet<>(List.of(board.getTile(coordinates), tile));
      board.getTile(coordinates).removePiece(this);
      coordinates = tile.getCoordinates();
      tile.appendPiece(this);
      history.add(tile.getCoordinates());
      updatedSquares.addAll(tile.executeActions(board));
      LOG.debug(String.format("Updated squares: %s", updatedSquares));
      return updatedSquares;
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
  public Set<ChessTile> getMoves(ChessBoard board) {
    return movementHandler.getMoves(this, board);
  }

  /***
   * @param coordinates to check for captures
   * @return if this piece can capture a piece on the given coordinates
   */
  public boolean validCapture(List<Coordinate> coordinates, ChessBoard board) {
    return movementHandler.validCapture(this, coordinates, board);
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
  public boolean canCapture(Piece piece, ChessBoard board) {
    return movementHandler.canCapture(this, piece, board);
  }

  /***
   * @return if this piece is opposing any piece in the given list (i.e. player numbers are
   * opposing)
   */
  public boolean isOpposing(List<Piece> opponents, ChessBoard board) {
    return opponents.stream().anyMatch((p) -> isOpposing(p, board));
  }

  /***
   * @param piece to check
   * @return if a given piece opposes this piece
   */
  private boolean isOpposing(Piece piece, ChessBoard board) {
    int[] opponentIDs = board.getPlayer(team).opponentIDs();
    return Arrays.stream(opponentIDs).anyMatch((o) -> piece.getTeam() == board.getPlayer(o).teamID());
  }

  /***
   * @param pieces to potentially capture
   * @return if this piece can capture any piece in a list of pieces
   */
  public boolean canCapture(List<Piece> pieces, ChessBoard board) {
    return movementHandler.canCapture(this, pieces, board);
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
    return getTeam() == team;
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
    return getTeam() == piece.getTeam();
  }

  /***
   * @return if this piece is the main piece
   */
  public boolean isTargetPiece() {
    return suppPieceData.mainPiece();
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
   * @return relative coordinates for all regular moves, not including captures
   */
  public List<MovementInterface> getMoves() {
    return movementHandler.getMovements();
  }

  /***
   * @return relative coordinates for all regular moves, not including captures
   */
  public List<Coordinate> getRelativeMoveCoords() {
    return movementHandler.getRelativeMoveCoords();
  }

  /***
   * @return relative coordinates for all regular moves, including captures
   */
  public List<Coordinate> getRelativeCapCoords() {
    return movementHandler.getRelativeCapCoords();
  }

  /***
   * @param newMovements new coordinates for all regular moves to set
   * @param newCaptures new coordinates for all regular captures to set
   */
  public void setNewMovements(List<MovementInterface> newMovements, List<MovementInterface> newCaptures) {
    movementHandler.setNewMovements(newMovements, newCaptures);
  }

  /***
   * @param newMovements new coordinates for all regular moves to add
   * @param newCaptures new coordinates for all regular captures to add
   */
  public void addNewMovements(List<MovementInterface> newMovements, List<MovementInterface> newCaptures) {
    movementHandler.addNewMovements(newMovements, newCaptures);
  }

  /***
   * @return relative coordinates for all regular moves including captures
   */
  public List<MovementInterface> getCaptures() {
    return movementHandler.getCaptures();
  }

  /***
   * Changes team of this piece
   * @param newTeam is the new team of this piece
   */
  public void updateTeam(int newTeam) {
    this.team = newTeam;
  }

  /***
   * Changes team of this piece
   * @param newImgFile is the new team of this piece
   */
  public void updateImgFile(String newImgFile) {
    this.img = newImgFile;
  }

  /***
   * Updates board state based on interaction modifiers and returns set of updated tiles
   *
   * @param board that piece is on
   * @return set of updated tiles based on logic in each interaction modifier
   */
  public Set<ChessTile> runInteractionModifiers(ChessBoard board) {
    LOG.debug("Running on interaction modifiers");
    return onInteractionModifiers.stream().flatMap((oim) -> oim.updateMovement(this, board).stream()).collect(
        Collectors.toSet());
  }

  /***
   * @return complete copy of the piece, including copies of all instance variables
   */
  @Override
  public Piece clone() {
    PieceData clonedData = new PieceData(
        new Coordinate(getCoordinates().getRow(), getCoordinates().getCol()),
        getName(),
        suppPieceData.pointValue(),
        getTeam(),
        suppPieceData.mainPiece(),
        List.of(),
        List.of(),
        List.of(),
        new ArrayList<>(onInteractionModifiers),
        img
    );
    return new Piece(clonedData, movementHandler);
  }

  /**
   * This method is used for the burn tile in order to
   */
  public boolean burn() {
    //TODO NEED TO ADD PIECE HEALTH TO JSON AND CONSTRUCTOR
    PieceHealth health = new PieceHealth();
    return health.damage();
  }

  /**
   * Updates/sets the movement for a piece
   */
  public void setMovement(MovementHandler moves) {
    movementHandler = moves;
  }

  public void clearActions() {

  }

  /**
   * Update name with newName
   *
   * @param newName to update
   */
  public void updateName(String newName) {
    this.name = newName;
  }

  /***
   * @return piece data
   */
  @Override
  public String toString() {
    return name;
  }

  /***
   * Checks if name, team, and coordinates are the same
   *
   * @param o other piece to compare to
   * @return if name, team, and coordinates are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Piece otherPiece = (Piece) o;
    return this.getName().equals(otherPiece.getName()) && this.getTeam() == otherPiece.getTeam() &&
    this.getCoordinates().equals(otherPiece.getCoordinates());
  }
}
