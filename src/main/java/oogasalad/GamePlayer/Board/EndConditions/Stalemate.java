package oogasalad.GamePlayer.Board.EndConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Stalemate implements EndCondition{


  public static boolean isStaleMate(ChessBoard board, int id){
    Set<Piece> friendlyPieces = friendlyPieces(board, id);
    for(Piece p : friendlyPieces){
      if(!p.getMoves(board).isEmpty()){
        return false;
      }
    }
    return true;
  }


  public static Set<Piece> friendlyPieces(ChessBoard board, int id) {
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> piece.checkTeam(id))
        .collect(Collectors.toSet());
  }

  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return new HashMap<>();
  }
}
