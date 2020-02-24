package logic.declarations;

import logic.deckofcards.Card;

public interface Declaration {

    String getName();

    int getPoints();

    Card.Rank getHighestRank();


}
