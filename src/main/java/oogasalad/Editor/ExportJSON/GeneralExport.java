package oogasalad.Editor.ExportJSON;

import java.util.ArrayList;

/**
 * Class that contains data for general section of main export file,
 * designed to be serialized to JSON by Jackson library
 * @author Leo Cao
 */
public class GeneralExport {

  private final int rows;
  private final int columns;
  private String turnCriteria;
  private ArrayList<String> colors;
  private ArrayList<ArrayList<String>> endConditions;
  private ArrayList<ArrayList<String>> validStateCheckers;

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

  public ArrayList<ArrayList<String>> getEndConditions() {
    return endConditions;
  }

  public void setEndConditions(ArrayList<ArrayList<String>> endConditions) {
    this.endConditions = endConditions;
  }

  public ArrayList<ArrayList<String>> getValidStateCheckers() {
    return validStateCheckers;
  }

  public void setValidStateCheckers(ArrayList<ArrayList<String>> validStateCheckers) {
    this.validStateCheckers = validStateCheckers;
  }
}
