package oogasalad.server;

import org.json.simple.JSONObject;

public interface Game {

  /**
   * Gets the rules of the game.
   *
   * @return the rules of the game.
   */
  public JSONObject getRules();

  /**
   * Gets the rules of the game.
   *
   * @param rules the rules of the game.
   */
  public void setRules(JSONObject rules);

  /**
   * Gets the game's id.
   *
   * @return the game's id.
   */
  public String getId();

  /**
   * Gets the properties of the game.
   *
   * @return the properties of the game.
   */
  public JSONObject getProperties();

  /**
   * Sets the properties of the game.
   *
   * @param properties the properties of the game.
   */
  public void setProperties(JSONObject properties);

  /**
   * Gets the players of the game.
   *
   * @return the players of the game.
   */
  public JSONObject getPlayers();

  /**
   * Sets the players of the game.
   *
   * @param players the players of the game.
   */
  public void setPlayers(JSONObject players);

  /**
   * Gets the current player.
   *
   * @param playerOrder
   */
  public void setPlayerOrder(JSONObject playerOrder);

  /**
   * Gets the active player.
   *
   * @return the active player.
   */
  public JSONObject getActivePlayer();

}