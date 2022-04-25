package oogasalad.Frontend.Editor.Piece;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import oogasalad.Frontend.util.NodeContainer;

import java.util.ArrayList;
import java.util.List;

public class PieceEditor extends NodeContainer {
	private final String ID;
	private final List<PieceBoard> myPieceBoards;
	private final PieceTileTypeSelector myTileTypeSelector;
	private final PieceSettings myPieceSettings;
	private final SimpleIntegerProperty mySelectedTeam;

	public PieceEditor(String id) {
		ID = id;
		mySelectedTeam = new SimpleIntegerProperty(0);
		myPieceBoards = new ArrayList<>();
		myPieceBoards.add(new PieceBoard(id, 0));
		myPieceBoards.add(new PieceBoard(id, 1));
		myTileTypeSelector = new PieceTileTypeSelector();
		myPieceSettings = new PieceSettings(id, mySelectedTeam);
	}

	public String getId() {
		return ID;
	}

	@Override
	protected Node makeNode() {
		return makeLayout();
	}

	private Node makeLayout() {
		BorderPane ret = new BorderPane();
		ret.setPrefWidth(myScreenSize.getWidth());
		ret.setCenter(getCurrentPieceBoard());
		ret.setLeft(myTileTypeSelector.getNode());
		ret.setRight(myPieceSettings.getNode());
		return ret;
	}

	private Node getCurrentPieceBoard() {
		Group ret = new Group();
		ret.getChildren().add(myPieceBoards.get(mySelectedTeam.getValue()).getNode());

		mySelectedTeam.addListener((ob, ov, nv) -> {
			ret.getChildren().clear();
			ret.getChildren().add(myPieceBoards.get((Integer) nv).getNode());
		});

		return ret;
	}
}
