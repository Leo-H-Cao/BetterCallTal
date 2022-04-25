package oogasalad.GamePlayer.Board.History;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/***
 * Record containing updates of a board
 *
 * @author Vincent Chen, Ritvik Janamsetty
 */
public record History(ChessBoard board, Set<Piece> movedPieces, Set<ChessTile> updatedTiles) {

  /**
   * Creates a new history record from a given History Data record
   *
   * @param historyData the history data to be converted to a history record
   */
  public History(HistoryData historyData) {
    this(new ChessBoard(historyData.board()), historyData.movedPieces(),
        historyData.updatedTiles());
  }

  /**
   * Creates a history record from a given board
   *
   * @param board the board to be converted to a history record
   * @return the history record
   */
  public static Collection<History> fromBoard(ChessBoard board) {
    return List.of(new History(board, Set.of(), Set.of()));
  }

  /**
   * @return information in record
   */
  @Override
  public String toString() {
    return String.format("Moved pieces: %s; Updated tiles:%s", movedPieces, updatedTiles);
  }

  /**
   * Gets the history data from the record
   *
   * @return the history data
   */
  public HistoryData getHistoryData() {
    return new HistoryData(board.getBoardData(), movedPieces, updatedTiles);
  }
}
