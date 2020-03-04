package com.danpopescu.belote.declarations;

import com.danpopescu.belote.deck.Card;

import java.util.Collections;
import java.util.List;

public class FourOfAKind extends Declaration {

    private FourOfAKind(String name, int points, List<Card> cards) {
        super(name, points, cards);
    }

    public static FourOfAKind createFourOfAKind(List<Card> cards) {
        if (!isFourOfAKind(cards)) {
            throw new IllegalArgumentException("The given list does not contain four cards of the same rank.");
        }

        switch (cards.get(0).getRank()) {
            case  JACK: return new FourOfAKind("Four Jacks", 200, cards);
            case  NINE: return new FourOfAKind("Four Nines", 140, cards);
            case   ACE: return new FourOfAKind("Four Aces", 100, cards);
            case   TEN: return new FourOfAKind("Four Tens", 100, cards);
            case  KING: return new FourOfAKind("Four Kings", 100, cards);
            case QUEEN: return new FourOfAKind("Four Queens", 100, cards);
            case EIGHT: return new FourOfAKind("Four Eights", 0, cards);
            case SEVEN: return new FourOfAKind("Four Sevens", 0, cards);
            default:    return null;
        }
    }

    private static boolean isFourOfAKind(List<Card> cards) {
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
        return getCards().get(0).getRank();
    }

    @Override
    public Card getHighestCard() {
        return Collections.max(getCards());
    }
}
