package oogasalad.EditorBackend;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.Editor.Exceptions.InvalidBoardSizeException;
import oogasalad.Editor.Validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardValidationTest {
  private Validator editorValidator;

  @BeforeEach
  void setup() {
    editorValidator = new Validator();
  }

  @Test
  void testBoardSizeChecking(){
    Exception largeBoardSizeException = assertThrows(InvalidBoardSizeException.class, () -> {
      editorValidator.checkBoardSizeValidity(30, 30);
    });
    String largeBoardExpectedMessage = "The dimensions of the board exceed the maximum (25)";
    String actualMessage = largeBoardSizeException.getMessage();
    assertTrue(actualMessage.contains(largeBoardExpectedMessage));

    Exception smallBoardSizeException = assertThrows(InvalidBoardSizeException.class, () -> {
      editorValidator.checkBoardSizeValidity(-1, -1);
    });

    String smallBoardExpectedMessage = "The dimensions of the board are less than the minimum (1)";
    actualMessage = smallBoardSizeException.getMessage();
    assertTrue(actualMessage.contains(smallBoardExpectedMessage));
  }



}
