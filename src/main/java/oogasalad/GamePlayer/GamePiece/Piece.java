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

  private Coordinate coordinates;
  private SupplementaryPieceData suppPieceData;
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
    this.suppPieceData = new SupplementaryPieceData(pieceData.name(), pieceData.pointValue(),
        pieceData.team(), pieceData.mainPiece(), pieceData.img());
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
   * @return updated actions based on tile actions
   */
  public Set<ChessTile> updateCoordinates(ChessTile tile, ChessBoard board) throws OutsideOfBoardException {
    try {
      board.getTile(coordinates).removePiece(this);
      coordinates = tile.getCoordinates();
      tile.appendPiece(this);
      history.add(tile.getCoordinates());
      return tile.executeActions(board);
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
    int[] opponentIDs = board.getPlayer(suppPieceData.team()).opponentIDs();
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
    return suppPieceData.img();
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
    return suppPieceData.team();
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
    return suppPieceData.name();
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
  public List<Coordinate> getRelativeMoveCoords() {
    return movementHandler.getRelativeMoveCoords();
  }

  /***
   * Updates board state based on interaction modifiers and returns set of updated tiles
   *
   * @param board that piece is on
   * @return set of updated tiles based on logic in each interaction modifier
   */
  public Set<ChessTile> runInteractionModifiers(ChessBoard board) {
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
        suppPieceData.img()
    );
    return new Piece(clonedData, movementHandler);
  }
}
