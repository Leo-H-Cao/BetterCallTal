package oogasalad.GamePlayer.Movement.MovementModifiers;

import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.BLOCK_COL;
import static oogasalad.GamePlayer.ValidStateChecker.BankBlocker.CH_CONFIG_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Coordinate;
import oogasalad.GamePlayer.Movement.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class BankJoiner implements MovementModifier{

  private static final Logger LOG = LogManager.getLogger(BankJoiner.class);
  private static final int[] DEFAULT_VALUE = new int[]{0, 1};

  /**
   *
   * @param piece that is referenced
   * @param board that piece is on
   * @return set of updated squares
   */
  @Override
  public Set<ChessTile> updateMovement(Piece piece, ChessBoard board) {
    LOG.debug("Bank join started");
    try {
      Piece justTaken = board.getHistory().get(board.getHistory().size() - 1).board().getTile(
          piece.getCoordinates()).getPiece().get();

      justTaken.updateTeam(board.getCurrentPlayer());
      //TODO: this does not need to be fixed with the current frontend implementation, but should be
      justTaken.updateImgFile(piece.getImgFile());
      justTaken.setNewMovements(Movement.invertMovements(justTaken.getMoves()),
          Movement.invertMovements(justTaken.getCaptures()));
      Set<ChessTile> updatedCoords = justTaken.updateCoordinates(findOpenSpot(justTaken.getTeam(), board), board);
      LOG.debug(String.format("Updated coords: %s", updatedCoords));
      return updatedCoords;
    } catch(OutsideOfBoardException | NoSuchElementException e) {
      LOG.warn("Bank joining failed");
      return Set.of();
    }
  }

  /***
   * @return where in the bank to put the piece
   */
  private ChessTile findOpenSpot(int teamID, ChessBoard board) throws OutsideOfBoardException {
    LOG.debug("Finding open spot");
    int col = BLOCK_COL + 1;
    int[] rowInfo = getStartRow(teamID, board.getBoardHeight());
    int row = rowInfo[0];
    int direction = rowInfo[1];

    ChessTile currentTile = board.getTile(Coordinate.of(row, col));
    while(!currentTile.getPieces().isEmpty()) {
      if(col >= board.getBoardLength()) {
        col = BLOCK_COL + 1;
        row = row + direction;
        LOG.debug(String.format("Updating open spot row, col: (%d, %d)", row, col));
      }
      else {
        col++;
      }
      currentTile = board.getTile(Coordinate.of(row, col));
    }
    LOG.debug(String.format("Bank tile: (%d, %d)", currentTile.getCoordinates().getRow(), currentTile.getCoordinates().getCol()));
    return currentTile;
  }

  /***
   * @param teamId of piece
   * @return which row to start on for the bank based on the piece id, and the direction to move
   */
  private int[] getStartRow(int teamId, int boardHeight) {
    try {
      String content = new String(Files.readAllBytes(Path.of(CH_CONFIG_FILE)));
      JSONArray rowArray = new JSONObject(content).getJSONArray("bankInfo");
      for(int i=0; i<rowArray.length(); i++) {
        JSONObject rowObject = rowArray.getJSONObject(i);
        LOG.debug(String.format("Expected teamID vs actual: (%d, %d)", teamId, rowObject.getInt("team")));
        if(rowObject.getInt("team") == teamId) {
          LOG.debug("reached");
          int[] startRow = new int[]{rowObject.getInt("row") < 0 ? boardHeight + rowObject.getInt("row") : rowObject.getInt("row"), rowObject.getInt("dir")};
          LOG.debug(String.format("Start row and delta: %s", Arrays.toString(startRow)));
          return startRow;
        }
      }
      LOG.warn("Default value used for bank start row");
      return DEFAULT_VALUE;
    } catch (IOException e) {
      LOG.warn("Default value used for bank start row");
      return DEFAULT_VALUE;
    }
  }
}
