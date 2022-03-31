package oogasalad.Editor.Backend.API;

import oogasalad.Editor.Backend.ModelState.EditorPiece;
import oogasalad.Editor.Backend.ModelState.MovementRules;
import oogasalad.Editor.Backend.ModelState.PieceInfo;

public interface ModifiesPiecesState {

  PieceInfo getPieceInfo(String pieceID);

  void setPieceStartingLocation(String pieceID, int startX, int startY);

  /**
   * Change piece display image to a custom image provided by user
   * @param piece whose image user is attempting to change
   * @param imageFile, new image file for piece
   */
    void changePieceImage(String pieceID, String imageFile);


  /**
   * Creates a new custom piece according to rules defined by user
   * @param pieceRules describe where the custom piece is allowed to move, how it interacts with other pieces
   * @return PieceConfig object containing information for new piece
   */
   EditorPiece createCustomPiece(int points, int teamNumber, String image, MovementRules movementRules, String pieceID, String pieceName, int startX, int startY);

  /**
   * Changes movement patterns/rules for a given piece
   * @param movementRules describes the squares that the piece can move to relative to itself
   */
  public void changePieceMovement(String pieceID, MovementRules movementRules);

  void setPiecePointValue(String pieceID, int points);

  void setPieceName(String pieceID, String name);

//  /**
//   * Adds modifier to a piece such as inivisible or make the piece the objective of the game
//   * @param piece user is trying add modifier to
//   * @param modifier
//   */
//  public void addPieceModifier(Piece piece, PieceModifier modifier);
//
//  /**
//   * Remove modifier from a given piece
//   * @param piece
//   * @param modifier
//   * @return the removed piece modifier
//   */
//  public PieceModifier removePieceModifier(Piece piece, PieceModifier modifier);

}
