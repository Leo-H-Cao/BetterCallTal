package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

/**
 * Wrapper of all the game configurations that go into the main export file,
 * to fit formatting needed by game engine once serialized to JSON by Jackson library
 * @author Leo Cao
 */
public class ExportWrapper {

  private final ArrayList<GeneralExport> general;
  private final ArrayList<PlayerInfoExport> playerInfo;
  private final ArrayList<PieceMainExport> pieces;
  private final ArrayList<TileExport> tiles;

  public ExportWrapper(GeneralExport generalExport, ArrayList<PlayerInfoExport> playerInfo, ArrayList<PieceMainExport> pieces, ArrayList<TileExport> tiles){
    general = new ArrayList<>();
    general.add(generalExport);
    this.playerInfo = playerInfo;
    this.pieces = pieces;
    this.tiles = tiles;
  }

  public ArrayList<GeneralExport> getGeneral() {
    return general;
  }

  public ArrayList<PlayerInfoExport> getPlayerInfo() {
    return playerInfo;
  }

  public ArrayList<PieceMainExport> getPieces() {
    return pieces;
  }

  public ArrayList<TileExport> getTiles(){
    return tiles;
  }
}
