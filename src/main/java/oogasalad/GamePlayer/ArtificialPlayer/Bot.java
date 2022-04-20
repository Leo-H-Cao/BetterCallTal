package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
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

  public Bot(int team, TurnCriteria tc){
    this.team = team;
    turnCriteria = tc;
  }



  public Bot(TurnManager turnManager) {
    this.turnManager = turnManager;
  }

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws EngineException {

    if(board.isGameOver()){
      return new TurnUpdate(null, -1);
    }

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
    return new TurnUpdate(movingPiece.move(finalSquare, board), turnCriteria.incrementTurn());
  }

}
