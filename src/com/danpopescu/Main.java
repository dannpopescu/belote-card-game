package com.danpopescu;

import logic.deckofcards.Card;
import logic.deckofcards.Rank;
import logic.deckofcards.Suit;
import logic.declarations.SequenceOfCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Card> hand = List.of(
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.KING),
                new Card(Suit.HEARTS, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.HEARTS, Rank.EIGHT),
                new Card(Suit.DIAMONDS, Rank.JACK),
                new Card(Suit.SPADES, Rank.JACK),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.QUEEN));

        // Counts fours of same kind
        Map<Rank, Long> counting = hand.stream().collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        List<Rank> fours = counting.entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        List<SequenceOfCards> combinations = getSequenceOfCardsDeclarations(hand);

//        List<Rank> spades = filterRanks(hand, Suit.SPADES);
//        List<Rank> clubs = filterRanks(hand, Suit.CLUBS);
//        List<Rank> diamonds = filterRanks(hand, Suit.DIAMONDS);
//        List<Rank> hearts = filterRanks(hand, Suit.HEARTS);
//
//        boolean tartSpades = tart(spades);
//        boolean tartDiamonds = tart(diamonds);
//        boolean tartHearts = tart(hearts);
//
//        List<List<Rank>> clubsCombinations = tart(clubs);




        System.out.println();
        System.out.println();
        System.out.println();


    }

    public static List<SequenceOfCards> getSequenceOfCardsDeclarations(List<Card> hand) {
        List<List<Card>> validSequencesOfCards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            List<List<Rank>> validSequencesOfRanksInHand = getValidSequencesOfRanks(hand, suit);

            for (List<Rank> sequenceOfRanks : validSequencesOfRanksInHand) {
                validSequencesOfCards.add(
                        hand.stream()
                                .filter(card -> sequenceOfRanks.contains(card.getRank()))
                                .filter(card -> card.getSuit() == suit)
                                .collect(Collectors.toList())
                );
            }
        }

        return validSequencesOfCards.stream()
                .map(SequenceOfCards::new)
                .collect(Collectors.toList());
    }


    public static List<Rank> filterRanks(List<Card> hand, Suit searchedSuit) {
        return hand.stream()
                .filter(card -> card.getSuit() == searchedSuit)
                .map(Card::getRank)
                .collect(Collectors.toList());
    }



    /**
     * Returns an array of zeros and ones of size equal to the number of Ranks used
     * in the game. The zero/one in this array shows the absence/presence of a Rank
     * of the given suit in the player's hand. For example:
     * return array:     [0, 1, 1, 1, 0, 0, 1, 1]
     * Rank.values():    [7, 8, 9, T, J, Q, K, A]
     * This means that there is one 8, 9, T, K, A cards of the suit given as parameter
     * in the list of cards given as parameter.
     *
     * @param hand a list of cards in which to search for the frequency of cards
     * @param suit the suit of the cards to search for
     * @return an array of int filled with zeros and ones
     */
    public static int[] getCardsFrequencyArray(List<Card> hand, Suit suit) {
        int[] cardsFrequency = new int[8];

        List<Rank> ranksOfTheGivenSuit = filterRanks(hand, suit);

        int index = 0;
        for (Rank rank : Rank.values()) {
            if (ranksOfTheGivenSuit.contains(rank)) {
                cardsFrequency[index] = 1;
            } else {
                cardsFrequency[index] = 0;
            }
            index++;
        }

        return cardsFrequency;
    }


    public static List<List<Rank>> getValidSequencesOfRanks(List<Card> hand, Suit suit) {
        int[] cardsFrequency = getCardsFrequencyArray(hand, suit);

        List<List<Rank>> combinations = new ArrayList<>();

        int minNumberOfConsecutiveCards = 3;

        outerloop:
        for (int i = 0; i <= cardsFrequency.length - minNumberOfConsecutiveCards; i++) {
            if (!combinations.isEmpty()) {
                for (List<Rank> list : combinations) {
                    if (list.contains(Rank.values()[i])) {
                        continue outerloop;
                    }
                }
            }

            int numberOfConsecutiveCards = cardsFrequency[i] + cardsFrequency[i + 1] + cardsFrequency[i + 2];
            if (numberOfConsecutiveCards == 3) {
                List<Rank> combination = new ArrayList<>(
                        List.of(Rank.values()[i],
                                Rank.values()[i + 1],
                                Rank.values()[i + 2])
                );

                // check if more consecutive cards follows right after the newly found combination
                int nextIndexToCheck = i + 3;
                for (int j = nextIndexToCheck; j < cardsFrequency.length; j++) {
                    if (cardsFrequency[j] == 1) {
                        combination.add(Rank.values()[j]);
                    } else {
                        break; // the streak of consecutive cards ends
                    }
                }
                combinations.add(combination);
            }
        }

        return combinations;
    }

//    public static List<List<Rank>> tart(List<Rank> sameSuitCards) {
//        int[] cardsFrequency = new int[8];
//
//        int index = 0;
//        for (Rank rank : Rank.values()) {
//            if (sameSuitCards.contains(rank)) {
//                cardsFrequency[index] = 1;
//            }
//            index++;
//        } // I get an array that can look like [0, 1, 1, 1, 0, 0, 1, 1] where one means that there is a card and zero that there is not
//        // the index of zeroes and ones in this array is the index of the corresponding card in Rank.values() array, so that
//        // cardsFrequency      [0, 1, 1, 1, 0, 0, 1, 1]
//        // Rank.values()       [7, 8, 9, T, J, Q, K, A]
//        // this means that there is one 8, 9, T, K, A in the given sameSuitCards list
//
//
//        List<List<Rank>> combinations = new ArrayList<>();
//
//        int minConsecutiveCards = 3;
//
//        outerloop:
//        for (int i = 0; i <= cardsFrequency.length - minConsecutiveCards; i++) {
//            if (!combinations.isEmpty()) {
//                for (List list : combinations) {
//                    if (list.contains(Rank.values()[i])) {
//                        continue outerloop;
//                    }
//                }
//            }
//
//            int numberOfConsecutiveCards = cardsFrequency[i] + cardsFrequency[i + 1] + cardsFrequency[i + 2];
//            if (numberOfConsecutiveCards == 3) {
//                List<Rank> combination = new ArrayList<>(
//                        List.of(Rank.values()[i],
//                                Rank.values()[i + 1],
//                                Rank.values()[i + 2])
//                );
//
//                int nextIndexToCheck = i + 3;
//                for (int j = nextIndexToCheck; j < cardsFrequency.length; j++) {
//                    if (cardsFrequency[j] == 1) {
//                        combination.add(Rank.values()[j]);
//                    } else {
//                        break;
//                    }
//                }
//                combinations.add(combination);
//            }
//        }
//
//        return combinations;
//    }
}
