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
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
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
    objectives.add(new Checkmate());
    objectives.add(new PieceValue());
  }

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws Throwable {

    if(board.isGameOver()){
      return new TurnUpdate(null, -1);
    }

    return getRandomMove(board, currentPlayer);
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

}
