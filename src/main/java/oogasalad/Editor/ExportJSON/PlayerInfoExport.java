package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class PlayerInfoExport {
  private int team;
  private ArrayList<Integer> opponents;

  public PlayerInfoExport(int team, ArrayList<Integer> opponents){
    this.team = team;
    this.opponents = opponents;
  }

  public int getTeam() {
    return team;
  }

  public ArrayList<Integer> getOpponents() {
    return opponents;
  }
}
