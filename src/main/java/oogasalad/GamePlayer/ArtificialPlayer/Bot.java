package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.CheckmateLoss;
import oogasalad.Frontend.Menu.LocalPlay.RemotePlayer.RemotePlayer;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.PieceValue;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Bot implements RemotePlayer {
  private TurnCriteria turnCriteria;
  private int team;
  private TurnManager turnManager;
  private DecisionTree decisionTree;
  private List<Utility> objectives;


  public Bot(int team, TurnCriteria tc){
    this.team = team;
    turnCriteria = tc;
  }



  public Bot(TurnManager turnManager) {
    this.turnManager = turnManager;
    objectives = new ArrayList<>();
    objectives.add(new CheckmateLoss());
    objectives.add(new PieceValue());
  }

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws Throwable {

    if(board.isGameOver()){
      return new TurnUpdate(null, -1);
    }


    return getMinimaxMove(board, currentPlayer, 2);
    //return getRandomMove(board, currentPlayer);
  }

  private TurnUpdate getMinimaxMove(ChessBoard board, int currentPlayer, int depth)
      throws Throwable {
    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }

    //maximize utility. TODO: move logic to DecisionTree class
    ArrayList<Double> utilityList  = new ArrayList<>();
    double maxUtility = Integer.MIN_VALUE;
    Piece movingPiece = null;
    ChessTile finalSquare = null;

    ArrayList<Piece> topMovePieces = new ArrayList<>();
    ArrayList<ChessTile> topMoveTiles = new ArrayList<>();

    for(Piece p : playerPieces){
      for(ChessTile t : board.getMoves(p)){
        ChessBoard copy = board.makeHypotheticalMove(p, t.getCoordinates());
        copy.getTurnManagerData().turn().incrementTurn();

        DecisionNode childNode = new DecisionNode(copy, turnCriteria);
        double util = childNode.calculateUtility(objectives, depth);
        utilityList.add(util);
        if(util >= maxUtility){
          if(util > maxUtility){
            topMovePieces.clear();
            topMoveTiles.clear();
          }
          maxUtility = util;
          movingPiece = p;
          finalSquare = t;
          topMovePieces.add(p);
          topMoveTiles.add(t);

        }
      }
    }

    int seed = (int) (Math.random() * topMovePieces.size());
    //return board.move(movingPiece, finalSquare.getCoordinates());
    return board.move(topMovePieces.get(seed), topMoveTiles.get(seed).getCoordinates());
  }



  private TurnUpdate getRandomMove(ChessBoard board, int currentPlayer) throws Throwable {
    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }


    Set<ChessTile> tileSet = board.getMoves(playerPieces.get(0));
    Piece movingPiece = playerPieces.get(0);
    Collections.shuffle(playerPieces);
    for(Piece p : playerPieces){
      if(!board.getMoves(p).isEmpty()){
        tileSet = board.getMoves(p);
        movingPiece = p;
      }

    }

    ChessTile finalSquare = null;
    for(ChessTile t : tileSet){
      finalSquare = t;
      break;
    }
    return board.move(movingPiece, finalSquare.getCoordinates());
    //return new TurnUpdate(movingPiece.move(finalSquare, board), turnManager.incrementTurn());
  }

  @Override
  public TurnUpdate getRemoteMove(ChessBoard board, int currentPlayer) throws Throwable {
    return getBotMove(board, currentPlayer);
  }
}
