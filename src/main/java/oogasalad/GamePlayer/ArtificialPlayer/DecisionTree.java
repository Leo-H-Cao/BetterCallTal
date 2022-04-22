package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;

public class DecisionTree {
  private DecisionNode root;
  private ArrayList<Utility> objectives;
  private ChessBoard boardState;
  private int currentPlayer;

  public DecisionTree(ChessBoard boardState, int currentPlayer){
    this.boardState = boardState;
    this.currentPlayer = currentPlayer;
  }

  public void evaluatePosition(ChessBoard position, int player, int depth){

  }

  private void generateTree(ChessBoard position, int player, int depth){

  }

}
