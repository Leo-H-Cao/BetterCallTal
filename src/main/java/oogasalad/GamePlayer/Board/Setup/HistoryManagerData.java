package oogasalad.GamePlayer.Board.Setup;

import oogasalad.GamePlayer.Board.History.HistoryManager;

/**
 * This class is used to store the data of the history manager when being sent via the network.
 *
 * @param historyAPI the API url of the history manager
 * @author Rivik Janamsetty
 */
public record HistoryManagerData(String historyAPI) {

  /**
   * Creates the history manager data from the history manager.
   *
   * @param historyAPI the history manager to create the data from.
   */
  public HistoryManagerData(HistoryManager historyAPI) {
    this(historyAPI.getLink());
  }

}
