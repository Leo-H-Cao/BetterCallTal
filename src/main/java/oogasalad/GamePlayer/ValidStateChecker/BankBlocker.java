package oogasalad.GamePlayer.ValidStateChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidBoardSizeException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/***
 * Creates a custom movement that prevents pieces from going into the piece bank
 */
public class BankBlocker implements ValidStateChecker {

  public static final String CH_CONFIG_FILE_HEADER = "doc/GameEngineResources/Other/";
  public static final String CH_DEFAULT_FILE = "CrazyhouseConfig.json";
  public static final String BLOCKER_NAME = "blocker";

  private static final Logger LOG = LogManager.getLogger(BankBlocker.class);
  public static final int DEFAULT_VALUE = 8;

  public int blockCol;

  /***
   * Create BankBlocker with default file path
   */
  public BankBlocker() {
    this(CH_DEFAULT_FILE);
  }

  /***
   * Create BankBlocker with given config file
   *
   * @param configFile to read
   */
  public BankBlocker(String configFile) {
    blockCol = getBlockerColumn(CH_CONFIG_FILE_HEADER + configFile);
  }

  /**
   * @return blocker column for bank separation
   */
  private int getBlockerColumn(String configFile) {
    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
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
    if(getBankHeight(board) * getBankWidth(board) < board.getPieces().stream().filter(p ->
        !p.getName().equalsIgnoreCase(BLOCKER_NAME) && isPlayerPiece(p, board.getPlayers())).toList().size()) {
      LOG.warn(String.format("Invalid size: expected is %d, actual is %d", board.getPieces().size(), getBankHeight(board) * getBankWidth(board)));
      throw new InvalidBoardSizeException(String.format("Current size: %d; needed size: %d",
          getBankHeight(board) * getBankWidth(board), board.getPieces().size()));
    }
    LOG.debug(String.format("Move coords: (%d, %d); BLOCK_COL: %d", move.getCoordinates().getRow(), move.getCoordinates().getCol(),
        blockCol));
    return move.getCoordinates().getCol() < blockCol;
  }

  /***
   * @return if a given piece can be played by a player
   */
  private boolean isPlayerPiece(Piece piece, Player[] players) {
    LOG.debug(String.format("Piece team, players: %d, %s", piece.getTeam(), Arrays.toString(players)));
    return Arrays.stream(players).anyMatch(p -> p.teamID() == piece.getTeam());
  }

  /**
   * @return width of bank
   */
  private int getBankWidth(ChessBoard board) {
    return (board.getBoardLength() - blockCol - 1);
  }

  /***
   * @return height of bank
   */
  private int getBankHeight(ChessBoard board) {
    return board.getBoardHeight();
  }
}
