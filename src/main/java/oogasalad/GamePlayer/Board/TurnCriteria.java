package oogasalad.GamePlayer.Board;

public interface TurnCriteria {

  /***
   * Determines which player is currently playing
   *
   * @return int player id
   */
  int getCurrentPlayer();

  /***
   * Increments turn
   *
   * @return int player id after turn is made
   */
  int incrementTurn();
}
