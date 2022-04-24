package oogasalad.Editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.ModelState.RulesState.GameRulesState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesTest {

  private GameRulesState gameRulesState;

  @BeforeEach
  void setup() {
    gameRulesState = new GameRulesState();
  }

  @Test
  void testSetTurnCriteria(){
    String defaultTurnCriteria = "Linear";
    assertEquals(gameRulesState.getTurnCriteria(), defaultTurnCriteria);

    String newTurnCriteria = "another criteria";
    gameRulesState.setTurnCriteria(newTurnCriteria);
    assertEquals(gameRulesState.getTurnCriteria(), newTurnCriteria);
  }
}
