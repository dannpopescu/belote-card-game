package logic.deckofcards;

import ui.ConsoleColors;

public class Card implements Comparable<Card> {

    private Suit suit;
    private Rank rank;
    private int points;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.points = rank.defaultPoints;
    }


    public void updatePoints() {
        if (rank == Rank.JACK && suit.isTrump) {
            this.points = 20;
        } else if (rank == Rank.NINE && suit.isTrump) {
            this.points = 14;
        } else {
            this.points = rank.defaultPoints;
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
        String color = "";

        if (suit == Suit.SPADES) {
            color = ConsoleColors.BLUE;
        } else if (suit == Suit.CLUBS) {
            color = ConsoleColors.GREEN;
        } else if (suit == Suit.HEARTS) {
            color = ConsoleColors.RED;
        } else if (suit == Suit.DIAMONDS) {
            color = ConsoleColors.YELLOW;
        }

        return color + rank.fileSymbol.toUpperCase() + "" + suit.unicodeSymbol + ConsoleColors.RESET;
    }

    public String toStringUncolored() {
        return rank.fileSymbol.toUpperCase() + "" + suit.unicodeSymbol;
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
        if (Boolean.compare(this.suit.isTrump, card.suit.isTrump) == 0) {
            return Integer.compare(points, card.points);
        } else {
            return Boolean.compare(this.suit.isTrump, card.suit.isTrump);
        }
    }


    public enum Rank {
        SEVEN("7", 0),
        EIGHT("8", 0),
        NINE("9", 0),
        TEN("t", 10),
        JACK("j", 2),
        QUEEN("q", 3),
        KING("k", 4),
        ACE("a", 11);

        private final String fileSymbol;
        private final int defaultPoints;


        Rank(String fileSymbol, int defaultPoints) {
            this.fileSymbol = fileSymbol;
            this.defaultPoints = defaultPoints;
        }
    }


    public enum Suit {
        CLUBS("c", '\u2663'),
        DIAMONDS("d", '\u2666'),
        HEARTS("h", '\u2665'),
        SPADES("s", '\u2660');

        private final String fileSymbol;
        private final char unicodeSymbol;
        private boolean isTrump;


        Suit(String fileSymbol, char unicodeSymbol) {
            this.fileSymbol = fileSymbol;
            this.unicodeSymbol = unicodeSymbol;
            this.isTrump = false;
        }


        public boolean isTrump() {
            return isTrump;
        }


        /**
         * Clears the trump suit of the previous game and sets
         * the trump suit of the current game.
         */
        public void setTrump() {
            for (Suit suit : Suit.values()) {
                suit.isTrump = false;
            }
            isTrump = true;
        }
    }
}

