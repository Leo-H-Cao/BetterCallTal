package oogasalad.GamePlayer.Board.Setup;

import java.util.List;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.GamePlayer.ValidStateChecker.ValidStateChecker;

/**
 * This class is used to setup the chess board. It is used to create the chess board JSON object.
 *
 * @param board              the list of tiles that make up the chess board
 * @param players            the list of players in the game
 * @param validStateCheckers the valid state checkers for the game
 * @param turnManagerData    the turn manager data for the game
 * @param history            the history manager data of the game
 * @author Ritvik Janamsetty
 */
public record ChessBoardData(List<List<ChessTile>> board, TurnManagerData turnManagerData,
                             GamePlayers players,
                             List<ValidStateChecker> validStateCheckers,
                             HistoryManagerData history) {

  /**
   * This method is used to create the chess board JSON object from a chess board.
   *
   * @param board the chess board
   */
  public ChessBoardData(ChessBoard board) {
    this(board.getTiles(), board.getTurnManagerData(), board.getGamePlayers(),
        board.getValidStateCheckers(), board.getHistoryManagerData());
  }

}
