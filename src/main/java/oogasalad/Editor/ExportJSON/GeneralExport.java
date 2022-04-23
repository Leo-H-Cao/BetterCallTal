package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

public class GeneralExport {

  private int rows;
  private int columns;
  private String turnCriteria;
  private ArrayList<String> colors;
  private ArrayList<String> endConditions;
  private ArrayList<String> validStateChecker;

  public GeneralExport(int row, int col){
    this.rows = row;
    this.columns = col;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public String getTurnCriteria() {
    return turnCriteria;
  }

  public void setTurnCriteria(String turnCriteria) {
    this.turnCriteria = turnCriteria;
  }

  public ArrayList<String> getColors() {
    return colors;
  }

  public void setColors(ArrayList<String> colors) {
    this.colors = colors;
  }

  public ArrayList<String> getEndConditions() {
    return endConditions;
  }

  public void setEndConditions(ArrayList<String> endConditions) {
    this.endConditions = endConditions;
  }

  public ArrayList<String> getValidStateChecker() {
    return validStateChecker;
  }

  public void setValidStateChecker(ArrayList<String> validStateChecker) {
    this.validStateChecker = validStateChecker;
  }
}
