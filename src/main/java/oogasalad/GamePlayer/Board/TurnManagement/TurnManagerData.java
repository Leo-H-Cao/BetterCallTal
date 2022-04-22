package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Collection;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.GamePlayers;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;

/**
 * This class is used to store the data needed to setup a turn manager.
 *
 * @param conditions The end conditions of the game.
 * @param turn       The turn criteria of the game.
 * @param players    The players of the game.
 * @param link       the API link of the game.
 * @author Ritvik Janamsetty
 */
public record TurnManagerData(GamePlayers players, TurnCriteria turn,
                              Collection<EndCondition> conditions, String link) {

  public TurnManagerData(TurnManager turnManager) {
    this(turnManager.getGamePlayers(), turnManager.getTurnCriteria(),
        turnManager.getEndConditions(), turnManager.getLink());
  }

}
