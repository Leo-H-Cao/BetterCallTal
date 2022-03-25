package oogasalad.GamePlayer.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class ChessBoard {

  private ChessTile[][] board;
  private TurnCriteria turnCriteria;
  private int numTeams;
  private List<EndCondition> endConditions;
  public ChessBoard(int length, int height, TurnCriteria turnCriteria, int numTeams, List<EndCondition> endConditions) {
    board = new ChessTile[length][height];
    this.turnCriteria = turnCriteria;
    this.numTeams = numTeams;
    this.endConditions = endConditions;
  }
  /***
   * Moves the piece on fromSquare to toSquare
   *
   * @param piece to move
   * @param toSquare end square
   * @return set of updated tiles
   */
  public Set<ChessTile> move(Piece piece, ChessTile toSquare) throws InvalidMoveException {
    return null;
  }

  /***
   * Returns all possible moves a piece can make
   *
   * @param piece to get moves from
   * @return set of tiles the piece can move to
   */
  public Set<ChessTile> getMoves(Piece piece) {
    return null;
  }

  /***
   * @return if the game is over
   */
  public boolean isGameOver() {
    return false;
  }

  /***
   * @return scores of all teams after game is over. If game isn't over, an empty map is returned.
   */
  public Map<Integer, Double> getScores() {
    return null;
  }
}
