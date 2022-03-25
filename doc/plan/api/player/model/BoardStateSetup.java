
public interface BoardStateSetup {


  /**
   * Creates an initial board state from an input JSON file
   * @param JSONFile the JSON file that serves as input
   * @return a board state with the starting position and rules of the game
   */
  Board initializeBoardState(File JSONFile);

}