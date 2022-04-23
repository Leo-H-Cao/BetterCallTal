package oogasalad.GamePlayer.Board.TurnCriteria;

import static oogasalad.GamePlayer.Board.TurnCriteria.ConstantIncrease.TURN_INCREMENT_NUM;

import oogasalad.GamePlayer.Board.Player;

/***
 * Creates a turn criteria that linearly goes across the player array: the most basic type
 *
 * @author Vincent Chen
 */
public class Linear extends TurnCriteria {

  /***
   * Creates a turn criteria that linearly goes across the player array: the most basic type
   * @param players array to get turns for
   */
  public Linear(Player[] players) {
    super(players);
  }

  /***
   * Moves the pointer to the next player, or back to the start if the end is reached
   * @return next player
   */
  @Override
  public int incrementTurn() {
    incrementIndex(TURN_INCREMENT_NUM);
    return getCurrentPlayer();
  }

  /***
   * @return copy of this turn criteria
   */
  public TurnCriteria copy(){
    Linear copy = new Linear(players);
    copy.incrementTurn();//temporary fix
    return copy;
  }
}
