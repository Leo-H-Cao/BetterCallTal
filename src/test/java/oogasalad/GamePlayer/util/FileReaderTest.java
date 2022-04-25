package oogasalad.GamePlayer.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderTest {

  static final String PATH_HEADER = "doc/testing_directory/file_reader_files/";
  static final String STRING_FILE = PATH_HEADER + "ManyStrings";
  static final String INT_FILE = PATH_HEADER + "OneInt";
  static final String DOUBLE_FILE = PATH_HEADER + "OneDouble";

  @Test
  void testManyStringsRead() {
    assertEquals(List.of("Lorem", "ipsum", "dolor", "sit", "amet"),
        FileReader.readManyStrings(STRING_FILE, List.of("test")));
    assertEquals(List.of("test"),
        FileReader.readManyStrings(STRING_FILE + "a", List.of("test")));
  }

  @Test
  void testOneIntRead() {
    assertEquals(6,
        FileReader.readOneInt(INT_FILE, 1));
    assertEquals(1,
        FileReader.readOneInt(STRING_FILE + "a", 1));
  }

  @Test
  void testOneDoubleRead() {
    assertEquals(5.0,
        FileReader.readOneDouble(DOUBLE_FILE, 1.0));
    assertEquals(1.0,
        FileReader.readOneDouble(STRING_FILE + "a", 1.0));
  }
}