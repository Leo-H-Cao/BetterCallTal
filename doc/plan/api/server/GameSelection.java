package oogasalad.server;

import org.json.simple.JSONObject;

public interface Game {

  /**
   * Gets the active games
   *
   * @return all active games
   */
  public JSONObject getActiveGames();

  /**
   * Creates a new game with the given properties
   *
   * @param properties the properties of the new game
   */
  public JSONObject createGame(JSONObject properties);

  /**
   * Gets the game with the given id
   *
   * @param properties
   * @return the game with the given id
   */
  public JSONObject getGame(String id);

  /**
   * Adds a player to the game with the given id
   *
   * @param id the id of the game
   * @return the game with the given id
   */
  public JSONObject joinGame(String id);

  /**
   * Ends the game with the given id
   *
   * @param id the id of the game
   * @return the game with the given id
   */
  public JSONObject endGame(String id);
}