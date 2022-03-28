package oogasalad.GamePlayer.Board;

import java.util.Arrays;

public record Player(int teamID, int[] opponentIDs){
  @Override
  public String toString() {
    return teamID + ": " + Arrays.toString(opponentIDs);
  }
}
