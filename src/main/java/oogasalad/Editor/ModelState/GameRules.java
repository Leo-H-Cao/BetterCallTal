package oogasalad.Editor.Backend.ModelState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GameRules {
  private static final String CONFIGURATION_RESOURCE_PATH = "Editor/Backend/GameRules";

  private List<String> winConditions;
  private String turnCriteria;
  private ResourceBundle myResources;

  public GameRules(){
    setResources();
    winConditions = new ArrayList<>();
    winConditions.add(myResources.getString("defaultConditionsCheckmate"));
    winConditions.add(myResources.getString("defaultConditionsStalemate"));
    turnCriteria = myResources.getString("defaultTurnCriteria");
  }

  private void setResources() {
    try {
      myResources = ResourceBundle.getBundle(CONFIGURATION_RESOURCE_PATH, Locale.ENGLISH);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", "pen"));
    }
  }

  public void setWinConditions(List<String> winConditions) {
    this.winConditions = winConditions;
  }

  public void setTurnCriteria(String turnCriteria){
    this.turnCriteria = turnCriteria;
  }

  public String getTurnCriteria(){
    return turnCriteria;
  }
}
