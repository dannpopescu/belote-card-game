package com.danpopescu.belote.declarations;

import com.danpopescu.belote.deck.Card;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SequenceOfCards extends Declaration {

    private SequenceOfCards(String name, int points, List<Card> cards) {
        super(name, points, cards);
    }

    public static SequenceOfCards createSequenceOfCards(List<Card> cards) {
        cards.sort(Comparator.comparing(Card::getRank));

        switch (cards.size()) {
            case 2: return new SequenceOfCards("Bella", 20, cards);
            case 3: return new SequenceOfCards("Tart", 20, cards);
            case 4: return new SequenceOfCards("Jumatate de suta", 50, cards);
            case 5: return new SequenceOfCards("O suta", 100, cards);
            case 6: case 7: case 8: return new SequenceOfCards("O suta cincizeci", 150, cards);
            default: return null;
        }
    }

    @Override
    public Card.Rank getHighestRank() {
        return getHighestCard().getRank();
    }

    @Override
    public Card getHighestCard() {
        return Collections.max(getCards());
    }
}
