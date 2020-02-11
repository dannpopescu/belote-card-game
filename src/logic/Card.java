package logic;


import javax.swing.*;

public class Card implements Comparable<Card> {

    private Suit suit;
    private Rank rank;
    private int points;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.points = rank.getDefaultPoints();
    }


    public void updatePoints() {
        if (rank == Rank.JACK && suit.isTrump()) {
            this.points = 20;
        } else if (rank == Rank.NINE && suit.isTrump()) {
            this.points = 14;
        } else {
            this.points = rank.getDefaultPoints();
        }
    }

    public int getPoints() {
        return points;
    }


    /**
     * Returns the suit of the card.
     * @return a Suit constant representing the suit value of the card.
     */
    public Suit getSuit() {
        return suit;
    }


    /**
     * Returns the rank of the card.
     * @return a Rank constant representing the rank value of the card.
     */
    public Rank getRank() {
        return rank;
    }


    /**
     * Returns a description of this card.
     * @return the name of the card.
     */
    public String toString() {
        return rank.getSymbol().toUpperCase() + "" + suit.getUnicodeSymbol();
    }


    /**
     * Returns a description of the suit of this card.
     * @return the suit value of the card as a string.
     */
    public String suitToString() {
        return suit.name();
    }


    /**
     * Compares two cards for the purposes of sorting.
     * Cards are ordered first by their suit value, then by their
     * rank value.
     * @param card the other card
     * @return a negative integer, zero, or a positive integer is this card is
     * less than, equal to, or greater than the referenced card.
     */
    public int compareTo(Card card) {
        if (Boolean.compare(this.suit.isTrump(), card.suit.isTrump()) == 0) {
            return Integer.compare(points, card.points);
        } else {
            return Boolean.compare(suit.isTrump(), card.suit.isTrump());
        }
    }
}

