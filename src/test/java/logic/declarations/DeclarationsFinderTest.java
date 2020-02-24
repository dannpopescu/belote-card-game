package logic.declarations;

import logic.deckofcards.Card;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class DeclarationsFinderTest {

    @Test
    void canFindFourJacks() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.JACK),
                new Card(Card.Suit.CLUBS, Card.Rank.JACK),
                new Card(Card.Suit.HEARTS, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.JACK), declaration,
                "Should find a declaration of four Jacks.");
    }

    @Test
    void canFindFourNines() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
                new Card(Card.Suit.CLUBS, Card.Rank.NINE),
                new Card(Card.Suit.HEARTS, Card.Rank.NINE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.NINE), declaration,
                "Should find a declaration of four Nines.");
    }

    @Test
    void canFindFourAces() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.ACE),
                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.ACE), declaration,
                "Should find a declaration of four Aces.");
    }

    @Test
    void canFindFourTens() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
                new Card(Card.Suit.CLUBS, Card.Rank.TEN),
                new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.TEN), declaration,
                "Should find a declaration of four Tens.");
    }

    @Test
    void canFindFourKings() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.CLUBS, Card.Rank.KING),
                new Card(Card.Suit.HEARTS, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.KING), declaration,
                "Should find a declaration of four Kings.");
    }

    @Test
    void canFindFourQueens() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
                new Card(Card.Suit.CLUBS, Card.Rank.QUEEN),
                new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.JACK)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.QUEEN), declaration,
                "Should find a declaration of four Queens.");
    }

    @Test
    void canFindFourEights() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT),
                new Card(Card.Suit.CLUBS, Card.Rank.EIGHT),
                new Card(Card.Suit.HEARTS, Card.Rank.EIGHT),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.JACK)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.EIGHT), declaration,
                "Should find a declaration of four Eights.");
    }

    @Test
    void canFindFourSevens() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN),
                new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
                new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.JACK)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertEquals(FourOfAKind.getFourOfAKind(Card.Rank.SEVEN), declaration,
                "Should find a declaration of four Sevens.");
    }

    @Test
    void canFindSequenceOfThree() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
                new Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(20, declaration.getPoints(), "Declaration should have 20 points."),
                () -> assertEquals(Card.Rank.KING, declaration.getHighestRank(), "Highest rank should be a King.")
        );
    }

    @Test
    void canFindTwoSequencesOfThree() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        );

        // when
        Declaration firstDeclaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);
        Declaration secondDeclaration = DeclarationsFinder.getDeclarations(handOfCards).get(1);

        List<Card.Rank> declarationHighestRanks = Arrays.asList(
                firstDeclaration.getHighestRank(),
                secondDeclaration.getHighestRank()
        );

        // then
        assertAll(
                () -> assertEquals(20, firstDeclaration.getPoints(), "Declaration should have 20 points."),
                () -> assertEquals(20, secondDeclaration.getPoints(), "Declaration should have 20 points."),
                () -> assertThat("Highest ranks should be King and Ace.",
                        declarationHighestRanks, containsInAnyOrder(Card.Rank.KING, Card.Rank.ACE))
        );
    }

    @Test
    void canFindSequencesOfFour() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(50, declaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(Card.Rank.QUEEN, declaration.getHighestRank(), "Highest rank should be a Queen.")
        );
    }

    @Test
    void canFindTwoSequencesOfFour() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        );

        // when
        Declaration firstDeclaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);
        Declaration secondDeclaration = DeclarationsFinder.getDeclarations(handOfCards).get(1);

        List<Card.Rank> declarationHighestRanks = Arrays.asList(
                firstDeclaration.getHighestRank(),
                secondDeclaration.getHighestRank()
        );

        // then
        assertAll(
                () -> assertEquals(50, firstDeclaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(50, secondDeclaration.getPoints(), "Declaration should have 50 points."),
                () -> assertThat("Highest ranks should be Queen and Ace.",
                        declarationHighestRanks, containsInAnyOrder(Card.Rank.QUEEN, Card.Rank.ACE))
        );
    }

    @Test
    void canFindSequencesOfFive() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(100, declaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(Card.Rank.KING, declaration.getHighestRank(), "Highest rank should be a King.")
        );
    }

    @Test
    void canFindSequencesOfSix() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.ACE)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(150, declaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(Card.Rank.ACE, declaration.getHighestRank(), "Highest rank should be a Ace.")
        );
    }

    @Test
    void canFindSequencesOfSeven() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.ACE)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(150, declaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(Card.Rank.ACE, declaration.getHighestRank(), "Highest rank should be a Ace.")
        );
    }

    @Test
    void canFindSequencesOfEight() {
        // given
        List<Card> handOfCards = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.SEVEN),
                new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.ACE)
        );

        // when
        Declaration declaration = DeclarationsFinder.getDeclarations(handOfCards).get(0);

        // then
        assertAll(
                () -> assertEquals(150, declaration.getPoints(), "Declaration should have 50 points."),
                () -> assertEquals(Card.Rank.ACE, declaration.getHighestRank(), "Highest rank should be a Ace.")
        );
    }
}