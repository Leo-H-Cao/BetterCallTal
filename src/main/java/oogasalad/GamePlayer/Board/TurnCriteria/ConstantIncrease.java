package oogasalad.GamePlayer.Board.TurnCriteria;

import oogasalad.GamePlayer.Board.Player;

public class ConstantIncrease extends TurnCriteria {

  public static final int TURN_INCREMENT_NUM = 1;

  private int numMovesTotal;
  private int numMovesPlayed;
  
  public ConstantIncrease(Player[] players) {
    super(players);
    numMovesTotal = 1;
  }

  @Override
  public int incrementTurn() {
    numMovesPlayed++;
    if(numMovesTotal == numMovesPlayed) {
      incrementIndex(TURN_INCREMENT_NUM);
      numMovesTotal++;
    }
    return getCurrentPlayer();
  }
}
