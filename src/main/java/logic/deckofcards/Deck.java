package logic.deckofcards;

import java.util.*;

public class Deck {
    private List<Card> deck;
    private int index;


    /**
     * Creates an empty deck of cards.
     */
    public Deck() {
        deck = new LinkedList<>();
        index = 0;
    }


    /**
     * Adds 24 cards to the deck. If there are four players, adds
     * additionally the SEVEN's and EIGHT's.
     */
    public void populateDeck(int numberOfPlayers) {
        EnumSet<Card.Rank> rankRange;
        if (numberOfPlayers == 4) {
            rankRange = EnumSet.range(Card.Rank.SEVEN, Card.Rank.ACE);
        } else {
            rankRange = EnumSet.range(Card.Rank.NINE, Card.Rank.ACE);
        }

        for (Card.Rank r : rankRange) {
            for (Card.Suit s : Card.Suit.values()) {
                addCard(new Card(s, r));
            }
        }
    }


    /**
     * Adds a card to the deck.
     * @param card card to be added to the deck.
     */
    public void addCard(Card card) {
        deck.add(card);
    }


    /**
     * Sets the trump suit of the current game and
     * updates the points of every card
     * @param suit the new trump suit
     */
    public void setTrumpSuit(Card.Suit suit) {
        suit.setTrump();

        for (Card card : deck) {
            card.updatePoints();
        }
    }


    /**
     * The size of a deck of cards.
     * @return the number of cards present in the full deck.
     */
    public int getSizeOfDeck() {
        return deck.size();
    }


    /**
     * The number of cards left in the deck.
     * @return the number of cards left to be dealt from the deck.
     */
    public int getNumberOfCardsRemaining() {
        return deck.size() - index;
    }


    /**
     * Deal one card from the deck.
     * @return a card from the deck, or the null reference if there
     * are no cards left in the deck.
     */
    public Card dealCard() {
        if (index >= deck.size()) {
            return null;
        }
        return deck.get(index++);
    }


    /**
     * Returns the top card from the deck without removing it.
     * @return the top card from the deck, or the null reference
     * if there are no cards left in the deck
     */
    public Card peekCard() {
        if (index >= deck.size()) {
            return null;
        }
        return deck.get(index);
    }


    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }


    /**
     * Looks for an empty deck.
     * @return <code>true</code> if there are no cards left to be dealt from the deck.
     */
    public boolean isEmpty() {
        return index >= deck.size();
    }


    /**
     * Restores the deck to "full deck" status.
     */
    public void restoreDeck() {
        index = 0;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "deck=" + deck +
                '}';
    }
}

