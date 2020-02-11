package logic;

import java.util.*;

public class Player {
    private String name;
    private int score;
    private Hand hand;
    private int bt;
    private boolean isDealer;
    private boolean ownsTheGame;
    private Set<Card> collectedCards;
    private List<Card> handCards;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new Hand();
        this.collectedCards = new HashSet<>();
    }

    public void addOneBT() {
        bt++;
        if (bt == 3) {
            score -= 10;
            bt = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int score) {
        this.score += score;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public void setDealer(boolean dealer) {
        isDealer = dealer;
    }

    public boolean ownsTheGame() {
        return ownsTheGame;
    }

    public void setOwnsTheGame(boolean ownsTheGame) {
        this.ownsTheGame = ownsTheGame;
    }

    public Hand getHand() {
        return hand;
    }

    public void addCollectedCards(Set<Card> cardsCollected) {
        this.collectedCards.addAll(cardsCollected);
    }

    public Set<Card> getCollectedCards() {
        return collectedCards;
    }

    // one match point is equal to ten points. rounding starts at 6
    public int getMatchPoints() {
        int points = 0;

        for (Card card : collectedCards) {
            points += card.getPoints();
        }

        int matchPoints = points / 10;

        if (points % 10 >= 6) {
            matchPoints++;
        }

        return matchPoints;
    }

    @Override
    public String toString() {
        return name;
    }
}
