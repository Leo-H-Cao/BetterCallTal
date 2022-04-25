package oogasalad.Frontend.Menu.LocalPlay.RemotePlayer;

import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;

public interface RemotePlayer {

  /**
   * Gets the move of a remote player
   */
  TurnUpdate getRemoteMove(ChessBoard board, int currentPlayer) throws Throwable;

}
