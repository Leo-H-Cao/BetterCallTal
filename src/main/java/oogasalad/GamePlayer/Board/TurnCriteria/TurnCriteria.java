package oogasalad.GamePlayer.Board.TurnCriteria;

import oogasalad.GamePlayer.Board.Player;

/***
 * Abstraction of turn determination
 *
 * @author Vincent Chen
 */
public abstract class TurnCriteria {

  private Player[] players;
  private int index;

  public TurnCriteria(Player[] players) {
    this.players = players;
    this.index = 0;
  }

  /**
   * @param i index in array
   * @return player id at specified index, adjusting if i is out of bounds
   */
  protected final int getPlayerIDAtIndex(int i) {
    i = Math.min(maxIndex(), Math.max(0, i));
    return players[i].teamID();
  }

  /**
   * Adds i to the current index
   * @param i to increment by
   */
  protected final void incrementIndex(int i) {
    index += i;
    if(index > maxIndex()) {
      index = 0;
    }
  }

  /**
   * @return last index in players array
   */
  protected final int maxIndex() {
    return players.length - 1;
  }


  /**
   * Determines which player is currently playing
   *
   * @return int player id
   */
  public final int getCurrentPlayer() {
    return getPlayerIDAtIndex(index);
  }

  /**
   * Increments turn
   *
   * @return int player id after turn is made
   */
  public abstract int incrementTurn();
}
