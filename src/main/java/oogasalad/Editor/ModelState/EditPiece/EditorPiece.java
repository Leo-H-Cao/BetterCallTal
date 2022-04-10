package oogasalad.Editor.ModelState.EditPiece;

import javafx.scene.image.Image;

public class EditorPiece {
  private EditPieceGrid editPieceGrid;
  private Image image0;
  private Image image1;
  private String pieceID;

  public EditorPiece(String pieceID){
    this.pieceID = pieceID;
    editPieceGrid = new EditPieceGrid();
  }

  public EditPieceGrid getEditPieceGrid() {
    return editPieceGrid;
  }

  public void setImage(int team, Image image) {
    if(team == 0){
      this.image0 = image;
    }
    else{
      this.image1 = image;
    }

  }

  public Image getImage(int team){
    if(team == 0){
      return image0;
    }
    else{
      return image1;
    }
  }

  public String getPieceID() {
    return pieceID;
  }
}
