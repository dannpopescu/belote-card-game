package logic.declarations;

import logic.deckofcards.Card;

import java.util.Map;

public enum FourOfAKind implements Declaration {
    JACK("Four Jacks", 200, Card.Rank.JACK),
    NINE("Four Nines", 140, Card.Rank.NINE),
    ACE("Four Aces", 100, Card.Rank.ACE),
    TEN("Four Tens", 100, Card.Rank.TEN),
    KING("Four Kings", 100, Card.Rank.KING),
    QUEEN("Four Queens", 100, Card.Rank.QUEEN),
    EIGHT("Four Eights", 0, Card.Rank.EIGHT),
    SEVEN("Four Sevens", 0, Card.Rank.SEVEN);

    private static final Map<Card.Rank, FourOfAKind> fourOfAKinds = Map.of(
            Card.Rank.JACK, JACK,
            Card.Rank.NINE, NINE,
            Card.Rank.ACE, ACE,
            Card.Rank.TEN, TEN,
            Card.Rank.KING, KING,
            Card.Rank.QUEEN, QUEEN,
            Card.Rank.EIGHT, EIGHT,
            Card.Rank.SEVEN, SEVEN

    );

    private final String name;
    private final int points;
    private final Card.Rank rank;

    FourOfAKind(String name, int points, Card.Rank rank) {
        this.name = name;
        this.points = points;
        this.rank = rank;
    }

    public static FourOfAKind getFourOfAKind(Card.Rank rank) {
        return fourOfAKinds.get(rank);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public Card.Rank getHighestRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "FourOfAKind{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", rank=" + rank +
                '}';
    }
}
