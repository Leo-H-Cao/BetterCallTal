package oogasalad.Frontend.LocalPlay.RemotePlayer;

import java.util.Set;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;

public interface RemotePlayer {

  /**
   * Gets the move of a remote player
   */
  Set<ChessTile> getRemoteMove();

}
