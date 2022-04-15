package oogasalad.GamePlayer.ValidStateChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidBoardSizeException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/***
 * Creates a custom movement that prevents pieces from going into the piece bank
 */
public class BankBlocker implements ValidStateChecker {

  public static final String CH_CONFIG_FILE = "doc/GameEngineResources/Other/CrazyhouseConfig.json";
  public static final int BLOCK_COL = getBlockerColumn();
  public static final String BLOCKER_NAME = "blocker";

  private static final Logger LOG = LogManager.getLogger(BankBlocker.class);
  private static final int DEFAULT_VALUE = 8;

  /**
   * @return blocker column for bank separation
   */
  private static int getBlockerColumn() {
    try {
      String content = new String(Files.readAllBytes(Path.of(CH_CONFIG_FILE)));
      JSONObject JSONContent = new JSONObject(content);

      return JSONContent.getJSONArray("general").getJSONObject(0).getInt("blockerCol");
    } catch (IOException e) {
      return DEFAULT_VALUE;
    }
  }

  /**
   * @return if the given piece movement falls to the left of the bank
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece,
      ChessTile move) throws OutsideOfBoardException, InvalidBoardSizeException {
    if(getBankHeight(board) * getBankWidth(board) < board.getPieces().stream().filter((p) -> !p.getName().equals(BLOCKER_NAME)).toList().size()) {
      LOG.warn(String.format("Invalid size: expected is %d, actual is %d", board.getPieces().size(), getBankHeight(board) * getBankWidth(board)));
      throw new InvalidBoardSizeException(String.format("Current size: %d; needed size: %d",
          getBankHeight(board) * getBankWidth(board), board.getPieces().size()));
    }
    LOG.debug(String.format("Move coords: (%d, %d); BLOCK_COL: %d", move.getCoordinates().getRow(), move.getCoordinates().getCol(), BLOCK_COL));
    return move.getCoordinates().getCol() < BLOCK_COL;
  }

  /**
   * @return width of bank
   */
  private int getBankWidth(ChessBoard board) {
    return (board.getBoardLength() - BLOCK_COL - 1);
  }

  /***
   * @return height of bank
   */
  private int getBankHeight(ChessBoard board) {
    return board.getBoardHeight();
  }
}
