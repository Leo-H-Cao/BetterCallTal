package oogasalad.GamePlayer.ValidStateChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.InvalidBoardSizeException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.json.JSONObject;

public class BankBlocker implements ValidStateChecker {

  public static final String CH_CONFIG_FILE = "doc/GameEngineResources/Other/CrazyhouseConfig.json";
  private static final int DEFAULT_VALUE = 8;
  public static final int BLOCK_COL = getBlockerColumn();

  /***
   * Creates a custom movement that prevents pieces from going into the piece bank
   */
  public BankBlocker() {

  }

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

  @Override
  public boolean isValid(ChessBoard board, Piece piece,
      ChessTile move) throws OutsideOfBoardException, InvalidBoardSizeException {
    if(getBankHeight(board) * getBankWidth(board) < board.getPieces().size()) {
      throw new InvalidBoardSizeException(String.format("Current size: %d; needed size: %d",
          getBankHeight(board) * getBankWidth(board), board.getPieces().size()));
    }
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
