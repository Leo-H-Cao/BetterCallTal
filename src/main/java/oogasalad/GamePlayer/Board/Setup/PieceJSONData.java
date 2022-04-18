package oogasalad.GamePlayer.Board.Setup;

import java.util.List;
import oogasalad.GamePlayer.Movement.MovementInterface;
import oogasalad.GamePlayer.Movement.MovementModifiers.MovementModifier;

public record PieceJSONData(String pieceName, String imgFile, int pointValue, List<MovementInterface> basicMovements,
                            List<MovementInterface> basicCaptures, List<MovementInterface> customMoves, List<MovementModifier>
                            movementModifiers, List<MovementModifier> onInteractionModifiers) {

}
