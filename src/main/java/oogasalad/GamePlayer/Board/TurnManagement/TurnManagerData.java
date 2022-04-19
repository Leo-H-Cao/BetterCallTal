package oogasalad.GamePlayer.Board.TurnManagement;

import java.util.Collection;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

public record TurnManagerData(GamePlayers players, TurnCriteria turn,
                              Collection<EndCondition> conditions) {

}
