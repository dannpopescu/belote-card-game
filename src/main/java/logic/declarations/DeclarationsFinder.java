package logic.declarations;

import logic.deckofcards.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeclarationsFinder {

    /**
     * Returns valid declarations found in the given list of cards.
     * A valid declaration is four cards of the same rank or a
     * sequence of cards of the same suit with the minimum length of 3 cards.
     * @param handOfCards a list of cards that a player holds
     * @return a list of IDeclaration references
     */
    public static List<Declaration> getDeclarations(List<Card> handOfCards) {
        List<Declaration> declarations = new ArrayList<>();

        declarations.addAll(getFoursOfAKind(handOfCards));
        declarations.addAll(getSequencesOfCards(handOfCards));

        return declarations;
    }


    public static Declaration getBella(List<Card> handOfCards) {
        Card.Suit trumpSuit = null;
        for (Card.Suit suit : Card.Suit.values()) {
            if (suit.isTrump()) {
                trumpSuit = suit;
            }
        }

        List<Card> bellaCards = Arrays.asList(
                new Card(trumpSuit, Card.Rank.QUEEN),
                new Card(trumpSuit, Card.Rank.KING)
        );

        if (handOfCards.containsAll(bellaCards)) {
            return new SequenceOfCards(bellaCards);
        }

        return null;
    }


    /**
     * Searches for four cards of the same rank and returns a list
     * of FourOfAKind references if anything found
     * @param handOfCards a list of cards that a player holds
     * @return a list of FourOfAKind references
     */
    private static List<FourOfAKind> getFoursOfAKind(List<Card> handOfCards) {
        Map<Card.Rank, Long> rankFrequency = handOfCards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        List<Card.Rank> ranksWithFrequencyFour = rankFrequency.entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Card> cardsOfFrequencyFour = handOfCards.stream()
                .filter(e -> ranksWithFrequencyFour.contains(e.getRank()))
                .collect(Collectors.toList());

        Map<Card.Rank, List<Card>> validFourOfAKind = cardsOfFrequencyFour.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.toList()));

        return validFourOfAKind.values().stream()
                .map(FourOfAKind::new)
                .collect(Collectors.toList());

//        return ranksWithFrequencyFour.stream()
//                .map(FourOfAKind::getFourOfAKind)
//                .collect(Collectors.toList());
    }


    /**
     * Finds valid sequences of cards in a given list. A valid sequence
     * of cards has minimum 3 consecutive cards of the same suit.
     * Consecutiveness is based on Rank.values() order (NOT points order).
     *
     * @param handOfCards a list of cards that a player holds
     * @return a list of SequenceOfCards references
     */
    private static List<SequenceOfCards> getSequencesOfCards(List<Card> handOfCards) {
        List<List<Card>> validSequencesOfCardsAsLists = new ArrayList<>();

        for (Card.Suit suit : Card.Suit.values()) {
            List<List<Card.Rank>> validSequencesOfRanksInHand = getValidSequencesOfRanks(handOfCards, suit);

            for (List<Card.Rank> sequenceOfRanks : validSequencesOfRanksInHand) {
                validSequencesOfCardsAsLists.add(
                        handOfCards.stream()
                                .filter(card -> sequenceOfRanks.contains(card.getRank()))
                                .filter(card -> card.getSuit() == suit)
                                .collect(Collectors.toList())
                );
            }
        }

        return validSequencesOfCardsAsLists.stream()
                .map(SequenceOfCards::new)
                .collect(Collectors.toList());
    }


    /**
     * Returns a valid sequence of Ranks in the given list of cards of the given Suit.
     * A sequence is valid if it has minimum 3 consecutive ranks.
     *
     * @param handOfCards a list of cards that a player holds
     * @param suit the Suit of the Ranks to check against
     * @return a list of lists of valid Rank sequences
     */
    private static List<List<Card.Rank>> getValidSequencesOfRanks(List<Card> handOfCards, Card.Suit suit) {
        int[] cardsFrequency = getRanksFrequencyArray(handOfCards, suit);
        int minNumberOfConsecutiveCards = 3;
        List<List<Card.Rank>> sequencesOfRanks = new ArrayList<>();

        outerloop:
        for (int i = 0; i <= cardsFrequency.length - minNumberOfConsecutiveCards; i++) {
            // check if the rank have been already found to be part of a sequence
            if (!sequencesOfRanks.isEmpty()) {
                for (List<Card.Rank> sequenceOfRanks : sequencesOfRanks) {
                    if (sequenceOfRanks.contains(Card.Rank.values()[i])) {
                        continue outerloop;
                    }
                }
            }

            int numberOfConsecutiveCards = cardsFrequency[i] + cardsFrequency[i + 1] + cardsFrequency[i + 2];
            if (numberOfConsecutiveCards == 3) {
                List<Card.Rank> sequenceOfRanks = new ArrayList<>(
                        List.of(Card.Rank.values()[i],
                                Card.Rank.values()[i + 1],
                                Card.Rank.values()[i + 2])
                );

                // check if more ranks follow right after the newly found sequenceOfRanks
                int nextIndexToCheck = i + 3;
                for (int j = nextIndexToCheck; j < cardsFrequency.length; j++) {
                    if (cardsFrequency[j] == 1) {
                        sequenceOfRanks.add(Card.Rank.values()[j]);
                    } else {
                        break; // the streak of consecutive cards ends
                    }
                }
                sequencesOfRanks.add(sequenceOfRanks);
            }
        }

        return sequencesOfRanks;
    }


    /**
     * Returns an array of zeros and ones of size equal to the number of Ranks used
     * in the game. The zero/one in this array shows the absence/presence of a Rank
     * of the given suit in the player's handOfCards. For example:
     * return array:     [0, 1, 1, 1, 0, 0, 1, 1]
     * Rank.values():    [7, 8, 9, T, J, Q, K, A]
     * This means that there is one 8, 9, T, K, A cards of the suit given as parameter
     * in the list of cards given as parameter.
     *
     * @param handOfCards a list of cards in which to search for the frequency of cards
     * @param suit the suit of the cards to search for
     * @return an array of int filled with zeros and ones
     */
    private static int[] getRanksFrequencyArray(List<Card> handOfCards, Card.Suit suit) {
        int[] cardsFrequency = new int[8];

        List<Card.Rank> ranksOfTheGivenSuit = filterRanks(handOfCards, suit);

        int index = 0;
        for (Card.Rank rank : Card.Rank.values()) {
            if (ranksOfTheGivenSuit.contains(rank)) {
                cardsFrequency[index] = 1;
            } else {
                cardsFrequency[index] = 0;
            }
            index++;
        }

        return cardsFrequency;
    }


    /**
     * Returns all the ranks of the given suit present in the given list of cards
     *
     * @param listOfCards a list of cards
     * @param searchedSuit the Suit to search for
     * @return a list of Rank references
     */
    private static List<Card.Rank> filterRanks(List<Card> listOfCards, Card.Suit searchedSuit) {
        return listOfCards.stream()
                .filter(card -> card.getSuit() == searchedSuit)
                .map(Card::getRank)
                .collect(Collectors.toList());
    }

}
