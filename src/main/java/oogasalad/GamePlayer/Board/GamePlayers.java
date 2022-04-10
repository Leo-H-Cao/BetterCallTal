package oogasalad.GamePlayer.Board;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to hold the players in the game and their information. Has methods to access
 * different information about the players.
 *
 * @param players The players in the game
 * @author Ritvik Janamsetty
 */
public record GamePlayers(List<Player> players) {

  /**
   * This class is used to hold the players in the game and their information. Has methods to access
   * different information about the players. Overloaded constructor to create a GamePlayers with an
   * array of players.
   *
   * @param players The players in the game
   */
  public GamePlayers(Player... players) {
    this(List.of(players));
  }

  /**
   * This class is used to hold the players in the game and their information. Has methods to access
   * different information about the players. Overloaded constructor to create a GamePlayers with an
   * iterable of players.
   *
   * @param players The players in the game
   */
  public GamePlayers(Iterable<Player> players) {
    this(iterableToList(players));
  }

  /**
   * Converts an iterable to a list for the purpose of creating a GamePlayers.
   *
   * @param players The iterable containing the players to convert.
   * @return A list containing the players.
   */
  private static List<Player> iterableToList(Iterable<Player> players) {
    List<Player> playerList = new ArrayList<>();
    players.forEach(playerList::add);
    return playerList;
  }

  /**
   * Gets the player object with the associated ID
   *
   * @param id of player
   * @return player with given id
   */
  public Player getPlayer(int id) {
    return players.get(Math.min(id, players.size() - 1));
  }

  /**
   * Get array containing all the players
   *
   * @return players list
   */
  public Player[] getPlayers() {
    return players.toArray(new Player[0]);
  }

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  public int[] getTeams() {
    return players.stream().mapToInt(Player::teamID).toArray();
  }


}
