package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.Server.Managers.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.InvalidMoveException;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Bot {
  private TurnManager turnManager;

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws OutsideOfBoardException, InvalidMoveException {

    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }


    Collections.shuffle(playerPieces);
    Piece movingPiece = playerPieces.get(0);

    Set<ChessTile> tileSet = playerPieces.get(0).getMoves(board);
    ChessTile finalSquare = null;
    for(ChessTile t : tileSet){
      finalSquare = t;
      break;
    }
    return new TurnUpdate(movingPiece.move(finalSquare, board), turnManager.incrementTurn());
  }

}
