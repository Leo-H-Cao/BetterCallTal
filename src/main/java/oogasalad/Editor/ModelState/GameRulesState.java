package oogasalad.Editor.Backend.ModelState;

import java.util.List;
import oogasalad.Editor.Backend.API.ModifiesRulesState;
import oogasalad.Editor.Backend.ModelState.GameRules;
import oogasalad.Editor.Backend.ModelState.ModelState;

public class GameRulesState extends ModelState implements ModifiesRulesState {

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