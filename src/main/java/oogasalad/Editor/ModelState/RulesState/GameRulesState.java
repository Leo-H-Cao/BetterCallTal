package oogasalad.Editor.ModelState.RulesState;

import java.util.List;

public class GameRulesState {

  private GameRules myGameRules;

  public GameRulesState(){
    myGameRules = new GameRules();
  }

  public void setWinConditions(List<String> winConditions) {
    myGameRules.setWinConditions(winConditions);
  }

  public void setTurnCriteria(String turnCriteria) {
    myGameRules.setTurnCriteria(turnCriteria);
  }

  //For testing
  public GameRules getGameRules(){
    return myGameRules;
  }
}
