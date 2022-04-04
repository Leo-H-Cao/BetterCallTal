package oogasalad.Frontend.GamePlayer.GamePiece;

import java.util.List;
<<<<<<< HEAD:src/main/java/oogasalad/Frontend/GamePlayer/GamePiece/PieceData.java
import oogasalad.Frontend.GamePlayer.Movement.CustomMovement;
import oogasalad.Frontend.GamePlayer.Movement.Movement;
import oogasalad.Frontend.GamePlayer.Movement.MovementModifier;
import oogasalad.Frontend.GamePlayer.Movement.Coordinate;
=======
import oogasalad.Editor.Movement.Coordinate;
import oogasalad.Editor.Movement.MovementInterface;
import oogasalad.Editor.Movement.MovementModifiers.MovementModifier;
>>>>>>> 13bbc0787cf1e68b3a5d1149146123c54d282f81:src/main/java/oogasalad/GamePlayer/GamePiece/PieceData.java

public record PieceData(Coordinate startingLocation,
                        String name,
                        double pointValue,
                        int team,
                        boolean mainPiece,
                        List<MovementInterface> movements,
                        List<MovementInterface> captures,
                        List<MovementInterface> customMovements,
                        List<MovementModifier> movementModifiers,
                        List<MovementModifier> onInteractionModifiers,
                        String img) {

}
