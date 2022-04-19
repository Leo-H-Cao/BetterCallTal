package oogasalad.GamePlayer.Board.EndConditions;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Movement.CustomMovements.EnPassant;
import oogasalad.GamePlayer.ValidStateChecker.BankBlocker;
import oogasalad.GamePlayer.util.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class InARow implements EndCondition {

  private static final Logger LOG = LogManager.getLogger(InARow.class);

  public static final String IAR_CONFIG_FILE_HEADER = "doc/GameEngineResources/Other/";
  public static final String IAR_DEFAULT_FILE = "InARowThree";

  private static final int IAR_DEFAULT_VAL = 3;
  private static final List<String> IAR_DEFAULT_NAMES = List.of("Checker");

  private List<String> pieceNames;
  private int numInARow;

  /***
   * Constructs InARow with default file
   */
  public InARow() {
    this(IAR_DEFAULT_FILE);
  }

  /***
   * Constructs InARow with provided config file
   *
   * @param configFile to read
   */
  public InARow(String configFile) {
    try {
      JSONObject data = new JSONObject(Files.readAllBytes(Path.of(
          IAR_CONFIG_FILE_HEADER + configFile + JSON_EXTENSION)));

      numInARow = data.getInt("num");
      pieceNames = new ArrayList<>();
      JSONArray pieceArray = data.getJSONArray("pieces");
      for(int i=0; i<pieceArray.length(); i++) {
        pieceNames.add(pieceArray.getString(i));
      }
    } catch (IOException e) {
      numInARow = IAR_DEFAULT_VAL;
      pieceNames = IAR_DEFAULT_NAMES;
    }
  }

  /***
   * Returns win for
   *
   * @param board to check
   * @return above
   */
  @Override
  public Map<Integer, Double> getScores(ChessBoard board) {
    return null;
  }

  @Override
  public int compareTo(EndCondition o) {
    return 0;
  }
}
