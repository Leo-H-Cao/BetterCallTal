package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class ExportWrapper {

  private GeneralExport general;
  private ArrayList<PlayerInfoExport> playerInfo;

  public ExportWrapper(GeneralExport generalExport, ArrayList<PlayerInfoExport> playerInfo){
    general = generalExport;
    this.playerInfo = playerInfo;
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
