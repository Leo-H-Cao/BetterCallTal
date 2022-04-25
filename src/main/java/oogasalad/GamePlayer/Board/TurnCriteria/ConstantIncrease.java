package oogasalad.GamePlayer.Board.TurnCriteria;

import oogasalad.GamePlayer.Board.Player;

/***
 * Creates TurnCriteria where each player gets one more move than the last
 *
 * @author Vincent Chen
 */
public class ConstantIncrease extends TurnCriteria {

  public static final int TURN_INCREMENT_NUM = 1;

  private int numMovesTotal;
  private int numMovesPlayed;

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public ConstantIncrease(){
    super();
  }

  /***
   * Creates TurnCriteria where each player gets one more move than the last
   * @param players array to get turns for
   */
  public ConstantIncrease(Player[] players) {
    super(players);
    numMovesTotal = 1;
  }

  /***
   * Goes to next player if they've played all their moves
   *
   * @return next player id
   */
  @Override
  public int incrementTurn() {
    numMovesPlayed++;
    if(numMovesTotal == numMovesPlayed) {
      incrementIndex(TURN_INCREMENT_NUM);
      numMovesTotal++;
      numMovesPlayed = 0;
    }
    return getCurrentPlayer();
  }

  /***
   * @return copy of this turn criteria
   */
  public TurnCriteria copy(){
    return new ConstantIncrease(this.players);
  }
}
