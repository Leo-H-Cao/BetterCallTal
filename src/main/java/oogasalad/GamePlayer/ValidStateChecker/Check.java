package oogasalad.GamePlayer.ValidStateChecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
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
  public boolean isValid(ChessBoard board, int id) {

    List<Piece> targetPieces = board.targetPiece(id);

    List<Piece> allPieces = board.getPieces();

    for(Piece p : allPieces){
      if(p.getTeam()==id){
        continue;
      }
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
  }

  /**
   * This method checks if the target piece of the current team is in check
   * @param board The current board being played
   * @param piece that is moving
   * @param move that the piece is making
   * @return whether the target-piece is under attack
   */
  public boolean isValid(ChessBoard board, Piece piece,
      ChessTile move) throws EngineException {

    ChessBoard copy = board.makeHypotheticalMove(board.getTile(piece.getCoordinates()).getPiece().get(), move.getCoordinates());

    return isValid(copy, piece.getTeam());

    /*
    return board.stream()
        .flatMap(List::stream).toList().stream()
        .map(ChessTile::getPieces)
        .flatMap(List::stream).toList().stream()
        .filter(piece -> !piece.checkTeam(id))
        .anyMatch(piece -> piece.validCapture(targetPieces.stream()
            .map(Piece::getCoordinates)
            .collect(Collectors.toList()), board));

     */
  }
}
