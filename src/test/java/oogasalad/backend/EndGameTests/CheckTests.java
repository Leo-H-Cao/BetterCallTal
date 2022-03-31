package oogasalad.backend.EndGameTests;

import oogasalad.GamePlayer.GameClauses.CheckValidator;
import org.junit.jupiter.api.Test;

/**
 * Testing class for the Check class
 */
public class CheckTests {

  CheckValidator check = new CheckValidator();

  /**
   * isInCheck should return true because the king is in check
   */
  @Test
  void inCheck() {

  }

  /**
   * isInCheck should return false because the king is not in check
   */
  @Test
  void notInCheck() {

  }

  /**
   * This should never happen due to other parameters but testing this just in case
   * it should ever happen (coverage, sad-testing)
   */
  @Test
  void movesIntoCheck() {

  }

  /**
   * inCheck method should first
   */
  @Test
  void movesOutOfCheck() {

  }

}
