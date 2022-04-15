package oogasalad.GamePlayer.Board.TurnManagement;


import java.util.List;
import oogasalad.GamePlayer.Board.Player;

/**
 * This class is used to hold the players in the game and their information. Has methods to access
 * different information about the players.
 *
 * @param players     The players in the game
 * @param teamNumbers the team numbers of the players
 * @author Ritvik Janamsetty
 */
public record GamePlayers(List<Player> players, List<Integer> teamNumbers) {

  /**
   * This class is used to hold the players in the game and their information. Has methods to access
   * different information about the players.
   *
   * @param players The players in the game
   */
  public GamePlayers(List<Player> players) {
    this(players, getTeamNums(players));
  }

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
   * Convert players to team numbers
   *
   * @return team nums associated with each player
   */
  private static List<Integer> getTeamNums(List<Player> players) {
    return players.stream().mapToInt(Player::teamID).boxed().toList();
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
  public Player[] getPlayersArr() {
    return players.toArray(new Player[0]);
  }

  /**
   * Gets all team numbers
   *
   * @return team numbers for all players
   */
  public int[] getTeams() {
    return teamNumbers.stream().mapToInt(Integer::intValue).toArray();
  }
}
