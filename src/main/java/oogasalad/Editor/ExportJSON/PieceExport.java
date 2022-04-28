package oogasalad.Editor.ExportJSON;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import oogasalad.Editor.ModelState.EditPiece.EditorPiece;

/**
 * Class containing info for single piece file that is to be exported, separate from piece
 * information that goes in the main export file
 * @author Leo Cao
 */
public class PieceExport {
	private final String pieceName;
	private final String imgFile;
	private final int pointValue;
	private final ArrayList<String> customMoves;
	private final ArrayList<String> movementModifiers;
	private final ArrayList<ArrayList<String>> onInteractionModifier;
	private final ArrayList<String> basicMovements;
	private final ArrayList<String> basicCaptures;

	@JsonIgnore
	private int teamNum;


  public PieceExport(EditorPiece editorPiece, int teamNum){
		this.teamNum = teamNum;
		pieceName = editorPiece.getPieceID();
		imgFile = editorPiece.getImage(teamNum).getValue().getUrl().split("/classes/")[1];
    pointValue = editorPiece.getPointValue();
    customMoves = editorPiece.getCustomMoves() == null ? new ArrayList<>() :editorPiece.getCustomMoves();
    basicMovements = new ArrayList<>();
    basicCaptures = new ArrayList<>();

		String team = teamNum == 0 ? "w" : "b";
		basicMovements.add(team + pieceName + "Mov");
		basicCaptures.add(team + pieceName + "Cap");

		movementModifiers = new ArrayList<>();
		onInteractionModifier = editorPiece.getOnInteractionModifiers();
	}

	public String getPieceName() {
		return pieceName;
	}

	public String getImgFile() {
		return imgFile;
	}

	public int getPointValue() {
		return pointValue;
	}

	public ArrayList<String> getCustomMoves() {
		return customMoves;
	}

	public ArrayList<String> getMovementModifiers() {
		return movementModifiers;
	}

	public ArrayList<ArrayList<String>> getOnInteractionModifier() {
		return onInteractionModifier;
	}

	public ArrayList<String> getBasicMovements() {
		return basicMovements;
	}

	public ArrayList<String> getBasicCaptures() {
		return basicCaptures;
	}

	public int getTeamNum() {
		return teamNum;
	}
}
