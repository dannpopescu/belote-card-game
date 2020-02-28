package com.danpopescu.belote.game;

import com.danpopescu.belote.deck.Card;
import com.danpopescu.belote.deck.Deck;
import com.danpopescu.belote.declarations.DeclarationsFinder;
import com.danpopescu.belote.declarations.PlayerComparatorByDeclarations;
import com.danpopescu.belote.cli.CollectedCardsTableGenerator;
import com.danpopescu.belote.cli.ConsoleColors;
import com.danpopescu.belote.cli.HandTableGenerator;

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
     * Additional Match Points given by players' declarations
     */
    private Map<Player, Integer> playerDeclarationMatchPoints = new HashMap<>();

    /**
     * Index of the last player in the list of players.
     * It's also the index of the game's dealer.
     */
    private int lastPlayerIndex;

    /**
     * Player who won the last trick and leads the next.
     */
    private Player leadsNextTrick;

    /**
     * Player who picked the trump suit of the current round.
     */
    private Player pickedTrumpSuit;

    /**
     * Number of match points of the current game round.
     */
    private int gameRoundMatchPoints;


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
        pickedTrumpSuit = null;
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
            playHand();
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
     * Dealer is the last player in the players list.
     * Moves the first player to the end and makes him the dealer of the game.
     */
    private void nextDealer() {
        if (!players.isEmpty()) {
            players.get(lastPlayerIndex).setDealer(false);
            players.add(players.remove(0));
            assert players.get(lastPlayerIndex) != null;
            players.get(lastPlayerIndex).setDealer(true);
        }
    }


    /**
     * Shuffles the deck and deals every player 5 cards in
     * two rounds (3 cards and 2 cards).
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
     * Prompts the players to choose the game's trump suit. Bidding is done in two rounds.
     * In the first, the top card is turned face up and the
     * players choose if they want to receive that card and set its suit as trump suit.
     * If nobody wants the top card, in the second round each player
     * takes turn to say a custom trump suit or pass.
     */
    private void pickTrumpSuit() {
        Card topCard = deck.peekCard();

        boolean firstRoundSuccessful = bidFirstRound(topCard);

        if (!firstRoundSuccessful) {
            bidSecondRound(topCard);
        }
    }


    /**
     * Handle the first round of bidding for a trump suit.
     * The top card is turned face up and every player in clock-wise
     * order from dealer has a chance to play in that card or to skip.
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
            if (action.equalsIgnoreCase("a")) {
                System.out.println(player.getName() + " accepted the card. The trump suit is " + topCard.getSuit().name());
                setTrumpSuitAndPicker(player, topCard.getSuit());
                player.addCard(deck.dealCard());
                return true;
            }
        }

        return false;
    }


    /**
     * Handle the second round of bidding if the first was unsuccessful.
     * Every player has a chance to choose the trump suit or to skip.
     * @param topCard used in the first round
     */
    private void bidSecondRound(Card topCard) {
        // the dealer receives the top card because no one have taken it in the first round of bidding
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
     * Set the trump suit of the game. The player who picked it is remembered
     * for later use and he leads the next trick.
     * @param player who picked the trump suit
     * @param suit the trump suit of the game
     */
    private void setTrumpSuitAndPicker(Player player, Card.Suit suit) {
        pickedTrumpSuit = player;
        leadsNextTrick = player;
        deck.setTrumpSuit(suit);
    }


    /**
     * Set the playing order of the next trick.
     * The player who leads the next trick is the first in
     * the order, and the rest are ordered clockwise.
     * @return a list of Player where the first player is the player
     * who leads the trick
     */
    private List<Player> getPlayingOrder() {
        List<Player> dealingOrder = new LinkedList<>(players);

        // TODO fix for 2 and 3 players
        if (leadsNextTrick == dealingOrder.get(0)) {
            return dealingOrder;
        } else if (leadsNextTrick == dealingOrder.get(1)) {
            dealingOrder.add(dealingOrder.remove(0));
        } else if (leadsNextTrick == dealingOrder.get(2)) {
            dealingOrder.add(dealingOrder.remove(0));
            dealingOrder.add(dealingOrder.remove(0));
        } else if (leadsNextTrick == dealingOrder.get(3)) {
            dealingOrder.add(0, dealingOrder.remove(3));
        }

        return dealingOrder;
    }



    /**
     * Play a hand. Player who leads the trick picks a card.
     * Every player should follow suit or use a trump card.
     */
    private void playHand() {
        List<Player> playingOrder = getPlayingOrder();

        Map<Card, Player> cardsPlayed = new HashMap<>();

        System.out.println("Please choose a card to play:");
        for (Player player : playingOrder) {
            System.out.print(player.getName() + ": ");
            int cardIndex = Integer.parseInt(scanner.nextLine());
            System.out.println(player.getCard(cardIndex));

            cardsPlayed.put(player.removeCard(cardIndex), player);
        }

        Card strongestCard = Collections.max(cardsPlayed.keySet());

        Player wonTheTrick = cardsPlayed.get(strongestCard);

        wonTheTrick.addWonTrick(cardsPlayed.keySet());

        leadsNextTrick = wonTheTrick;
    }


    private void compareDeclarations() {

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
     * Calculates the match points collected by every player.
     * EXCEPTION: the player who chose the trump suit (his score
     * is the residual up to 16). If his score is not the highest/equal to
     * the highest score, his match points go to the player with the highest
     * score and his BT counter increases by one.
     */
    private void calculateGameRoundScore() {
        int gameScore = 16;
        for (Player player : players) {
            gameScore += player.getDeclarationsMatchPoints();
        }

        List<Player> playersSortedByMatchPoints = new ArrayList<>(players);
        playersSortedByMatchPoints.sort(Comparator.comparingInt(Player::getMatchPoints));

        int totalGameScoreWithoutTrumpSuitPicker = 0;
        for (Player player : players) {
            if (player != pickedTrumpSuit) {
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
            pickedTrumpSuit.incrementBT();
            playerWithHighestScore.increaseScore(trumpSuitChooserScore); // the player with the largest score get's the other's match points
        } else {
            pickedTrumpSuit.increaseScore(trumpSuitChooserScore);
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
