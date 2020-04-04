package com.danpopescu.belote.game;

import com.danpopescu.belote.cli.CollectedCardsTableGenerator;
import com.danpopescu.belote.cli.ConsoleColors;
import com.danpopescu.belote.cli.HandTableGenerator;
import com.danpopescu.belote.deck.Card;
import com.danpopescu.belote.deck.Deck;
import com.danpopescu.belote.declarations.DeclarationsFinder;
import com.danpopescu.belote.declarations.PlayerComparatorByDeclarations;

import java.util.*;

public class GameRound {

    /**
     * Deck of cards used in the game
     */
    private Deck deck;

    /**
     * List of players playing the current game
     */
    private List<Player> players;

    /**
     * Index of the last player in the list of players.
     * It's also the index of the game's dealer.
     */
    private int lastPlayerIndex;

    /**
     * Player who won the last trick and leads the next.
     */
    private Player nextTrickLeader;

    /**
     * Player who picked the trump suit of the current round.
     */
    private Player trumpSuitPicker;


    private static Scanner scanner = new Scanner(System.in);


    public GameRound(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;

        this.lastPlayerIndex = players.size() - 1;
    }

    /**
     * Play a game round.
     * A game round starts with dealing the cards, picking the trump suit,
     * playing all the cards in the hand and calculating the round score.
     */
    public void playRound() {
        nextDealer();
        trumpSuitPicker = null;
        dealCards();

        System.out.println(ConsoleColors.RED + "The dealer is " + players.get(lastPlayerIndex) + ConsoleColors.RESET);
        printPlayersHands();

        pickTrumpSuit();
        dealRestOfCards();

        printDeclarations();
        compareDeclarations();

        // play 8 hands
        for (int i = 0; i < 8; i++) {
            printCollectedCards();
            printPlayersHands();

            System.out.println(getPlayingOrder());
            playTrick();
        }

        calculateGameRoundScore();

        for (Player player : players) {
            System.out.println(player.getScore());
        }

        for (Player player : players) {
            player.clearVariablesAfterGameRound();
        }
    }


    /**
     * Update the dealer.
     * Note: dealer is the last player in the players list
     */
    private void nextDealer() {
        // set last dealer to false
        players.get(lastPlayerIndex).setDealer(false);

        // move the new dealer to the end
        Player newDealer = players.remove(0);
        newDealer.setDealer(true);
        players.add(newDealer);
    }


    /**
     * Shuffle the deck and deal every player 3 + 2 cards
     */
    private void dealCards() {
        // TODO implement cutting before dealing the cards

        deck.restoreDeck();
        deck.shuffle();

        for (Player player : players) {
            for (int i = 0; i < 3; i++) {
                player.addCard(deck.dealCard());
            }
        }

        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                player.addCard(deck.dealCard());
            }
        }
    }



    private void dealRestOfCards() {
        for (Player player : players) {
            // the player who took the top card will have 6 cards, while the rest only 5
            int cardsToDeal = player.getHandSize() == 6 ? 2 : 3;
            for (int i = 0; i < cardsToDeal; i++) {
                player.addCard(deck.dealCard());
            }
        }
    }


    /**
     * Prompt the players to choose the trump suit
     *
     * Bidding is done in two rounds:
     *   1. the top card in the deck is turned face up and every player decides if he wants
     *      to receive the card and set its suit as trump suit
     *   2. if the first round is unsuccessful, every player decides if he wants to choose
     *      a custom suit or to pass
     */
    private void pickTrumpSuit() {
        Card topCard = deck.peekCard();

        boolean firstRoundSuccessful = bidFirstRound(topCard);

        if (!firstRoundSuccessful) {
            bidSecondRound(topCard);
        }
    }


    /**
     * Handle the first round of bidding for a trump suit
     *
     * Note: Every player decides, in clock-wise order from the dealer, if he wants
     *       to receive the top card and set its suit as trump suit
     *
     * @param topCard the card faced up
     * @return true if the bidding round ended successfully
     */
    private boolean bidFirstRound(Card topCard) {
        // handle ABIZON case
        if (topCard.getRank() == Card.Rank.JACK) {
            setTrumpSuitAndPicker(players.get(0), topCard.getSuit());
            System.out.println("ABIZON");
            return true;
        }

        System.out.println("Top card: " + topCard);
        System.out.println("[P]ass or [A]ccept?");

        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            String action = scanner.nextLine();
            if (action.charAt(0) == 'a') {
                System.out.println(player.getName() + " accepted the card. The trump suit is " + topCard.getSuit().name());
                setTrumpSuitAndPicker(player, topCard.getSuit());
                player.addCard(deck.dealCard());
                return true;
            }
        }

        return false;
    }


    /**
     * Handle the second round of bidding for a trump suit
     *
     * Note: Every player decides if he wants to choose a custom suit or to pass
     *
     * @param topCard the card faced up used in the first round
     */
    private void bidSecondRound(Card topCard) {
        // the dealer receives the top card because no one has taken it in the first round of bidding
        players.get(lastPlayerIndex).addCard(deck.dealCard());

        System.out.println("[P]ass or select a trump suite?");
        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            String suitChoiceOrInvalid = scanner.nextLine().toLowerCase();

            for (Card.Suit suit : Card.Suit.values()) {
                if (suit.name().equalsIgnoreCase(suitChoiceOrInvalid) // if the user entered a valid suit name
                        && suit != topCard.getSuit()) { // and it's not the top card's suit
                    setTrumpSuitAndPicker(player, suit);
                    return;
                }
            }
        }
    }


    /**
     * Set the trump suit of the game. The player who picked it leads the next trick.
     *
     * @param player who picked the trump suit
     * @param suit the trump suit of the game
     */
    private void setTrumpSuitAndPicker(Player player, Card.Suit suit) {
        trumpSuitPicker = player;
        nextTrickLeader = player;
        deck.setTrumpSuit(suit);
    }


    /**
     * Set the playing order of the next trick.
     *
     * Note: The player who leads the next trick is the first in
     *       the order, and the rest are ordered clockwise.
     *
     * @return a list of Player where the first player is the player
     * who leads the trick
     */
    private List<Player> getPlayingOrder() {
        List<Player> dealingOrder = new LinkedList<>(players);

        // TODO fix for 2 and 3 players
        if (nextTrickLeader == dealingOrder.get(0)) {
            return dealingOrder;
        } else if (nextTrickLeader == dealingOrder.get(1)) {
            dealingOrder.add(dealingOrder.remove(0));
        } else if (nextTrickLeader == dealingOrder.get(2)) {
            dealingOrder.add(dealingOrder.remove(0));
            dealingOrder.add(dealingOrder.remove(0));
        } else if (nextTrickLeader == dealingOrder.get(3)) {
            dealingOrder.add(0, dealingOrder.remove(3));
        }

        return dealingOrder;
    }



    /**
     * Play a trick
     *
     * Note: Player who leads the trick picks a card.
     *       Every player should follow suit or use a trump card.
     */
    private void playTrick() {
        List<Player> playingOrder = getPlayingOrder();

        Map<Card, Player> cards = new HashMap<>();  // cards on the table

        System.out.println("Please choose a card to play:");
        for (Player player : playingOrder) {
            System.out.print(player.getName() + ": ");
            int cardIndex = Integer.parseInt(scanner.nextLine());
            System.out.println(player.getCard(cardIndex));

            // remove card from the hand and put it on table
            cards.put(player.removeCard(cardIndex), player);
        }

        Card strongestCard = Collections.max(cards.keySet());
        Player trickWinner = cards.get(strongestCard);
        trickWinner.addWonTrick(cards.keySet());

        nextTrickLeader = trickWinner;
    }


    private void compareDeclarations() {
        // TODO review the method and write javadoc

        int nullDeclarationsCounter = 0;

        for (Player player : players) {
            if (player.getDeclarations() == null) {
                nullDeclarationsCounter++;
            }
        }

        if (nullDeclarationsCounter == players.size()) {
            System.out.println("No declarations in this game.");
            return;
        }

        Player strongestPlayerByDeclarations = Collections.max(players, new PlayerComparatorByDeclarations());

        for (Player player : players) {
            if (player != strongestPlayerByDeclarations) {
                player.setDeclarationsToInvalid();
            }
        }

        System.out.println("Best player: " + strongestPlayerByDeclarations.getName()
                + "\nDeclarations: " + strongestPlayerByDeclarations.getDeclarations()
        + "\nPlayer points: " + strongestPlayerByDeclarations.getDeclarationsMatchPoints());
    }




    /**
     * Calculate each player's match points
     *
     * NOTE: the score of the player who have chosen the trump suit
     *       is the residual up to 16. If his score is not the/equal to
     *       the highest score, his match points go to the player with the
     *       highest score and his BT counter increments.
     */
    private void calculateGameRoundScore() {
        int gameScore = 16;  // base round score
        for (Player player : players) {  // increase if players have valid declarations
            gameScore += player.getDeclarationsMatchPoints();
        }

        List<Player> playersSortedByMatchPoints = new ArrayList<>(players);
        playersSortedByMatchPoints.sort(Comparator.comparingInt(Player::getMatchPoints));

        // TODO Refactor and change the .increaseScore method
        int totalGameScoreWithoutTrumpSuitPicker = 0;
        for (Player player : players) {
            if (player != trumpSuitPicker) {
                if (player.getMatchPoints() == 0) {
                    player.increaseScore(-10);
                    continue;
                }
                player.increaseScore();

                totalGameScoreWithoutTrumpSuitPicker += player.getMatchPoints();
            }
        }

        int trumpSuitChooserScore = gameScore - totalGameScoreWithoutTrumpSuitPicker;
        Player playerWithHighestScore = Collections.max(playersSortedByMatchPoints);

        // TODO divide the match points of the trump suit chooser evenly among the players with a equal highest score
        if (trumpSuitChooserScore < playerWithHighestScore.getMatchPoints()) { // the player who picked trump doesn't have the highest score
            trumpSuitPicker.incrementBT();
            playerWithHighestScore.increaseScore(trumpSuitChooserScore); // the player with the largest score get's the other's match points
        } else {
            trumpSuitPicker.increaseScore(trumpSuitChooserScore);
        }
    }


    private void clearGameRoundVariables() {
        for (Player player : players) {
            player.clearVariablesAfterGameRound();
        }
    }



    private void printPlayersHands() {
        HandTableGenerator.generateTable(players);
    }

    private void printCollectedCards() {
        CollectedCardsTableGenerator.generateTable(players);
    }

    private void printDeclarations() {
        for (Player player : players) {
            System.out.println(player.getName());
            System.out.println(DeclarationsFinder.getDeclarations(player.getHand()));
        }
    }
}
