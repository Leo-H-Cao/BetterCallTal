package oogasalad;

public interface Initializable {

  /**
   * Uses arguments passed in to override any defaults for pieces, board, and rules with values provided by the user,
   * @return initialized model state
   */
  public ModelState initializeModelState(BoardConfig initialBoardConfig, PiecesConfig initialPiecesConfig,
      RulesConfig initialRulesConfig);

  /**
   * Instead of providing initial configurations, the initial settings are read in from a file
   * @return initialized modelState based on file read in
   */
  public ModelState loadModelStateFromFile(File file);

}
