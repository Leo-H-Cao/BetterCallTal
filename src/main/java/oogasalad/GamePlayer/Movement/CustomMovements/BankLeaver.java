package oogasalad.GamePlayer.Movement.CustomMovements;

import static oogasalad.GamePlayer.Board.BoardSetup.JSON_EXTENSION;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_CONFIG_FILE_HEADER;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_DEFAULT_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * Creates a custom movement that allows pieces to leave the bank
 *
 * @author Vincent Chen
 */
public class BankLeaver implements MovementInterface {

  private static final Logger LOG = LogManager.getLogger(BankLeaver.class);

  private int blockCol;
  private String configFile;

  /***
   * Create BankLeaver with default config file
   */
  public BankLeaver() {
    this(CH_DEFAULT_FILE);
  }

  /***
   * Create BankLeave with given config file
   *
   * @param configFile to read
   */
  public BankLeaver(String configFile) {
    this.configFile = CH_CONFIG_FILE_HEADER + configFile + JSON_EXTENSION;
    LOG.debug(String.format("Config file: %s", this.configFile));
    try {
      String content = new String(Files.readAllBytes(Path.of(this.configFile)));
      JSONObject data = new JSONObject(content);
      blockCol = data.
          getJSONArray("general").getJSONObject(0).getInt("blockerCol");
    } catch (IOException e) {
      blockCol = BankBlocker.DEFAULT_VALUE;
      this.configFile = CH_CONFIG_FILE_HEADER + CH_DEFAULT_FILE + JSON_EXTENSION;
    }
    LOG.debug(String.format("Block col: %d", blockCol));
  }

  /***
   * For testing, gets the block col
   *
   * @return block col
   */
  int getBlockCol() {
    return blockCol;
  }

  /**
   * @return updated squares when a piece leaves the bank
   */
  @Override
  public Set<ChessTile> movePiece(Piece piece, Coordinate finalSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    if(!getMoves(piece, board).contains(board.getTile(finalSquare))) {
      LOG.warn("Illegal bank leaving move attempted");
      throw new InvalidMoveException(finalSquare.toString());
    }
    board.getTile(finalSquare).clearPieces();
    Set<ChessTile> updatedSquares = new HashSet<>(
        Arrays.asList(board.getTile(piece.getCoordinates()), board.getTile(finalSquare)));
    updatedSquares.addAll(piece.updateCoordinates(board.getTile(finalSquare), board));
    return updatedSquares;
  }

  /***
   * @throws InvalidMoveException not a valid move
   */
  @Override
  public Set<ChessTile> capturePiece(Piece piece, Coordinate captureSquare, ChessBoard board)
      throws InvalidMoveException, OutsideOfBoardException {
    LOG.warn("Bank leaving does not support captures");
    throw new InvalidMoveException("Bank leaving does not support captures");
  }

  /***
   * @return nothing, not applicable
   */
  @Override
  public Set<ChessTile> getCaptures(Piece piece, ChessBoard board) {
    return Collections.emptySet();
  }

  /***
   * @return all open squares on board to the left of the bank, if piece is in the bank
   */
  @Override
  public Set<ChessTile> getMoves(Piece piece, ChessBoard board) {
    if(piece.getCoordinates().getCol() < blockCol) return Set.of();
    return board.stream().flatMap(Collection::stream).toList().stream().filter(t ->
        t.getCoordinates().getCol() < blockCol && t.getPieces().isEmpty() &&
        !isBlockedSquare(piece.getName(), t.getCoordinates(), board.getBoardHeight()))
        .collect(Collectors.toSet());
  }

  /***
   * @return if a piece can move to a given square based on the CrazyhouseConfig json
   */
  private boolean isBlockedSquare(String pieceName, Coordinate possibleCoordinate, int boardHeight) {
    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
      JSONArray pieceRestrictionsArray = new JSONObject(content).getJSONArray("pieceRestrictions");
      LOG.debug(String.format("Piece restriction array length: %d", pieceRestrictionsArray.length()));
      for(int i=0; i<pieceRestrictionsArray.length(); i++) {
        JSONObject currentPR = pieceRestrictionsArray.getJSONObject(i);
        if(currentPR.getString("piece").equals(pieceName)) {
          List<Integer> restrictedRows = convertToPosInts(currentPR.getJSONArray("rows"), boardHeight);
          List<Integer> restrictedCols = convertToPosInts(currentPR.getJSONArray("cols"), blockCol);
          return restrictedRows.contains(possibleCoordinate.getRow()) || restrictedCols.contains(possibleCoordinate.getCol());
        }
      }
      return false;
    } catch (IOException e) { return true;}
  }

  /***
   * @param intArray to convert to all positive ints
   * @param max the number to loop from if given a negative number
   * @return positive number list bound by max
   */
  private List<Integer> convertToPosInts(JSONArray intArray, int max) {
    List<Integer> posInts = new ArrayList<>(intArray.length());
    for(int i=0; i<intArray.length(); i++) {
      posInts.add(intArray.getInt(i) < 0 ? max + intArray.getInt(i) : intArray.getInt(i));
    }
    return posInts;
  }

  /***
   * @return nothing, not applicable
   */
  @Override
  public List<Coordinate> getRelativeCoords() {
    return Collections.emptyList();
  }

  /***
   * @return equals if block cols are the same
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankLeaver that = (BankLeaver) o;
    return blockCol == that.blockCol;
  }

  /***
   * @return hash of this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(blockCol);
  }
}
