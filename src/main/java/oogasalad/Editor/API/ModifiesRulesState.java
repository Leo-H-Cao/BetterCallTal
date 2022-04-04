package oogasalad.Editor.API;

import java.util.List;

public interface ModifiesRulesState {

  void setWinConditions(List<String> winConditions);
  void setTurnCriteria(String turnCriteria);

}