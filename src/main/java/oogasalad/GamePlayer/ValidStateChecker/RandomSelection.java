package oogasalad.GamePlayer.ValidStateChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
  private Piece currentPiece;
  private Set<ChessTile> currentAllMoves;
  private Set<ChessTile> currentAcceptedMoves;

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
    currentAcceptedMoves = new HashSet<>();
    currentPiece = null;
  }

  /***
   * @return if a given move is valid based on a random selection
   */
  @Override
  public boolean isValid(ChessBoard board, Piece piece, ChessTile move) throws EngineException {
    Set<ChessTile> allMoves = piece.getMoves(board);
    if(allMoves.size() <= numRandom) return true;

    if(piece != currentPiece || currentAllMoves.equals(allMoves)) {
      currentPiece = piece;
      currentAcceptedMoves = generateAllowedMoves(allMoves);
    }

    return currentAcceptedMoves.contains(move);
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
