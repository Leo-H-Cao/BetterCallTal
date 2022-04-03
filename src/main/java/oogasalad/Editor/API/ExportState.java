package oogasalad.Editor.API;

public interface ExportState {

  /**
   * Exports game configurations as a JSON file to users local computer, ready for use in hosting a game
   * @return if export is successful
   */
  public Boolean exportJSON();

}