package oogasalad.Editor.ModelState.EditPiece;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * Piece class with configurations created and modified by user, unique pieceID,
 * and is placed on editor board to set starting location,
 * A single piece editor contains info for both its black and white version
 * @author Leo Cao
 */
public class EditorPiece {
	private MovementGrid movementGrid0;
	private MovementGrid movementGrid1;
	private final Property<Image> image0;
	private final Property<Image> image1;
	private final String pieceID;
	private final boolean defaultPiece;
	private boolean mainPiece;
	private ArrayList<String> customMoves;
	private int pointValue;
	private final SimpleStringProperty pieceName;
	private ArrayList<ArrayList<String>> onInteractionModifiers;

	public EditorPiece(String pieceID){
		this.pieceID = pieceID;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getClass().getName());
		String image0Path = String.format("images/pieces/Default/white/%s.png", resourceBundle.getString("DefaultImage0"));
		String image1Path = String.format("images/pieces/Default/black/%s.png", resourceBundle.getString("DefaultImage1"));
		onInteractionModifiers = new ArrayList<>();

		movementGrid0 = new MovementGrid();
		movementGrid1 = new MovementGrid();
		mainPiece = false;
		pieceName = new SimpleStringProperty(pieceID);
		image0 = new SimpleObjectProperty<>(new Image(image0Path));
		image1 = new SimpleObjectProperty<>(new Image(image1Path));
		defaultPiece = false;
	}

	public EditorPiece(String pieceID, boolean defaultPiece) {
		this.pieceID = pieceID;
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getClass().getName());
		String image0Path = String.format("images/pieces/Default/white/%s.png", resourceBundle.getString("DefaultImage0"));
		String image1Path = String.format("images/pieces/Default/black/%s.png", resourceBundle.getString("DefaultImage1"));
		onInteractionModifiers = new ArrayList<>();

		movementGrid0 = new MovementGrid();
		movementGrid1 = new MovementGrid();
		mainPiece = false;
		pieceName = new SimpleStringProperty(pieceID);
		image0 = new SimpleObjectProperty<>(new Image(image0Path));
		image1 = new SimpleObjectProperty<>(new Image(image1Path));
		this.defaultPiece = defaultPiece;
	}

	public boolean isDefaultPiece() {
		return defaultPiece;
	}

	public MovementGrid getMovementGrid(int team) {
		if(team == 0){
			return movementGrid0;
		}
		else{
			return movementGrid1;
		}
	}

	/**
	 * Sets current piece as objective of game
	 * @param main is the piece a main piece
	 */
	public void setMainPiece(boolean main){
		mainPiece = main;
	}

	public boolean getMainPiece(){
		return mainPiece;
	}

	public String getPieceID() {
		return pieceID;
	}

	/**
	 * Sets movement grid tile as open, closed, or capture for movement
	 * @param x coord of tile
	 * @param y coord of tile
	 * @param tileStatus determine whether tile is closed or open for movement/capture
	 * @param team determines which movement grid of the piece to edit
	 */
	public void setTile(int x, int y, PieceGridTile tileStatus, int team){
		if(team == 0){
			movementGrid0.setTile(x, y, tileStatus);
		}
		else{
			movementGrid1.setTile(x,y,tileStatus);
		}

	}

	public PieceGridTile getTileStatus(int x, int y, int team){
		if(team == 0){
			return movementGrid0.getTileStatus(x,y);
		}
		else{
			return movementGrid1.getTileStatus(x,y);
		}
	}

	/**
	 * Changes piece image to image set by user
	 * @param team determine which team's (black or white) image of this piece should be changed
	 * @param image new piece image
	 */
	public void setImage(int team, Image image) {
		if(team == 0){image0.setValue(image);}
		else{image1.setValue(image);}
	}

	public Property<Image> getImage(int team){
		if(team == 0){return image0;}
		else{return image1;}
	}

	public void setCustomMoves(ArrayList<String> customMoves){
		this.customMoves = customMoves;
	}

	public ArrayList<String> getCustomMoves(){
		return customMoves;
	}

	public void setMovementGrid(MovementGrid movementGrid, int team) {
		if(team == 1){
			this.movementGrid1 = movementGrid;
		}
		else{
			this.movementGrid0 = movementGrid;
		}
	}

	public boolean isMainPiece() {
		return mainPiece;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public Property<String> getPieceName() {
		return pieceName;
	}

	public void setPieceName(String pieceName) {
		// Ensure that the piece name cannot be empty
		if(pieceName.equals("")) return;
		this.pieceName.setValue(pieceName);
	}

	public ArrayList<ArrayList<String>> getOnInteractionModifiers() {
		return onInteractionModifiers;
	}

	public void setOnInteractionModifiers(
			ArrayList<ArrayList<String>> onInteractionModifier) {
		this.onInteractionModifiers = onInteractionModifier;
	}

	@Override
	public String toString() {
		return pieceID;
	}
}
