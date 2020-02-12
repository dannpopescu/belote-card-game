package logic.declarations;

import logic.deckofcards.Rank;

public interface IDeclaration {

    String getName();

    int getPoints();

    Rank getHighestRank();


}
