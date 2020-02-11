package logic;

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


    /**
     * Returns true if the suit is the current trump suit.
     */
    public boolean isTrump() {
        return isTrump;
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
}
