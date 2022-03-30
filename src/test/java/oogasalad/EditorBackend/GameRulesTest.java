package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.Backend.ModelState.GameRules;
import oogasalad.Editor.Backend.ModelState.ModelState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameRulesTest {

  private ModelState modelState;

  @BeforeEach
  void setup() {
    modelState = new ModelState();
  }

  @Test
  void testSetTurnCriteria(){
    String defaultTurnCriteria = "linear";
    GameRules gameRules = modelState.getGameRules();
    assertEquals(gameRules.getTurnCriteria(), defaultTurnCriteria);

    String newTurnCriteria = "another criteria";
    modelState.setTurnCriteria(newTurnCriteria);
    assertEquals(gameRules.getTurnCriteria(), newTurnCriteria);
  }

}
