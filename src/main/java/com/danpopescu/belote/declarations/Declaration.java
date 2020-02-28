package com.danpopescu.belote.declarations;

import com.danpopescu.belote.deck.Card;

import java.util.List;

public abstract class Declaration implements Comparable<Declaration> {

    protected String name;
    protected int points;
    protected List<Card> cards;

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
