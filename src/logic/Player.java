package logic;

import java.util.*;

public class Player implements Comparable<Player> {
    private String name;
    private int score;
    private int bt;
    private boolean isDealer;
    private boolean ownsTheGame;
    private List<Card> hand;
    private Set<Card> collectedCards;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
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

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    public Card removeCard(Card card) {
        if (hand.remove(card)) {
            return card;
        } else {
            return null;
        }
    }

    public Card removeCard(int index) {
        try {
            return hand.remove(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void discardHand() {
        hand.clear();
    }

    public int getHandSize() {
        return hand.size();
    }

    public boolean isHandEmpty() {
        return hand.isEmpty();
    }

    public boolean handContainsCard(Card card) {
        return hand.contains(card);
    }

    public String handToStringForTable() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card).append(", ");
        }
        return sb.toString();
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
    public int compareTo(Player o) {
        return Integer.compare(score, o.score);
    }

    @Override
    public String toString() {
        return name;
    }
}
