package oogasalad.GamePlayer.ArtificialPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.CheckmateLoss;
import oogasalad.Frontend.Menu.LocalPlay.RemotePlayer.RemotePlayer;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.KingOfTheHillWin;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.PieceValue;
import oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.Utility;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.EndConditions.EndCondition;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.Board.TurnCriteria.TurnCriteria;
import oogasalad.GamePlayer.Board.TurnManagement.TurnManager;
import oogasalad.GamePlayer.Board.TurnManagement.TurnUpdate;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import org.apache.catalina.Engine;

/***
 * Generalized AI player for all supported chess variants
 *
 * @author Jed
 */

public class Bot implements RemotePlayer {
  private TurnCriteria turnCriteria;
  private int team;
  private TurnManager turnManager;
  private DecisionTree decisionTree;
  private List<Utility> objectives;
  private static final String RESOURCE_PATH = "oogasalad.GamePlayer.BotGameModes";
  private static final String OBJECTIVES_PATH = "oogasalad.GamePlayer.BotObjectives";
  private static final String UTILITY_FUNCTIONS_PATH = "oogasalad.GamePlayer.ArtificialPlayer.UtilityFunctions.";
  private String setting;
  private boolean firstMove = true; //TODO: get the endconditions upon setup to complete setup of bot before a move is made


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
    try {
      setSpecialObjectives(turnManager.getEndConditions());
    } catch (Exception e) {
      return;
    }

    setting = s;
  }


  /**
   *
   * @param board
   * @param currentPlayer
   * @return
   * @throws Throwable
   */

  @Override
  public TurnUpdate getRemoteMove(ChessBoard board, int currentPlayer) throws Throwable {
    return getBotMove(board, currentPlayer);
  }

  /**
   * This method returns a TurnUpdate of the bot's move when it is asked to make a move
   * @param board the current state of the board
   * @param currentPlayer the bot's player ID
   * @return a TurnUpdate for the GameView to update the board view
   * @throws Throwable runtime exception
   */

  public TurnUpdate getBotMove(ChessBoard board, int currentPlayer)
      throws Throwable {

    if(board.isGameOver()){
      return new TurnUpdate(Set.of(), -1, "");
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

  private void setSpecialObjectives(Collection<EndCondition> endConditions)
      throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
    ResourceBundle botObjectiveResources = ResourceBundle.getBundle(OBJECTIVES_PATH);
    for(EndCondition condition : endConditions){
      String conditionClass = condition.getClass().getSimpleName();
      if(botObjectiveResources.containsKey(conditionClass)){
        String objectiveName = botObjectiveResources.getString(conditionClass);
        Class<?> clazz = Class.forName(UTILITY_FUNCTIONS_PATH+objectiveName);
        Constructor<?> ctor = clazz.getConstructor();
        Utility objective = (Utility) ctor.newInstance();
        objectives.add(objective);
      }
    }

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
    return new TurnUpdate(Set.of(), -1, "");
  }

  //best move according to the minimax algorithm
  private TurnUpdate getMinimaxMove(ChessBoard board, int currentPlayer, int depth)
      throws Throwable {
    DecisionTree decisionTree = new DecisionTree(board, currentPlayer, objectives);
    return decisionTree.evaluatePosition(board, currentPlayer, depth);
  }



  //random legal move
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
