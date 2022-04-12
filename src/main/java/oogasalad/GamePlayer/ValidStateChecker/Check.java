package oogasalad.GamePlayer.ValidStateChecker;

import java.util.List;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.GamePiece.Piece;

/**
 * @author Jose Santillan
 * @author Jed Yang
 */
public class Check implements ValidStateChecker {

  /**
   * This method checks if the target piece of the current team is in check
   * @param board The current board being played
   * @param id The id of the pieces that are being attacked
   * @return whether the target-piece is under attack
   */
  public boolean isValid(ChessBoard board, int id) throws OutsideOfBoardException {

    List<Piece> targetPieces = board.targetPiece(id);

    List<Piece> allPieces = board.getPieces();

    for(Piece p : allPieces){
      Set<ChessTile> enemyMoves = p.getMoves(board);
      for(ChessTile t : enemyMoves){
        for(Piece target : targetPieces){
          if(t.getCoordinates().equals(target.getCoordinates())){
            return false;
          }
        }
      }
    }
    return true;

    /*
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> !piece.checkTeam(id))
        .anyMatch(piece -> piece.validCapture(targetPieces.stream()
            .map(Piece::getCoordinates)
<<<<<<< HEAD:src/main/java/oogasalad/GamePlayer/GameClauses/CheckValidator.java
            .collect(Collectors.toList())));


  }
=======
            .collect(Collectors.toList()), board));
>>>>>>> 53af6a11edc1f4e0b832ceb5ee55258244d2ff85:src/main/java/oogasalad/GamePlayer/ValidStateChecker/Check.java

     */
  }
}
