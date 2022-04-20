package oogasalad.GamePlayer.Board;

import java.util.Arrays;

/***
 * Record containing relevant info for a player
 *
 * @author Vincent Chen
 */
public record Player(int teamID, int[] opponentIDs){

  @Override
  public String toString() {
    return teamID + ": " + Arrays.toString(opponentIDs);
  }
}
