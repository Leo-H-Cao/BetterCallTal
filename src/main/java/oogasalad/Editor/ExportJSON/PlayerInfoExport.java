package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;
import java.util.HashMap;

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

  public void setTeam(int team) {
    this.team = team;
  }

  public ArrayList<Integer> getOpponents() {
    return opponents;
  }

  public void setOpponents(ArrayList<Integer> opponents) {
    this.opponents = opponents;
  }
}
