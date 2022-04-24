package oogasalad.Editor.ModelState.RulesState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import oogasalad.Editor.ExportJSON.ExportJSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Leo Cao
 * Tracks game rules configurations changes from user
 */
public class GameRulesState {
  private static final Logger LOG = LogManager.getLogger(ExportJSON.class);
  private static final String CONFIGURATION_RESOURCE_PATH = "oogasalad/Editor/GameRules";

  private ArrayList<ArrayList<String>> winConditions;
  private String turnCriteria;
  private ResourceBundle myResources;
  private ArrayList<String> colors;
  private HashMap<Integer, ArrayList<Integer>> teamOpponents;
  private ArrayList<ArrayList<String>> validStateCheckers;

  public GameRulesState(){
    setResources();
    winConditions = new ArrayList<>();
    colors = new ArrayList<>();
    teamOpponents = new HashMap<>();
    validStateCheckers = new ArrayList<>();
    setDefaults();
  }

  /**
   * Tracks win conditions for the game that is being created
   * @param winConditions list of win conditions
   */
  public void setWinConditions(ArrayList<ArrayList<String>> winConditions) {
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

  public ArrayList<ArrayList<String>> getWinConditions() {
    return winConditions;
  }

  public ArrayList<String> getColors() {
    return colors;
  }

  public void setColors(ArrayList<String> colors) {
    this.colors = colors;
  }

  public HashMap<Integer, ArrayList<Integer>> getTeamOpponents() {
    return teamOpponents;
  }

  public void setTeamOpponents(HashMap<Integer, ArrayList<Integer>> teamOpponents) {
    this.teamOpponents = teamOpponents;
  }

  public ArrayList<ArrayList<String>> getValidStateCheckers() {
    return validStateCheckers;
  }

  public void setValidStateCheckers(ArrayList<ArrayList<String>> validStateCheckers) {
    this.validStateCheckers = validStateCheckers;
  }

  private void setResources() {
    try {
      myResources = ResourceBundle.getBundle(CONFIGURATION_RESOURCE_PATH, Locale.ENGLISH);
    } catch (NullPointerException | MissingResourceException e) {
      LOG.warn(String.format("Invalid resource file: %s", CONFIGURATION_RESOURCE_PATH));
    }
  }

  private void setDefaults(){
    ArrayList<String> defaultConditionsCheckmate = new ArrayList<>();
    defaultConditionsCheckmate.add(myResources.getString("defaultConditionsCheckmate"));
    ArrayList<String> defaultConditionsStalemate = new ArrayList<>();
    defaultConditionsStalemate.add(myResources.getString("defaultConditionsStalemate"));
    winConditions.add(defaultConditionsCheckmate);
    winConditions.add(defaultConditionsStalemate);

    turnCriteria = myResources.getString("defaultTurnCriteria");
    colors.add(myResources.getString("defaultColor0"));
    colors.add(myResources.getString("defaultColor1"));

    int team0 = Integer.parseInt(myResources.getString("defaultTeam0"));
    int team1 = Integer.parseInt(myResources.getString("defaultTeam1"));
    teamOpponents.put(team0, new ArrayList<>());
    teamOpponents.put(team1, new ArrayList<>());
    teamOpponents.get(team0).add(team1);
    teamOpponents.get(team1).add(team0);

    ArrayList<String> defaultValidStateCheckers = new ArrayList<>();
    defaultValidStateCheckers.add(myResources.getString("defaultValidStateCheckers"));
    validStateCheckers.add(defaultValidStateCheckers);
  }
}
