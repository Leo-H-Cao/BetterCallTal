package oogasalad.GamePlayer.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
  public Set<ChessTile> move(Piece piece, ChessTile toSquare) {
    return null;
  }


}
