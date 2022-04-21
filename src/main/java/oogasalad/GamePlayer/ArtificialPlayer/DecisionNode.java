package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;

public class DecisionNode {
  private ChessBoard board;
  private double utility;
  private List<DecisionNode> children;
  private TurnCriteria turnCriteria;

  public DecisionNode(ChessBoard board, List<DecisionNode> children, TurnCriteria tc){
    this.board = board;
    this.children = children;
    this.turnCriteria = tc;
    this.turnCriteria = board.getTurnManagerData().turn();
  }

  public double calculateUtility(List<Utility> objectives){
    if(children.isEmpty()){
      //calculate current utility, which is the minimum of all objective utilities
      double minUtility = Double.MAX_VALUE;
      for(Utility u : objectives){
        double util = u.getUtility(turnCriteria.getCurrentPlayer(), board);
        if(util < minUtility){
          minUtility = util;
        }
      }
      return minUtility;
    }
    else{
      //run minimax
      //turnCriteria.incrementTurn();
    }
    return 0;
  }



}
