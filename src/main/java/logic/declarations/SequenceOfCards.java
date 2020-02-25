package logic.declarations;

import logic.deckofcards.Card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SequenceOfCards extends Declaration {

    public SequenceOfCards(List<Card> cards) {
        this.cards = cards;
        this.cards.sort(Comparator.comparing(Card::getRank));

        switch (cards.size()) {
            case 2:
                this.name = "Bella";
                this.points = 20;
                break;
            case 3:
                this.name = "Tart";
                this.points = 20;
                break;
            case 4:
                this.name = "Jumatate de suta";
                this.points = 50;
                break;
            case 5:
                this.name = "O suta";
                this.points = 100;
                break;
            case 6: case 7: case 8:
                this.name = "O suta cincizeci";
                this.points = 150;
                break;
        }
    }


    @Override
    public Card.Rank getHighestRank() {
        return getHighestCard().getRank();
    }

    @Override
    public Card getHighestCard() {
        return Collections.max(cards);
    }
}
