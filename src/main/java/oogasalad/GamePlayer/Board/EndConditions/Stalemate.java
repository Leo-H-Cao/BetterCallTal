package oogasalad.GamePlayer.Board.EndConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.ValidStateChecker.Check;

public class Stalemate implements EndCondition{


  public boolean hasNoLegalMoves(ChessBoard board, int id) throws EngineException {
    Set<Piece> friendlyPieces = friendlyPieces(board, id);
    for(Piece p : friendlyPieces){
      for(ChessTile t : p.getMoves(board)){
        ChessBoard newPosition = board.makeHypotheticalMove(p, t.getCoordinates());
        if((new Check().isValid(newPosition, p.getTeam()))){
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
    return new HashMap<>();
  }
}
