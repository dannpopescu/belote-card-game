package logic.deckofcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    public static final int CARDS_FOR_THREE_PLAYERS = 24;
    public static final int CARDS_FOR_FOUR_PLAYERS = 32;

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void shouldBe24CardsForThreePlayers() {
        // when
        deck.populateDeck(3);

        // then
        assertEquals(CARDS_FOR_THREE_PLAYERS, deck.getSizeOfDeck(), "There aren't 24 cards in the deck.");
        for (int i = 0; i < CARDS_FOR_THREE_PLAYERS; i++) {
            Card dealtCard = deck.dealCard();
            assertNotEquals(Card.Rank.SEVEN, dealtCard.getRank(), "There shouldn't exist a seven in the deck when there are 3 players.");
            assertNotEquals(Card.Rank.EIGHT, dealtCard.getRank(), "There shouldn't exist an eight in the deck when there are 3 players.");
        }
    }

    @Test
    void shouldBe32CardsForFourPlayers() {
        // when
        deck.populateDeck(4);

        // then
        assertEquals(CARDS_FOR_FOUR_PLAYERS, deck.getSizeOfDeck(), "There aren't 32 cards in the deck.");
    }

    @Test
    void canAddAndDealACard() {
        // given
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);

        // when
        deck.addCard(card);

        // then
        assertEquals(card, deck.dealCard(), "The card was not added to the deck.");
    }

    @Test
    void canSetTrumpSuit() {
        // when
        deck.setTrumpSuit(Card.Suit.SPADES);

        // then
        assertTrue(Card.Suit.SPADES.isTrump(), "The trump suit was not set correctly.");
    }

    @Test
    void canResetTrumpSuit() {
        // when
        deck.setTrumpSuit(Card.Suit.SPADES);
        deck.setTrumpSuit(Card.Suit.DIAMONDS);

        // then
        assertFalse(Card.Suit.SPADES.isTrump(), "Spades shouldn't be the trump suit.");
        assertTrue(Card.Suit.DIAMONDS.isTrump(), "Diamonds should be set as the trump suit.");
    }

    @Test
    void canRestoreDeck() {
        // given
        deck.populateDeck(4);
        Card card1 = deck.dealCard();
        Card card2 = deck.dealCard();
        Card card3 = deck.dealCard();
        Card card4 = deck.dealCard();

        // then
        assertEquals(CARDS_FOR_FOUR_PLAYERS - 4, deck.getNumberOfCardsRemaining(),
                "The number of cards remaining should be 28.");

        // when
        deck.restoreDeck();

        // then
        assertAll(
                () -> assertEquals(card1, deck.dealCard(), "Dealt cards are not equal."),
                () -> assertEquals(card2, deck.dealCard(), "Dealt cards are not equal."),
                () -> assertEquals(card3, deck.dealCard(), "Dealt cards are not equal."),
                () -> assertEquals(card4, deck.dealCard(), "Dealt cards are not equal.")
        );
    }
}