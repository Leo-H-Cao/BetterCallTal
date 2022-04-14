package oogasalad.Editor.ModelState.RulesState;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Leo Cao
 * Tracks game rules configurations changes from user
 */
public class GameRulesState {
  private static final String CONFIGURATION_RESOURCE_PATH = "oogasalad/Editor/GameRules";

  private ArrayList<String> winConditions;
  private String turnCriteria;
  private ResourceBundle myResources;
  private ArrayList<String> colors;

  public GameRulesState(){
    setResources();
    winConditions = new ArrayList<>();
    winConditions.add(myResources.getString("defaultConditionsCheckmate"));
    winConditions.add(myResources.getString("defaultConditionsStalemate"));
    turnCriteria = myResources.getString("defaultTurnCriteria");
    this.colors = new ArrayList<>();
    this.colors.add(myResources.getString("defaultColor0"));
    this.colors.add(myResources.getString("defaultColor1"));
  }

  /**
   * Tracks win conditions for the game that is being created
   * @param winConditions list of win conditions
   */
  public void setWinConditions(ArrayList<String> winConditions) {
    this.winConditions = winConditions;
  }

  /**
   * Tracks turn criteria (default is linear) for current game being created
   * @param turnCriteria string representing chosen turn criteria
   */
  public void setTurnCriteria(String turnCriteria){
    this.turnCriteria = turnCriteria;
  }

  /**
   * Getter for turn criteria set by user
   * @return string representing turn criteria
   */
  public String getTurnCriteria(){
    return turnCriteria;
  }

  public ArrayList<String> getWinConditions() {
    return winConditions;
  }

  public ArrayList<String> getColors() {
    return colors;
  }

  public void setColors(ArrayList<String> colors) {
    this.colors = colors;
  }

  private void setResources() {
    try {
      myResources = ResourceBundle.getBundle(CONFIGURATION_RESOURCE_PATH, Locale.ENGLISH);
    } catch (NullPointerException | MissingResourceException e) {
      throw new IllegalArgumentException(String.format("Invalid resource file: %s", "GameRules"));
    }
  }
}
