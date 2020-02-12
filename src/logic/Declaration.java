package logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Declaration {
    private String name;
    private int points;
    private List<Card> cards;

    public static final Declaration JACK_CARRE =
            new Declaration("Carre of Jacks", 200, Rank.JACK);


    public static final Declaration NINE_CARRE =
            new Declaration("Carre of Nines", 140, Rank.NINE);


    public static final Declaration ACE_CARRE =
            new Declaration("Carre of Aces", 100, Rank.ACE);


    public static final Declaration TEN_CARRE =
            new Declaration("Carre of Tens", 100, Rank.TEN);


    public static final Declaration KING_CARRE =
            new Declaration("Carre of Kings", 100, Rank.KING);


    public static final Declaration QUEEN_CARRE =
            new Declaration("Carre of Queens", 100, Rank.QUEEN);



    public Declaration(String name, int points, List<Card> cards) {
        this.name = name;
        this.points = points;
        this.cards = cards;
    }

    private Declaration(String name, int points, Rank rank) {
        this.name = name;
        this.points = points;
        this.cards = List.of(new Card(Suit.SPADES, rank),
                new Card(Suit.DIAMONDS, rank),
                new Card(Suit.CLUBS, rank),
                new Card(Suit.HEARTS, rank));
    }

    public Declaration(List<Card> cards) {
        this.cards = cards;
        this.cards.sort(Comparator.comparing(Card::getRank));
//        Collections.sort(this.cards);

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
}
