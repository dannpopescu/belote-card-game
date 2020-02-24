package logic.declarations;

import logic.deckofcards.Card;

public interface IDeclaration {

    String getName();

    int getPoints();

    Card.Rank getHighestRank();


}
