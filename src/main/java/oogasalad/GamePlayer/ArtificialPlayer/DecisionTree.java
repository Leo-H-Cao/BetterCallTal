package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.List;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class DecisionTree {
  private DecisionNode root;
  private List<Utility> objectives;
  private ChessBoard boardState;
  private int currentPlayer;

  private TurnCriteria turnCriteria;
  private ArrayList<Piece> topMovePieces = new ArrayList<>();
  private ArrayList<ChessTile> topMoveTiles = new ArrayList<>();
  ArrayList<Double> utilityList  = new ArrayList<>();
  double maxUtility = Integer.MIN_VALUE;


  public DecisionTree(ChessBoard boardState, int currentPlayer, List<Utility> objectives){
    this.boardState = boardState;
    this.currentPlayer = currentPlayer;
    this.objectives = objectives;
  }

  public TurnUpdate evaluatePosition(ChessBoard board, int player, int depth) throws EngineException {
    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }
    utilityList  = new ArrayList<>();
    maxUtility = Integer.MIN_VALUE;

    topMovePieces = new ArrayList<>();
    topMoveTiles = new ArrayList<>();
    for(Piece p : playerPieces){
      for(ChessTile t : board.getMoves(p)){
        ChessBoard copy = board.makeHypotheticalMove(p, t.getCoordinates());
        copy.getTurnManagerData().turn().incrementTurn();

        evaluateChildNode(copy, p, t, depth);
      }
    }

    int seed = (int) (Math.random() * topMovePieces.size());
    //return board.move(movingPiece, finalSquare.getCoordinates());
    return board.move(topMovePieces.get(seed), topMoveTiles.get(seed).getCoordinates());

  }


  private void evaluateChildNode(ChessBoard copy, Piece p, ChessTile t, int depth)
      throws EngineException {
    DecisionNode childNode = new DecisionNode(copy, turnCriteria);
    double util = childNode.calculateUtility(objectives, depth);
    utilityList.add(util);
    if (util >= maxUtility) {
      if (util > maxUtility) {
        topMovePieces.clear();
        topMoveTiles.clear();
      }
      maxUtility = util;
      topMovePieces.add(p);
      topMoveTiles.add(t);
    }
  }



}
