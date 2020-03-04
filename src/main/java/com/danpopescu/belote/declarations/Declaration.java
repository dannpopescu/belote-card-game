package com.danpopescu.belote.declarations;

import com.danpopescu.belote.deck.Card;

import java.util.List;

public abstract class Declaration implements Comparable<Declaration> {

    private String name;
    private int points;
    private List<Card> cards;

    public Declaration(String name, int points, List<Card> cards) {
        this.name = name;
        this.points = points;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getMatchPoints() {
        return points / 10;
    }

    public abstract Card.Rank getHighestRank();

    public abstract Card getHighestCard();

    List<Card> getCards() {
        return cards;
    }

    public String toString() {
        return name + " {cards=" + cards + "}";
    }

    @Override
    public int compareTo(Declaration o) {
        if (points == o.points) {
            return getHighestRank().compareTo(o.getHighestRank());
        } else {
            return Integer.compare(points, o.points);
        }
    }
}
