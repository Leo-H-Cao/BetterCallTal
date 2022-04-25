package oogasalad.GamePlayer.ArtificialPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.CheckmateLoss;
import oogasalad.Frontend.Menu.LocalPlay.RemotePlayer.RemotePlayer;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.PieceValue;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;

public class Bot implements RemotePlayer {
  private TurnCriteria turnCriteria;
  private int team;
  private TurnManager turnManager;
  private DecisionTree decisionTree;
  private List<Utility> objectives;
  private static final String RESOURCE_PATH = "oogasalad.GamePlayer.BotGameModes";
  private String setting;

  private ArrayList<Piece> topMovePieces = new ArrayList<>();
  private ArrayList<ChessTile> topMoveTiles = new ArrayList<>();
  ArrayList<Double> utilityList  = new ArrayList<>();
  double maxUtility = Integer.MIN_VALUE;
  private int minimaxDepth = 2;
  private static final double RANDOMIZER_THRESHOLD = 0.001;


  public Bot(int team, TurnCriteria tc){
    this.team = team;
    turnCriteria = tc;
  }

  public Bot(TurnManager turnManager, String s) {
    this.turnManager = turnManager;
    objectives = new ArrayList<>();
    objectives.add(new CheckmateLoss());
    objectives.add(new PieceValue());
    setting = s;
  }

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws Throwable {

    if(board.isGameOver()){
      return new TurnUpdate(Set.of(), -1);
    }

    ResourceBundle botResources = ResourceBundle.getBundle(RESOURCE_PATH);
    String[] depthProbabilities = botResources.getString(setting).split(",");
    double[] depths = new double[depthProbabilities.length];
    for(int i=0; i<depthProbabilities.length; i++){
      depths[i] = Double.parseDouble(depthProbabilities[i]);
    }

    TurnUpdate result = getSettingBasedMove(board, currentPlayer, depths);
    return result;
    //return getMinimaxMove(board, currentPlayer, minimaxDepth);
    //return getRandomMove(board, currentPlayer);
  }

  private TurnUpdate getSettingBasedMove(ChessBoard board, int currentPlayer, double[] depths)
      throws Throwable {
    double seed = Math.random();
    if(seed < depths[0]){
      return getRandomMove(board,currentPlayer);
    }
    for(int i=0; i<depths.length; i++){
      seed -= depths[i];
      if(seed < RANDOMIZER_THRESHOLD){
        return getMinimaxMove(board, currentPlayer, i);
      }
    }
    return new TurnUpdate(Set.of(), -1);
  }

  private TurnUpdate getMinimaxMove(ChessBoard board, int currentPlayer, int depth)
      throws Throwable {
    List<Piece> playerPieces = new ArrayList<>();

    for(Piece p : board.getPieces()){
      if(p.getTeam()==currentPlayer && !p.getMoves(board).isEmpty()){
        playerPieces.add(p);
      }
    }
    utilityList  = new ArrayList<>();
    maxUtility = Integer.MIN_VALUE;

    topMovePieces = new ArrayList<>();
    topMoveTiles = new ArrayList<>();
    for(Piece p : playerPieces){
      for(ChessTile t : board.getMoves(p)){
        ChessBoard copy = board.makeHypotheticalMove(p, t.getCoordinates());
        copy.getTurnManagerData().turn().incrementTurn();

        evaluateChildNode(copy, p, t, depth);
      }
    }

    int seed = (int) (Math.random() * topMovePieces.size());
    //return board.move(movingPiece, finalSquare.getCoordinates());
    return board.move(topMovePieces.get(seed), topMoveTiles.get(seed).getCoordinates());
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

  private void evaluateChildNode(ChessBoard copy, Piece p, ChessTile t, int depth)
      throws EngineException {
    DecisionNode childNode = new DecisionNode(copy, turnCriteria);
    double util = childNode.calculateUtility(objectives, depth);
    utilityList.add(util);
    if (util >= maxUtility) {
      if (util > maxUtility) {
        topMovePieces.clear();
        topMoveTiles.clear();
      }
      maxUtility = util;
      topMovePieces.add(p);
      topMoveTiles.add(t);
    }
  }

  @Override
  public TurnUpdate getRemoteMove(ChessBoard board, int currentPlayer) throws Throwable {
    return getBotMove(board, currentPlayer);
  }
}
