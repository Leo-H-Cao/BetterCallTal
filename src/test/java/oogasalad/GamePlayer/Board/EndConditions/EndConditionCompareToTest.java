package oogasalad.GamePlayer.Board.EndConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EndConditionCompareToTest {

  @Test
  void testCompareTo() {
    assertEquals(0, new Antichess().compareTo(new Antichess()));
    assertEquals(0, new AtomicEndCondition().compareTo(new Antichess()));
    assertEquals(0, new Checkmate().compareTo(new Antichess()));
    assertEquals(0, new InARow().compareTo(new Antichess()));
    assertEquals(0, new KingOfTheHill().compareTo(new Antichess()));
    assertEquals(0, new KnownDraws().compareTo(new Antichess()));
    assertEquals(0, new LoseAllPieces().compareTo(new Antichess()));
    assertEquals(0, new NoEndCondition().compareTo(new Antichess()));
    assertEquals(0, new PawnReachesEnd().compareTo(new Antichess()));
    assertEquals(0, new ReachTile().compareTo(new Antichess()));
    assertEquals(0, new Repetition().compareTo(new Antichess()));
    assertEquals(0, new Stalemate().compareTo(new Antichess()));
    assertEquals(0, new StalemateWin().compareTo(new Antichess()));
    assertEquals(0, new TwoMoves().compareTo(new Antichess()));


  }
}
