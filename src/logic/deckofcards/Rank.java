package logic.deckofcards;

public enum Rank {
    SEVEN("7", 0),
    EIGHT("8", 0),
    NINE("9", 0),
    TEN("t", 10),
    JACK("j", 2),
    QUEEN("q", 3),
    KING("k", 4),
    ACE("a", 11);

    private final String symbol;
    private final int defaultPoints;


    Rank(String symbolValue, int defaultPoints) {
        this.symbol = symbolValue;
        this.defaultPoints = defaultPoints;
    }


    /**
     * The default number of points of the given rank.
     */
    public int getDefaultPoints() {
        return defaultPoints;
    }


    /**
     *  The symbol associated with this rank. Returns the symbol, which
     *  usually constitutes a single character, in the form of a string.
     *  Symbol is used for the construction of the filenames of the card images.
     *  @return string containing the symbol for the rank.
     */
    public String getSymbol() {
        return symbol;
    }
}
