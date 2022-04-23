package oogasalad.GamePlayer.Board.Tiles.CustomTiles;

import static oogasalad.GamePlayer.Board.Setup.BoardSetup.JSON_EXTENSION;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import oogasalad.GamePlayer.Board.ChessBoard;
import oogasalad.GamePlayer.Board.Tiles.ChessTile;
import oogasalad.GamePlayer.EngineExceptions.EngineException;
import oogasalad.GamePlayer.GamePiece.Piece;
import oogasalad.GamePlayer.Movement.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Promotion but it just adds the reverse of possible moves
 *
 * @author Vincent Chen
 */
public class PromotionReverse implements TileAction{

  private static final String PROMOTION_IMAGE_FILE_PATH_HEADER = "doc/GameEngineResources/Other/";
  private static final String DEFAULT_PROMOTION_FILE = "PromotionReverse";
  private static final String DEFAULT_PROMOTION_IMAGE = "checkerKing";
  private static final List<String> DEFAULT_PROMOTABLE = List.of("Checker", "Pawn");

  private static final Logger LOG = LogManager.getLogger(PromotionReverse.class);

  private String promotionImage;
  private List<String> promotablePieceNames;

  /***
   * Creates PromotionReverse with default config file
   */
  public PromotionReverse() {
    this(DEFAULT_PROMOTION_FILE);
  }

  /***
   * Creates PromotionReverse with given config file
   *
   * @param configFile to read
   */
  public PromotionReverse(String configFile) {
    promotionImage = getPromotionImage(PROMOTION_IMAGE_FILE_PATH_HEADER + configFile + JSON_EXTENSION);
    promotablePieceNames = getPromotablePieceNames(PROMOTION_IMAGE_FILE_PATH_HEADER + configFile + JSON_EXTENSION);
  }

  /**
   * Reads in promotable piece names from a JSON file
   *
   * @return promotable piece list
   */
  private List<String> getPromotablePieceNames(String configFile) {
    ArrayList<String> nameList = new ArrayList<>();
    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
      JSONArray JSONContent = new JSONObject(content).getJSONArray("promotable");
      for(int i=0; i<JSONContent.length(); i++) {
        nameList.add(JSONContent.getString(i));
      }
      return nameList;
    } catch (IOException e) {
      return DEFAULT_PROMOTABLE;
    }
  }

  /***
   * Assigns piece immune to explosions, default is just pawn
   */
  private static String getPromotionImage(String configFile) {
    try {
      String content = new String(Files.readAllBytes(Path.of(configFile)));
      return new JSONObject(content).getString("image");
    } catch (IOException e) {
      return DEFAULT_PROMOTION_IMAGE;
    }
  }

  /**
   * Adds the reverse of all moves and captures to the given piece
   *
   * @return tile parameter, only tile that is updated
   */
  @Override
  public Set<ChessTile> executeAction(ChessTile tile, ChessBoard board) throws EngineException {
    if(tile.getPiece().isEmpty()) return Collections.emptySet();

    Piece promotedPiece = tile.getPiece().get();
    promotedPiece.addNewMovements(Movement.invertMovements(promotedPiece.getMoves()),
        Movement.invertMovements(promotedPiece.getCaptures()));
    //FIXME: because of current frontend implementation, name change determines image file change
    promotedPiece.updateName(promotionImage);
    return Set.of(tile);
  }
}
