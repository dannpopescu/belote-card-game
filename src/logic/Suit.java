// Suit.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of the "suit" value for a playing card.

package logic;

import java.util.*;


/**
 * Specification of the suit values for a standard deck of cards.
 */
public final class Suit implements Comparable<Suit> {
    private String name;
    private String fileSymbol;
    private char unicodeSymbol;
    private boolean isTrump;

    /**
     * The suit clubs.
     */
    public static final Suit CLUBS = new Suit("clubs", "c", '\u2663');
    /**
     * The suit diamonds.
     */
    public static final Suit DIAMONDS = new Suit("diamonds", "d", '\u2666');
    /**
     * The suit hearts.
     */
    public static final Suit HEARTS = new Suit("hearts", "h", '\u2665');
    /**
     * The suit spades.
     */
    public static final Suit SPADES = new Suit("spades", "s", '\u2660');

    /**
     * List of all suit values.  Primarily for use with iteration.
     */
    public static final List<Suit> VALUES = List.of(CLUBS, DIAMONDS, HEARTS, SPADES);


    private Suit(String name, String fileSymbol, char unicodeSymbol) {
        this.name = name;
        this.fileSymbol = fileSymbol;
        this.unicodeSymbol = unicodeSymbol;
        this.isTrump = false;
    }


    /**
     * Clears the trump suit of the previous game and
     * sets the trump suit of the current game.
     */
    public void setTrump() {
        for (Suit suit : VALUES) {
            suit.isTrump = false;
        }
        isTrump = true;
    }


    /**
     * Returns true if the suit is the current trump
     */
    public boolean isTrump() {
        return isTrump;
    }


    /**
     *  Returns a description of this suit.
     *  @return the name of the suit.
     */
    public String getName() {
        return name;
    }


    /**
     *  The symbol associated with this suit.  Returns the symbol, which
     *  usually constitutes a single character, in the form of a string.
     *  Symbol is used for the construction of the filenames of the card images.
     *  @return string containing the symbol for the suit.
     */
    public String getFileSymbol() {
        return fileSymbol;
    }


    /**
     * Returns the unicode char symbol associated with this suit.
     * @return char containing the symbol for the suit.
     */
    public char getUnicodeSymbol() {
        return unicodeSymbol;
    }


    /**
     * Returns a description of this suit.
     * @return the name of this suit.
     */
    public String toString() {
        return name;
    }


    /**
     *  Compares the suits. Used for the purpose of choosing the winner card.
     *  @param o the other suit.
     *  @return < 0 if the other suit is trump and this is not, 0 if the suits
     *  are both trump or both non-trump, or > 0 if this suit is trump and the other is not.
     */
    @Override
    public int compareTo(Suit o) {
        return Boolean.compare(isTrump, o.isTrump);
    }
}

