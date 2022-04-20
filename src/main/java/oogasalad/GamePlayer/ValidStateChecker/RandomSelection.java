package oogasalad.GamePlayer.ValidStateChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * Selects a random subset of moves from the given piece that are valid
 *
 * @author Vincent
 */
public class RandomSelection implements ValidStateChecker {

  private static final String RS_NUM_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_RS_FILE = "RandomSelection1";
  private static final Logger LOG = LogManager.getLogger(RandomSelection.class);
  private static final int DEFAULT_RS_NUM = 1;

  private int numRandom;
  private Map<Piece, RandomGenerationInfo> currentRandomGenerations;

  /***
   * Creates RandomSelection with default config file
   */
  public RandomSelection() {
    this(DEFAULT_RS_FILE);
  }

  /***
   * Creates RandomSelection with given config file
   *
   * @param configFile to read
   */
  public RandomSelection(String configFile) {
    numRandom = FileReader.readOneInt(RS_NUM_FILE_PATH_HEADER + configFile, DEFAULT_RS_NUM);
    currentRandomGenerations = new HashMap<>();
  }

  /***
   * @return if a given move is valid based on a random selection
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece, ChessTile move) throws EngineException {
    Set<ChessTile> allMoves = piece.getMoves(board);
    if(allMoves.size() <= numRandom) return true;

    if(!currentRandomGenerations.containsKey(piece) ||
        !currentRandomGenerations.get(piece).allMoves().equals(allMoves)) {
      currentRandomGenerations.put(piece,
          RandomGenerationInfo.of(allMoves, generateAllowedMoves(allMoves)));
    }

    return currentRandomGenerations.get(piece).acceptedMoves().contains(move);
  }

  /***
   * Generates a list of randomly allowed moves from the given set
   *
   * @param moves to pick random
   */
  private Set<ChessTile> generateAllowedMoves(Set<ChessTile> moves) {
    List<ChessTile> movesList = new ArrayList<>(moves);
    Collections.shuffle(movesList);
    return new HashSet<>(movesList.subList(0, numRandom));
  }
}
