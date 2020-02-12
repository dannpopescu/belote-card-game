package logic.declarations;

import logic.deckofcards.Rank;

import java.util.Map;

public enum FourOfAKind implements IDeclaration {
    JACK("Four Jacks", 200, Rank.JACK),
    NINE("Four Nines", 140, Rank.NINE),
    ACE("Four Aces", 100, Rank.ACE),
    TEN("Four Tens", 100, Rank.TEN),
    KING("Four Kings", 100, Rank.KING),
    QUEEN("Four Queens", 100, Rank.QUEEN),
    EIGHT("Four Eights", 0, Rank.EIGHT),
    SEVEN("Four Sevens", 0, Rank.SEVEN);

    private static final Map<Rank, FourOfAKind> fourOfAKinds = Map.of(
            Rank.JACK, JACK,
            Rank.NINE, NINE,
            Rank.ACE, ACE,
            Rank.TEN, TEN,
            Rank.KING, KING,
            Rank.QUEEN, QUEEN,
            Rank.EIGHT, EIGHT,
            Rank.SEVEN, SEVEN

    );

    private final String name;
    private final int points;
    private final Rank rank;

    FourOfAKind(String name, int points, Rank rank) {
        this.name = name;
        this.points = points;
        this.rank = rank;
    }

    public static FourOfAKind getFourOfAKind(Rank rank) {
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
    public Rank getHighestRank() {
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
