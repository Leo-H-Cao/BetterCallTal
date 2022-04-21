package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Checkmate;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.PieceValue;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.Linear;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Bot {
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
    //objectives.add(new Checkmate());
    objectives.add(new PieceValue());
  }

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws EngineException {

    if(board.isGameOver()){
      return new TurnUpdate(null, -1);
    }


    return getMinimaxMove(board, currentPlayer, 1);
    //return getRandomMove(board, currentPlayer);
  }

  private TurnUpdate getMinimaxMove(ChessBoard board, int currentPlayer, int depth)
      throws EngineException {
    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }

    //maximize utility. TODO: move logic to DecisionTree class
    double maxUtility = Integer.MIN_VALUE;
    Piece movingPiece = null;
    ChessTile finalSquare = null;
    for(Piece p : playerPieces){
      for(ChessTile t : board.getMoves(p)){
        ChessBoard copy = board.makeHypotheticalMove(p, t.getCoordinates());
        DecisionNode childNode = new DecisionNode(copy, turnCriteria);
        double util = childNode.calculateUtility(objectives, depth);
        if(util > maxUtility){
          maxUtility = util;
          movingPiece = p;
          finalSquare = t;

        }
      }
    }

    return board.move(movingPiece, finalSquare.getCoordinates());
  }



  private TurnUpdate getRandomMove(ChessBoard board, int currentPlayer) throws EngineException {
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

}
