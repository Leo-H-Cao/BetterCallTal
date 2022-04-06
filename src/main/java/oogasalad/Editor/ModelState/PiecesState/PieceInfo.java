package oogasalad.Editor.ModelState.PiecesState;

/**
 * abstraction pieces with only information that front end needs
 */
public class PieceInfo {
  private int startingPosX;
  private int startingPosY;
  private String myImage;
  private int team;

  public PieceInfo(String imageFile, int teamNumber){
    myImage = imageFile;
    team = teamNumber;
  }

  public void setStartingPosX(int startX){
    startingPosX = startX;
  }

  public void setStartingPosY(int startY){
    startingPosY = startY;
  }

  public void setImage(String imageFile){
    myImage = imageFile;
  }

  public void setTeam(int teamNumber){
    team = teamNumber;
  }

  public int getStartingPosX(){
    return startingPosX;
  }

  public int getStartingPosY(){
    return startingPosY;
  }

  public String getImageFile(){
    return myImage;
  }

}
