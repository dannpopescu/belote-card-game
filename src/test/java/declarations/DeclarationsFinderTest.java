package declarations;

import com.danpopescu.belote.deck.Card;
import com.danpopescu.belote.declarations.Declaration;
import com.danpopescu.belote.declarations.DeclarationsFinder;
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
        assertAll(
                () -> assertEquals(200, declaration.getPoints(), "Declaration should have 200 points."),
                () -> assertEquals(Card.Rank.JACK, declaration.getHighestRank(), "Highest rank should be a Jack.")
        );
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
        assertAll(
                () -> assertEquals(140, declaration.getPoints(), "Declaration should have 140 points."),
                () -> assertEquals(Card.Rank.NINE, declaration.getHighestRank(), "Highest rank should be a Nine.")
        );
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
        assertAll(
                () -> assertEquals(100, declaration.getPoints(), "Declaration should have 100 points."),
                () -> assertEquals(Card.Rank.ACE, declaration.getHighestRank(), "Highest rank should be an Ace.")
        );
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
        assertAll(
                () -> assertEquals(100, declaration.getPoints(), "Declaration should have 100 points."),
                () -> assertEquals(Card.Rank.TEN, declaration.getHighestRank(), "Highest rank should be a Ten.")
        );
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
        assertAll(
                () -> assertEquals(100, declaration.getPoints(), "Declaration should have 100 points."),
                () -> assertEquals(Card.Rank.KING, declaration.getHighestRank(), "Highest rank should be a King.")
        );
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
        assertAll(
                () -> assertEquals(100, declaration.getPoints(), "Declaration should have 100 points."),
                () -> assertEquals(Card.Rank.QUEEN, declaration.getHighestRank(), "Highest rank should be a Queen.")
        );
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
        assertAll(
                () -> assertEquals(0, declaration.getPoints(), "Declaration should have 0 points."),
                () -> assertEquals(Card.Rank.EIGHT, declaration.getHighestRank(), "Highest rank should be a Eight.")
        );
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
        assertAll(
                () -> assertEquals(0, declaration.getPoints(), "Declaration should have 0 points."),
                () -> assertEquals(Card.Rank.SEVEN, declaration.getHighestRank(), "Highest rank should be a Seven.")
        );
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

    @Test
    void FourJacksStrongerThanFourNines() {
        // given
        List<Card> handOfJack = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.JACK),
                new Card(Card.Suit.CLUBS, Card.Rank.JACK),
                new Card(Card.Suit.HEARTS, Card.Rank.JACK),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        List<Card> handOfNine = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
                new Card(Card.Suit.CLUBS, Card.Rank.NINE),
                new Card(Card.Suit.HEARTS, Card.Rank.NINE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        Declaration fourJack = DeclarationsFinder.getDeclarations(handOfJack).get(0);
        Declaration fourNine = DeclarationsFinder.getDeclarations(handOfNine).get(0);

        // when
        assertEquals(1, fourJack.compareTo(fourNine), "Four Jacks is a stronger declaration than four Nines.");
    }

    @Test
    void FourAcesStrongerThanASequenceOfFive() {
        List<Card> handOfAce = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.ACE),
                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        List<Card> sequence = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING)
        );

        Declaration fourAce = DeclarationsFinder.getDeclarations(handOfAce).get(0);
        Declaration sequenceOfFive = DeclarationsFinder.getDeclarations(sequence).get(0);

        assertEquals(1, fourAce.compareTo(sequenceOfFive), "Four Aces is a stronger declaration than any sequence of Five.");
    }

    @Test
    void SequenceOfSixStrongerThanFourAces() {
        List<Card> handOfAce = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                new Card(Card.Suit.CLUBS, Card.Rank.ACE),
                new Card(Card.Suit.HEARTS, Card.Rank.ACE),
                new Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN)
        );

        List<Card> sequence = Arrays.asList(
                new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                new Card(Card.Suit.SPADES, Card.Rank.NINE),
                new Card(Card.Suit.SPADES, Card.Rank.TEN),
                new Card(Card.Suit.SPADES, Card.Rank.JACK),
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                new Card(Card.Suit.SPADES, Card.Rank.KING)
        );

        Declaration fourAce = DeclarationsFinder.getDeclarations(handOfAce).get(0);
        Declaration sequenceOfSix = DeclarationsFinder.getDeclarations(sequence).get(0);

        assertEquals(-1, fourAce.compareTo(sequenceOfSix), "A sequence of six is stronger than four Aces.");
    }
}