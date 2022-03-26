package oogasalad;

public interface CaptureCriteria {

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
