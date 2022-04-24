package oogasalad.GamePlayer.Board.TurnCriteria;

import oogasalad.GamePlayer.Board.Player;

/***
 * End condition where only the first person gets to go
 *
 * @author Vincent Chen
 */
public class OnlyFirstTeam extends TurnCriteria {

  /***
   * Creates OnlyFirstTeam with given players array
   *
   * @param players in the given game
   */
  public OnlyFirstTeam(Player[] players) {
    super(players);
  }

  /***
   * @return current player, which is always the first one
   */
  @Override
  public int incrementTurn() {
    return getCurrentPlayer();
  }

  /***
   * @return copy of this turn criteria
   */
  @Override
  public TurnCriteria copy() {
    return new OnlyFirstTeam(players);
  }
}
