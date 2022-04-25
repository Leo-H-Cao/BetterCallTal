package oogasalad.Frontend.Game.History;

import java.util.ArrayList;
import java.util.List;

import oogasalad.GamePlayer.Board.ChessBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Jose Santillan
 */
public class BoardHistory implements History {

  private List<ChessBoard> myHistory;
  private int idx;

  private static final Logger LOG = LogManager.getLogger(BoardHistory.class);

  public BoardHistory() {
    myHistory = new ArrayList<>();
    idx = 0;
  }

  @Override
  public ChessBoard getBoard() {
    return myHistory.get(idx);
  }

  @Override
  public void add(ChessBoard board) {
    myHistory.add(board);
  }

  @Override
  public History next() {
    if (!inBounds(idx + 1)) return this;
    idx += 1;
    return this;
  }

  @Override
  public History previous() {
    if (!inBounds(idx - 1)) return this;
    idx -= 1;
    return this;
  }

  public void update(ChessBoard board) {
    add(board);
    idx = myHistory.size() - 1;
  }

  private boolean inBounds(int i) {
    return i >= 0 && i < myHistory.size();
  }

  public boolean isOnRecent() {
    return idx == myHistory.size() - 1;
  }
}
