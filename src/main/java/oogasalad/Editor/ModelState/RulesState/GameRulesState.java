package oogasalad.Editor.ModelState.RulesState;

import java.util.List;
import oogasalad.Editor.API.ModifiesRulesState;

public class GameRulesState implements ModifiesRulesState {

  private GameRules myGameRules;

  public GameRulesState(){
    myGameRules = new GameRules();
  }

  @Override
  public void setWinConditions(List<String> winConditions) {
    myGameRules.setWinConditions(winConditions);
  }

  @Override
  public void setTurnCriteria(String turnCriteria) {
    myGameRules.setTurnCriteria(turnCriteria);
  }

  //For testing
  public GameRules getGameRules(){
    return myGameRules;
  }
}
