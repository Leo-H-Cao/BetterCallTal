package oogasalad.GamePlayer.Board.EndConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Player;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.ValidStateChecker.Check;

/**
 * End condition where a player reaches stalemate
 *
 * @author Jed Yang
 */

public class Stalemate implements EndCondition{
  private Check check = new Check();

  /**
   * Empty constructor used for Jackson serialization and deserialization
   */
  public Stalemate(){
    super();
  }

  boolean isStalemate(ChessBoard board) throws EngineException{
    //for all teams, check if there are legal moves left
//    for(int i : board.getTeams()){
//      if(hasNoLegalMoves(board, board.getCurrentPlayer()) && check.isValid(board, board.getCurrentPlayer())){
//        return true;
//      }
//    }
    return hasNoLegalMoves(board, board.getCurrentPlayer()) && check.isValid(board, board.getCurrentPlayer());
  }

  public boolean hasNoLegalMoves(ChessBoard board, int id) throws EngineException {
    Set<Piece> friendlyPieces = friendlyPieces(board, id);
    for(Piece p : friendlyPieces){
      for(ChessTile t : p.getMoves(board)){
        if((new Check().isValid(board, p, board.getTile(t.getCoordinates())))){
          return false;
        }
      }

    }
    return true;
  }


  public Set<Piece> friendlyPieces(ChessBoard board, int id) {
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(id))
        .collect(Collectors.toSet());
  }

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    try {
      if(isStalemate(board)){
        return Arrays.stream(board.getPlayers()).collect(
            Collectors.toMap(Player::teamID, p -> DRAW));
      }
    } catch (EngineException e) {
      e.printStackTrace(); //TODO: handle this exception
    }

    return new HashMap<>();
  }

  /**
   * @return 0
   */
  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
