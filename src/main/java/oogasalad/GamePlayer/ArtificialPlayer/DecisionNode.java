package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class DecisionNode {
  private ChessBoard board;
  private double utility;
  private List<DecisionNode> children;
  private TurnCriteria turnCriteria;

  public DecisionNode(ChessBoard board, TurnCriteria tc){
    this.board = board;
    this.turnCriteria = tc;
    this.turnCriteria = board.getTurnManagerData().turn();
  }

  public double calculateUtility(List<Utility> objectives, int remainingDepth)
      throws EngineException {
    if(remainingDepth == 1){
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

      TurnCriteria copyCriteria = board.getTurnManagerData().turn();
      List<Piece> playerPieces = new ArrayList<Piece>();
      for(Piece p : board.getPieces()){
        if (p.getTeam()==copyCriteria.getCurrentPlayer()){
          playerPieces.add(p);
        }
      }


      ArrayList<Double> utilityList = new ArrayList<>();
      double minUtility = Double.MAX_VALUE;
      for(Piece p : playerPieces){

        for(ChessTile t : board.getMoves(p)){
          ChessBoard copy = board.makeHypotheticalMove(p, t.getCoordinates());
          copy.getTurnManagerData().turn().incrementTurn();

          DecisionNode child = new DecisionNode(copy, copy.getTurnManagerData().turn());
          //recursion
          double util = child.calculateUtility(objectives, remainingDepth-1);
          utilityList.add(util);
          if(util < minUtility){
            minUtility = util;
          }
        }
      }
      int a = 0;//checkpoint for debugging

      return minUtility;
    }

  }



}
