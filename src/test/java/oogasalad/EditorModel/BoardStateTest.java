package oogasalad.EditorModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.Editor.Backend.ModelState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardStateTest {
  private ModelState modelState;

  @BeforeEach
  void setup() {
    modelState = new ModelState();
  }

  @Test
  void testChangeBoardSize(){
    modelState.changeBoardSize(10,5);
    assertEquals(modelState.getBoardHeight(), 5);
    assertEquals(modelState.getBoardWidth(), 10);
  }


}
