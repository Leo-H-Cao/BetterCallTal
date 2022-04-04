package oogasalad.Editor.API;

public interface ChangeGameView {

  /**
   * Updates color theme for overall application UI by changing to other CSS file
   * @param theme is string that describes the colors, also name of CSS file
   */
  public void changeApplicationTheme(String theme);

  /**
   * Updates color theme for overall application UI by changing to other CSS file
   * @param theme is string that describes the colors, also name of CSS file
   */
  public void changeBoardTheme(String theme);

  /**
   * Changes the display language for application
   * @param language is the language string that user chose
   */
  public void changeDisplayLanguage(String language);

}