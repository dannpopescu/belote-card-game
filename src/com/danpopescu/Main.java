package com.danpopescu;

import logic.Card;
import logic.Rank;
import logic.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Card> hand = List.of(new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.QUEEN));

        // Counts fours of same kind
        Map<Rank, Long> counting = hand.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        List<Rank> fours = counting.entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());




        List<Rank> spades = filterRanks(hand, Suit.SPADES);
        List<Rank> clubs = filterRanks(hand, Suit.CLUBS);
        List<Rank> diamonds = filterRanks(hand, Suit.DIAMONDS);
        List<Rank> hearts = filterRanks(hand, Suit.HEARTS);

//        boolean tartSpades = tart(spades);
//        boolean tartDiamonds = tart(diamonds);
//        boolean tartHearts = tart(hearts);

        List<Integer> highestIndexes = tart(clubs);




        System.out.println();
        System.out.println();
        System.out.println();


    }

    public static List<Rank> filterRanks(List<Card> hand, Suit searchedSuit) {
        return hand.stream()
                .filter(card -> card.getSuit() == searchedSuit)
                .map(Card::getRank)
                .collect(Collectors.toList());

    }

    public static List<Integer> tart(List<Rank> sameSuitCards) {
        int[] cards = new int[8];

        int index = 0;
        for (Rank rank : Rank.values()) {
            if (sameSuitCards.contains(rank)) {
                cards[index] = 1;
            }
            index++;
        }

        boolean tart = false;
        List<Integer> highestCardIndexes = new ArrayList<>();
        for (int i = 0; i <= cards.length - 3; i++) {
            int consecutiveIndexes = cards[i] + cards[i + 1] + cards[i + 2];
            if (consecutiveIndexes == 3) {
                tart = true;
                highestCardIndexes.add(i + 2);
            }
        }

        return (highestCardIndexes.isEmpty()) ? null : highestCardIndexes;
    }
}
