package oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions;

import java.util.HashSet;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.OutsideOfBoardException;
import oogasalad.GamePlayer.Movement.Coordinate;

public class KingOfTheHillWin extends Utility {
  private final int AGGRESSION_FACTOR = 3;

  public KingOfTheHillWin(){
    super();
  }

  public double getUtility(int player, ChessBoard board){
    Set<Coordinate> centerTiles = new HashSet<>();
    try {
      centerTiles = calculateCenterTiles(board);
    } catch (OutsideOfBoardException e) {
      return Integer.MAX_VALUE;
    }
    int teamDistance = distanceFromEndZone(board.targetPiece(player).get(0).getCoordinates(), centerTiles);
    int opponentDistance = distanceFromEndZone(board.targetPiece(1-player).get(0).getCoordinates(), centerTiles);
    int util = (AGGRESSION_FACTOR * (opponentDistance - teamDistance));
    return util;
  }

  private Set<Coordinate> calculateCenterTiles(ChessBoard board) throws OutsideOfBoardException {
    double[] center = new double[2];
    center[0] = (double)(board.getBoardHeight()-1)/2;
    center[1] = (double)(board.getBoardLength()-1)/2;
    HashSet<Coordinate> centerTiles = new HashSet<>();
    for(int i=0; i<board.getBoardHeight(); i++){
      for(int j=0; j<board.getBoardLength(); j++){
        Coordinate location = new Coordinate(i, j);
        if((Math.abs(location.getRow()-center[0]) < 1)&& (Math.abs(location.getCol() - center[1]) < 1 )){
          centerTiles.add(location);
        }
      }
    }

    return centerTiles;
  }

  private int distanceFromEndZone(Coordinate point, Set<Coordinate> centerTiles){
    int min = Integer.MAX_VALUE;
    for(Coordinate t : centerTiles){
      int dx = Math.abs(t.getRow() - point.getRow());
      int dy = Math.abs(t.getCol() - point.getCol());
      int distance = dx + dy;
      if(distance < min){
        min = distance;
      }
    }
    return min;
  }

}
