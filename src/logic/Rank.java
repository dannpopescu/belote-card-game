// Rank.java - John K. Estell - 8 May 2003
// last modified: 16 Febraury 2004
// Implementation of the "rank" value for a playing card.

package logic;

import java.util.List;

/**
 *  Specification of the rank values for a standard deck of cards.
 *  Client has ability to set either the ace or the king to be the
 *  highest ranking card; default is king high.  Ranks are
 *  established in the following ascending order:
 *  <p>King high: ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king.
 *  <p>Ace high: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace.
 *  <p>Class can be extended for implementation of speciality decks
 *  containing a subset of the standard ranks, e.g. pinochle.
 */
public class Rank implements Comparable<Rank> {
    private String name;
    private String symbol;

    /**
     * The rank seven.
     */
    public static final Rank SEVEN = new Rank("Seven", "7");
    /**
     * The rank eight.
     */
    public static final Rank EIGHT = new Rank("Eight", "8");
    /**
     * The rank nine.
     */
    public static final Rank NINE = new Rank("Nine", "9");
    /**
     * The rank ten.
     */
    public static final Rank TEN = new Rank("Ten", "t");
    /**
     * The rank jack.
     */
    public static final Rank JACK = new Rank("Jack", "j");
    /**
     * The rank queen.
     */
    public static final Rank QUEEN = new Rank("Queen", "q");
    /**
     * The rank king.
     */
    public static final Rank KING = new Rank("King", "k");
    /**
     * The rank ace.
     */
    public static final Rank ACE = new Rank("Ace", "a");

    /**
     * List of all rank values.  Used primarily for the purpose of iteration.
     */
    private static final List<Rank> VALUES_FOUR_PLAYERS =
            List.of(SEVEN, EIGHT, NINE, JACK, QUEEN, KING, TEN, ACE);

    private static final List<Rank> VALUES_LESS_THAN_FOUR_PLAYERS =
            List.of(NINE, JACK, QUEEN, KING, TEN, ACE);

    public static List<Rank> VALUES = VALUES_LESS_THAN_FOUR_PLAYERS;


    private Rank(String nameValue, String symbolValue) {
        this.name = nameValue;
        this.symbol = symbolValue;
    }


    /**
     *  Sets the VALUES field to contain the cards needed
     *  for four players.
     */
    public static void setValuesForFourPlayers() {
        VALUES = VALUES_FOUR_PLAYERS;
    }


    /**
     *  Sets the VALUES field to contain the cards needed
     *  for less than four players.
     */
    public static void setValuesLessThanFourPlayers() {
        VALUES = VALUES_LESS_THAN_FOUR_PLAYERS;
    }


    /**
     *  Returns a description of this rank.
     *  @return the name of this rank.
     */
    public String getName() {
        return name;
    }


    /**
     *  Returns a description of this rank.
     *  @return the name of this rank.
     */
    public String toString() {
        return name;
    }


    /**
     *  The symbol associated with this rank.  Returns the symbol, which
     *  usually constitutes a single character, in the form of a string.
     *  Symbol is used for the construction of the filenames of the card images.
     *  @return string containing the symbol for the rank.
     */
    public String getSymbol() {
        return symbol;
    }


    /**
     *  Compares the ranks.  Result is dependent on the whether the ace
     *  or the king is considered to be the high rank.
     *  @param o the other rank.
     *  @return the arithmetic difference between the compared ranks
     *  based on their ordering in the listing of values.  This result
     *  may differ depending on whether the king or the ace is considered
     *  the high card.  Result will be < 0 if this rank is lower than
     *  the other rank, 0 if the ranks are the same, or > 0 if this
     *  rank is higher than the other rank.
     */
    @Override
    public int compareTo(Rank o) {
        return Integer.compare(VALUES.indexOf(this), VALUES.indexOf(o));
    }

    public void create() {
        System.out.println("hello");
    }

}
