package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.ArrayList;
import java.util.Collection;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

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

  /**
   * Creates a new TurnManagerData object from an existing TurnManager.
   *
   * @param turnManager the existing TurnManager.
   */
  public TurnManagerData(TurnManager turnManager) {
    this(turnManager.getGamePlayers(), turnManager.getTurnCriteria(),
        turnManager.getEndConditions(), turnManager.getLink());
  }

  /**
   * Creates an empty TurnManagerData object.
   */
  public TurnManagerData() {
    this(new GamePlayers(), new Linear(new Player[1]), new ArrayList<>(), "");
  }

  /**
   * Creates a new TurnManager from the data stored in this object.
   *
   * @return the new TurnManager.
   */
  public TurnManager toTurnManager() {
    return new LocalTurnManager(this);
  }

  public TurnManagerData copy(){
    return new TurnManagerData(this.players, this.turn.copy(), this.conditions, "");
  }

}
