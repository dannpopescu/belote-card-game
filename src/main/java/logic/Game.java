package logic;

import logic.deckofcards.Card;
import logic.deckofcards.Deck;
import logic.declarations.DeclarationsFinder;
import ui.CollectedCardsTableGenerator;
import ui.HandTableGenerator;

import java.util.*;

public class Game {

    private Deck deck = new Deck();

    private List<Player> players = new LinkedList<>();
    private int lastPlayerIndex;

    private Player leadsNextTrick;
    private Player pickedTrumpSuit;


    private static Scanner scanner = new Scanner(System.in);


    public Game(List<String> playerNames) {
        if (!(playerNames.size() >= 2 && playerNames.size() <= 4)) {
            throw new IllegalArgumentException("Number of players cannot be less than 2 or greater than 4.");
        }

        deck.populateDeck(players.size());

        this.lastPlayerIndex = playerNames.size() - 1;

        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
    }


    public void startGame() {
        playRound();
    }


    private void playRound() {
        resetDealer();
        System.out.println("The dealer is " + players.get(lastPlayerIndex));

        resetPlayerWhoPickedTrumpSuit();

        dealCards();

        printPlayersHands();

        pickTrumpSuit();
        dealRestOfCards();



        for (int i = 0; i < 8; i++) {
            printCollectedCards();
            printPlayersHands();
            printDeclarations();

            System.out.println(getPlayingOrder());
            playHand();
        }

         calculateScore();

        for (Player player : players) {
            System.out.println(player.getScore());
        }

        for (Player player : players) {
            player.discardHand();
        }
    }


    private void playHand() {
        List<Player> playingOrder = getPlayingOrder();

        Map<Card, Player> cardsPlayed = new HashMap<>();

        System.out.println("Please choose a card to play:");
        for (Player player : playingOrder) {
            System.out.print(player.getName() + ": ");
            int cardIndex = scanner.nextInt();

            System.out.println(player.getCard(cardIndex));
            scanner.nextLine();
            cardsPlayed.put(player.removeCard(cardIndex), player);
        }

        Card strongestCard = Collections.max(cardsPlayed.keySet());

        Player wonTheHand = cardsPlayed.get(strongestCard);

        wonTheHand.addWonTrick(cardsPlayed.keySet());

        leadsNextTrick = wonTheHand;
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
                    setTrumpSuitAndPicker(player, topCard.getSuit());
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


    private void dealRestOfCards() {
        for (Player player : players) {
            // the player who took the top card will have 6 cards, while the rest only 5
            int cardsToDeal = player.getHandSize() == 6 ? 2 : 3;
            for (int i = 0; i < cardsToDeal; i++) {
                player.addCard(deck.dealCard());
            }
        }
    }


//    private void setPlayingOrder() {
//        playingOrder = new LinkedList<>(playersInDealingOrder);
//        ListIterator<Player> playerIterator = playingOrder.listIterator(playingOrder.size());
//
//        while (true) {
//            if (playerIterator.hasPrevious()) {
//                Player playerToMove = playerIterator.previous();
//                playerIterator.remove();
//                playingOrder.add(0, playerToMove);
//                if (playerToMove == tookLastHand) {
//                    break;
//                }
//            }
//        }
//    }


    private List<Player> getPlayingOrder() {
        List<Player> dealingOrder = new LinkedList<>(players);

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
     * Dealer is the last player in the players list.
     * Moves the first player to the end and makes him the dealer of the game.
     */
    private void resetDealer() {
        if (!players.isEmpty()) {
            players.get(lastPlayerIndex).setDealer(false);
            players.add(players.remove(0));
            assert players.get(lastPlayerIndex) != null;
            players.get(lastPlayerIndex).setDealer(true);
        }
    }


    /**
     * Calculates the match points collected by every player.
     * EXCEPTION: the player who chose the trump suit (his score
     * is the residual up to 16). If his score is not the highest/equal to
     * the highest score, his match points go to the player with the highest
     * score and his BT counter increases by one.
     */
    private void calculateScore() {
        List<Player> playersSortedByMatchPoints = new ArrayList<>(players);
        playersSortedByMatchPoints.sort(Comparator.comparingInt(Player::getMatchPoints));

        int totalGameScoreWithoutTrumpSuitChooser = 0;
        for (Player player : players) {
            if (player != pickedTrumpSuit) {
                if (player.getMatchPoints() < 1) {
                    player.increaseScore(-10);
                    continue;
                }
                player.increaseScore();
                totalGameScoreWithoutTrumpSuitChooser += player.getMatchPoints();
            }
        }

        int trumpSuitChooserScore = 16 - totalGameScoreWithoutTrumpSuitChooser;
        Player playerWithHighestScore = Collections.max(playersSortedByMatchPoints);

        // TODO divide the match points of the trump suit chooser evenly among the players with a equal highest score
        if (trumpSuitChooserScore < playerWithHighestScore.getMatchPoints()) { // the player who picked trump doesn't have the highest score
            pickedTrumpSuit.incrementBT();
            playerWithHighestScore.increaseScore(trumpSuitChooserScore); // the player with the largest score get's the other's match points
        } else {
            pickedTrumpSuit.increaseScore(trumpSuitChooserScore);
        }
    }


    private void resetPlayerWhoPickedTrumpSuit() {
        pickedTrumpSuit = null;
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
