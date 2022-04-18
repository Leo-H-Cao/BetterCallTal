package oogasalad.GamePlayer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

  /***
   * Reads in given file and returns List of Strings in file
   */
  public static List<String> read(String filePath, List<String> defaultValue) {
    List<String> list = new ArrayList<>();
    try {
      File endZoneFile = new File(filePath);
      Scanner reader = new Scanner(endZoneFile);
      while (reader.hasNext()) {
        list.add(reader.next().trim());
      }
      reader.close();
      return list;
    } catch (Exception e) {
      return defaultValue;
    }
  }
}
