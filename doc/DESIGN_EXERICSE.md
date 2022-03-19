# Fluxx Lab Discussion
## vjc9, rj160, lam145, zy111, lhc22, jas299, nrg23



### High Level Design Ideas


### CRC Card Classes

Abstract class representing a collection of cards
```java
public abstract class Deck {
    // Cards that were previosuly played that are currently active
    private final Collection<Card> activeCards;
    // updates the information based on new data 
    public void update (int data);
    // Gets a random active card and deactivates it
    public Card getRandom ();
    // Perform associated action
    public abstract void performAction();
    // Adds a card
    public void addCard(Card card);
}
public class RulesDeck extends Deck {
    
    
}

public class PlayerDeck extends Deck {

}
```
Card class for each card
```java
public abstract class Card {
     // returns type of card (goal, action, etc.)
     public String getType ()
     // plays the card and returns action
     public abstract ActionObject play ()
 }
```

Action Card class for cards that result in an action
```java
public class ActionCard {
     public ActionObject play ()
 }
```

Abstract class for different types of actions
```java
public abstract class ActionObject {
     public abstract void execute (Collection<Players>));
 }
```

Rule Action
```java
public class RuleAction extends ActionObject {
     public void execute(Collection<Players>);
 }
```

Player class for each player
```java
public class Player {
    Collection<Card> myKeeperCards
     // Takes any card as an input and plays it
     public ActionObject playCard (Card card)
     // Takes any card as an input and plays it
     public Order makeOrder (String structuredData)
 }
```

CardPlayer class for handling card actions
```java
public class CardPlayer {
     // Plays card card as a keeper for player p
     public void playKeeper (Card keeperCard, Player p)
     //Adds a rule to the current set, overriding a previous rule if necessary
     public void playRule (Card ruleCard)
    //play an action card for player p
    public ActionObject playAction (Card actionCard, player p)
 }

Class for all different types of card piles
public class CardPile {
    // Cards that were previosuly played that are now discarded
    private Collection<Card> discardedCards;
    // Cards that are currently active
    private Collection<Card> activeCards;
    // Makes sures the 
    // updates the information based on new data 
     public void update (int data)
 }
```


### Use Cases

 * A player plays a Goal card, changing the current goal, and wins the game.
```java 

    
    Player.playCard(GoalCard);
    
    Deck.getGoalCards().add(GoalCard);

    //At the end of any action
    Player.checkWin(myKeepers, Deck.getGoalCards())
    
    Game.EndGame();
```

 * A player plays an Action card, allowing him to choose cards from another player's hand and play them.
```java
    player.playAction(actionCard, player2);
    
```

 * A player plays a Rule card, adding to the current rules to set a hand-size limit, requiring all players to immediately drop cards from their hands if necessary.
```java
   ...
   Deck.getRuleCards().add(player.playCard());
   ...
   // inside RuleCard class
   public void execute(Collection<Player> players) {
      for(Player: players) ...
   }
```

 * A player plays a Rule card, changing the current rule from Play 1 to Play All, requiring the player to play more cards this turn.
```java
 Player currentPlayer = Controller.getCurrentPlayer();
Card playedCard = currentPlayer.chooseCard(input);
 currentPlayer.playCard(playedCard);
 
```

 * A player plays a card, fulfilling the current Ungoal, and everyone loses the game.
```java
```

 * A new theme for the game is designed, creating a different set of Rule, Keeper, and Creeper cards.
```java
 //Within the customization branch of the program
    CardFactory.createRuleCard()
```