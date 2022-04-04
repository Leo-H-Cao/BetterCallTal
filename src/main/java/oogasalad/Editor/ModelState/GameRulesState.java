package oogasalad.Editor.ModelState;

import java.util.List;

public class GameRulesState extends ModelState implements
    oogasalad.Editor.API.ModifiesRulesState {

  public GameRulesState(){
    super();
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
