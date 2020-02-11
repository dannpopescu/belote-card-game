package logic;

import java.util.*;

public class Player implements Comparable<Player> {

    /**
     * Player's name
     */
    private String name;

    /**
     * Player's game score calculated in Match Points, which
     * is different from Card Points. One Match Point is roughly equal
     * to 10 Card Points. Rounding up starts at six.
     */
    private int score;

    /**
     * Player's number of BT's. When reaches 3,
     * the score is diminished by 10 and BT is zero again.
     */
    private int bt;

    /**
     * True if player is the dealer in the current round
     */
    private boolean isDealer;

    /**
     * True if player chose the trump suit of the game round
     */
    private boolean choseTrumpSuit;

    /**
     * List of player's cards in the current round
     */
    private List<Card> hand;

    /**
     * Set of cards from won tricks in the current round
     */
    private Set<Card> wonTricks;


    /**
     * Constructs a new player
     * @param name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
        this.wonTricks = new HashSet<>();
    }


    /**
     * Returns the player's name
     * @return the name of the player
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the player's score in the current game
     * @return the score of the player in the current game
     */
    public int getScore() {
        return score;
    }


    /**
     * Increases the current game score
     * @param score match points accumulated in the current round
     */
    public void increaseScore(int score) {
        this.score += score;
    }


    /**
     * Returns true if player is the dealer in the current round
     * @return true if player is the dealer
     */
    public boolean isDealer() {
        return isDealer;
    }


    /**
     * Sets the player the dealer of the round to true or false
     * @param dealer true if player is the dealer of the current round
     */
    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }


    /**
     * Returns true if player chose the trump suit in the current round
     * @return true i player chose the trump suit
     */
    public boolean choseTrumpSuit() {
        return choseTrumpSuit;
    }


    /**
     * Records the fact that player chose the trump suit of the round
     * @param choseTrumpSuit true if player chose the trump suit
     */
    public void setChoseTrumpSuit(boolean choseTrumpSuit) {
        this.choseTrumpSuit = choseTrumpSuit;
    }


    /**
     * Adds one BT to the player. If BT count reaches 3, the score is
     * decreased by 10, and the BT count is zeroed.
     */
    public void addOneBT() {
        if (++bt == 3) {
            score -= 10;
            bt = 0;
        }
    }


    /**
     * Adds one card to the player's hand in the current round
     * @param card to be added
     */
    public void addCard(Card card) {
        hand.add(card);
    }


    /**
     * Returns a card from the player's hand without removing it.
     * @param index of the card in the hand list
     * @return a card reference
     */
    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Returns a card and removes it from player's hand.
     * If the card doesn't exist, returns null.
     * @param card to be removed
     * @return back a card reference or null if the card doesn't exist in the player's hand
     */
    public Card removeCard(Card card) {
        if (hand.remove(card)) {
            return card;
        }
        return null;
    }


    /**
     * Returns a card and removes it from player's hand
     * @param index of the card in the hand list
     * @return the card reference that was removed or null if the index is out of bound
     */
    public Card removeCard(int index) {
        try {
            return hand.remove(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Returns the number of cards left in the player's hand
     * @return a integer representing the number of cards in the hand
     */
    public int getHandSize() {
        return hand.size();
    }


    /**
     * Removes all the cards from the player's hand
     */
    public void discardHand() {
        hand.clear();
    }


    /**
     * Adds a collection of cards representing the trick won by the player
     * @param wonTrick a Set of Cards representing the won trick
     */
    public void addWonTrick(Set<Card> wonTrick) {
        this.wonTricks.addAll(wonTrick);
    }


    /**
     * Returns a Set of cards collected by the player in the tricks won by him
     * @return a Set of Cards won by the player
     */
    public Set<Card> getWonTricks() {
        return wonTricks;
    }


    /**
     * Calculates the number of Match Points won by the player in the
     * current round. One match points is equal to ten card points.
     * Rounding up starts at six.
     * @return an integer representing the player's match points in the current game
     */
    public int getMatchPoints() {
        int cardPoints = 0;

        for (Card card : wonTricks) {
            cardPoints += card.getPoints();
        }

        int matchPoints = cardPoints / 10;

        if (cardPoints % 10 >= 6) {
            matchPoints++;
        }

        return matchPoints;
    }


    /**
     * Compares two players for the purposes of sorting and getting the winner.
     * Players are ordered by their score in the game represented by the number
     * of Match Points accumulated.
     * @param o the other player
     * @return a negative integer, zero, or a positive integer if this player has
     * a lower, equal, or greater score than the referenced player.
     */
    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.score);
    }


    /**
     * Returns a description of this player.
     * @return the name of the player and it's score.
     */
    @Override
    public String toString() {
        return name + " (" + score + ")";
    }


    /**
     * Returns a String representing the cards in the player's hand.
     * The String is used by the TableGenerators for the CLI.
     * @return a String representing the cards in the player's hand.
     */
    public String handToStringForTable() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card).append(", ");
        }
        return sb.toString();
    }
}
