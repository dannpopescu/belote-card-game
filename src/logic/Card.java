// Card.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of a playing card.  Uses classes Rank and Suit for
// expressing the card value.

package logic;

import javax.swing.*;
import java.util.Comparator;
import java.util.Map;


/**
 * Representation of a single playing card. A card consists of a suit value
 * (e.g. hearts, spades), a rank value (e.g. ace, 7, king), and an image of
 * the front of the card.  A card object is immutable; once instantiated, the
 * values cannot change.
 *
 * @author John K. Estell
 * @version 1.0
 */
public class Card implements Comparable<Card> {

    private Suit suit;
    private Rank rank;
    private int points;
    private ImageIcon cardFace;

    // the number of points of a certain rank, not taking into consideration if it's a trump
    private static final Map<Rank, Integer> rankPoints = Map.of(
            Rank.SEVEN, 0,
            Rank.EIGHT, 0,
            Rank.NINE, 0,
            Rank.JACK, 2,
            Rank.QUEEN, 3,
            Rank.KING, 4,
            Rank.TEN, 10,
            Rank.ACE, 11);


    /**
     * Creates a new playing card.
     * @param suit the suit value of this card.
     * @param rank the rank value of this card.
     * @param cardFace the face image of this card.
     */
    public Card(Suit suit, Rank rank, ImageIcon cardFace) {
        this.suit = suit;
        this.rank = rank;
        this.cardFace = cardFace;
    }


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.points = rankPoints.get(rank);
    }


    public void updatePoints() {
        // handles the special value of trump JACK and trump NINE
        if (rank == Rank.JACK && suit.isTrump()) {
            this.points = 20;
        } else if (rank == Rank.NINE && suit.isTrump()) {
            this.points = 14;
        } else {
            this.points = rankPoints.get(rank);
        }
    }

    public int getPoints() {
        return points;
    }

    /**
     * Generates the filename associated with the card.  <code>getFilename</code> assumes that all of the standard card images
     * are stored in individual files using filenames in the form of:
     * <b>RS.gif</b> where <b>R</b> is a single character used to represent
     * the rank value of the card and <b>S</b> is a single character used to represent
     * the suit value of the card.
     * <p>The characters used for <b>R</b> are:
     * 'a' (ace), '2', '3', '4', '5', '6', '7', '8', '9',
     * 't' (10), 'j' (jack), 'q' (queen), and 'k' (king).
     * <p>The characters used for <b>S</b> are:
     * 'c' (clubs), 'd' (diamonds), 'h' (hearts), and 's' (spades).
     * <p>Two other cards are also available: "b.gif" (back of card) and "j.gif" (joker).
     *
     * @param suit the suit value of the card.
     * @param rank the rank value of the card.
     * @return a string containing the filename of the card.
     */
    public static String getFilename(Suit suit, Rank rank) {
        return rank.getSymbol() + suit.getFileSymbol();
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
     * Returns the graphic image of the card.
     * @return an icon containing the graphic image of the card.
     */
    public ImageIcon getCardImage() {
        return cardFace;
    }


    /**
     * Returns a description of this card.
     * @return the name of the card.
     */
    public String toString() {
        return rank.getSymbol().toUpperCase() + "" + suit.getUnicodeSymbol();
    }


    /**
     * Returns a description of the rank of this card.
     * @return the rank value of the card as a string.
     */
    public String rankToString() {
        return rank.toString();
    }


    /**
     * Returns a description of the suit of this card.
     * @return the suit value of the card as a string.
     */
    public String suitToString() {
        return suit.toString();
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


    /**
     * Compares two cards to determine if they have the same value.
     * This is not the same as the use of <code>equals</code> which compares
     * two objects for equality.
     * @param card the other card
     * @return <code>true</code> if the two cards have the same rank and suit
     * values, <code>false</code> if they do not.
     */
    public boolean isSameAs(Card card) {
        return (rank.compareTo(card.rank) == 0)
                && (Boolean.compare(this.suit.isTrump(), card.suit.isTrump()) == 0);
    }

}

