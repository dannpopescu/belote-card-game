package com.danpopescu.belote.declarations;

import com.danpopescu.belote.deck.Card;

import java.util.Collections;
import java.util.List;

public class FourOfAKind extends Declaration {

    public FourOfAKind(List<Card> cards) {
        if (!isFourOfAKind(cards)) {
            throw new IllegalArgumentException("The given list does not contain four cards of the same rank.");
        }

        this.cards = cards;

        switch (cards.get(0).getRank()) {
            case JACK:
                this.name = "Four Jacks";
                this.points = 200;
                break;
            case NINE:
                this.name = "Four Nines";
                this.points = 140;
                break;
            case ACE:
                this.name = "Four Aces";
                this.points = 100;
                break;
            case TEN:
                this.name = "Four Tens";
                this.points = 100;
                break;
            case KING:
                this.name = "Four Kings";
                this.points = 100;
                break;
            case QUEEN:
                this.name = "Four Queens";
                this.points = 100;
                break;
            case EIGHT:
                this.name = "Four Eights";
                this.points = 0;
                break;
            case SEVEN:
                this.name = "Four Sevens";
                this.points = 0;
                break;
        }
    }

    private boolean isFourOfAKind(List<Card> cards) {
        if (cards.size() != 4) {
            return false;
        }

        Card.Rank firstRank = cards.get(0).getRank();
        for (Card card : cards) {
            if (card.getRank() != firstRank) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Card.Rank getHighestRank() {
        return cards.get(0).getRank();
    }

    @Override
    public Card getHighestCard() {
        return Collections.max(cards);
    }
}
