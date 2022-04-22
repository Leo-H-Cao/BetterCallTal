package oogasalad.GamePlayer.Board.History;

import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard.ChessBoardData;
import oogasalad.GamePlayer.Board.History.History;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

/**
 * This class is used to store the history of the game when communicating with the server.
 *
 * @param board        the board of the game
 * @param movedPieces  the pieces that have been moved
 * @param updatedTiles the tiles that have been updated
 */
public record HistoryData(ChessBoardData board, Set<Piece> movedPieces,
                          Set<ChessTile> updatedTiles) {

  /**
   * This method returns the history of the game.
   *
   * @param history the history of the game
   */
  public HistoryData(History history) {
    this(history.board().getBoardData(), history.movedPieces(), history.updatedTiles());
  }

}
