package logic.declarations;

import logic.deckofcards.Card;
import logic.deckofcards.Rank;

import java.util.Comparator;
import java.util.List;

public class SequenceOfCards implements IDeclaration {

    private String name;
    private int points;
    private List<Card> cards;

    public SequenceOfCards(List<Card> cards) {
        this.cards = cards;
        this.cards.sort(Comparator.comparing(Card::getRank));

        switch (cards.size()) {
            case 3:
                this.name = "Tart";
                this.points = 20;
                break;
            case 4:
                this.name = "Jumatate de suta";
                this.points = 50;
                break;
            case 5:
                this.name = "O suta";
                this.points = 100;
                break;
            case 6: case 7: case 8:
                this.name = "O suta cincizeci";
                this.points = 150;
                break;
        }
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
        Card highestCard = cards.get(cards.size() - 1);
        return highestCard.getRank();
    }

    @Override
    public String toString() {
        return "SequenceOfCards{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", cards=" + cards +
                '}';
    }
}
