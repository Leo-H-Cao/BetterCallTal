package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class ExportWrapper {

  private GeneralExport general;
  private ArrayList<PlayerInfoExport> playerInfo;
  private ArrayList<PieceExport> pieces;

  public ArrayList<PieceExport> getPieces() {
    return pieces;
  }

  public ExportWrapper(GeneralExport generalExport, ArrayList<PlayerInfoExport> playerInfo, ArrayList<PieceExport> pieces){
    general = generalExport;
    this.playerInfo = playerInfo;
    this.pieces = pieces;
  }

  public GeneralExport getGeneral() {
    return general;
  }

  public void setGeneral(GeneralExport generalExport) {
    general = generalExport;
  }

  public ArrayList<PlayerInfoExport> getPlayerInfo() {
    return playerInfo;
  }

  public void setPlayerInfo(ArrayList<PlayerInfoExport> playerInfo) {
    this.playerInfo = playerInfo;
  }
}
