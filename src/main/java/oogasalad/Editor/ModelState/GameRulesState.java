package oogasalad.Editor.ModelState;

import java.util.List;
import oogasalad.Editor.API.ModifiesRulesState;
import oogasalad.Editor.ModelState.PiecesState.EditorPiece;
import oogasalad.Editor.ModelState.PiecesState.MovementRules;

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
