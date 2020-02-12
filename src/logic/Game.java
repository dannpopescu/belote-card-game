package logic;

import ui.CollectedCardsTableGenerator;
import ui.HandTableGenerator;

import java.util.*;

public class Game {
    private int playersLastIndex;
    private List<Player> players;
    private Deck deck;
    private Player leadsNextTrick;
    private Player pickedTrumpSuit;


    private static Scanner scanner = new Scanner(System.in);

    public Game(List<String> playerNames) {
        this.playersLastIndex = playerNames.size() - 1;
        this.deck = new Deck();
        this.players = new LinkedList<>();

        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
    }


    public void startGame() {
        populateDeck();
        playRound();
        System.out.println("==============================================");
//        playRound();
    }


    private void playRound() {
        resetDealer(); // dealer is the last player in the players deque
        System.out.println("The dealer is " + players.get(playersLastIndex));

        resetOwnerOfTheGame();

        dealCards();

        printPlayersHands();

        pickTrumpSuit();
        dealRestOfCards();



        for (int i = 0; i < 8; i++) {
            printCollectedCards();
            printPlayersHands();
            for (Player player : players) {
                player.checkDeclarations();
            }
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
     * In the first, the top card is switched face up and the
     * players choose if they want to receive that card and set its suit as trump suit.
     * If nobody wants the top card, in the second round each player
     * takes turn to say a custom trump suit or pass.
     */
    private void pickTrumpSuit() {

        // first round of bidding: accepting/passing the top card
        Card topCard = deck.peekCard();
        Suit topCardSuit = topCard.getSuit(); // used in the second round to make sure the player doesn't chooses it

        // handles the "abizon" case: when the JACK is the top card, the next player MUST play in it
        if (topCard.getRank() == Rank.JACK) {
            pickedTrumpSuit = players.get(0);
            leadsNextTrick = players.get(0);
            deck.setTrumpSuit(topCard.getSuit());
            System.out.println("ABIZON");
            return;
        }

        System.out.println("Top card: " + topCard);
        System.out.println("[P]ass or [A]ccept?");

        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("a")) {
                System.out.println(player.getName() + " accepted the card. The trump suit is " + topCard.suitToString().toUpperCase());
                pickedTrumpSuit = player;
                leadsNextTrick = player;
                player.addCard(deck.dealCard());
                deck.setTrumpSuit(topCardSuit);
                return;
            }
        }

        // the dealer receives the top card if no one takes it in the first round of bidding
        players.get(playersLastIndex).addCard(deck.dealCard());

        // second round of bidding: choosing a trump suit or passing
        System.out.println("[P]ass or select a trump suite?");
        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            String suitChoiceOrInvalid = scanner.nextLine().toLowerCase();

            for (Suit suit : Suit.values()) {
                if (suit.name().toLowerCase().equals(suitChoiceOrInvalid) // if the user entered a suit name
                        && suit != topCardSuit) { // it's not the top card's suit
                    pickedTrumpSuit = player;
                    leadsNextTrick = player;
                    deck.setTrumpSuit(suit);
                    return;
                }
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
            player.sortCards();
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
     * Adds 24 cards to the deck. If there are four players, adds
     * additionally the SEVEN's and EIGHT's.
     */
    private void populateDeck() {
        EnumSet<Rank> rankRange;
        if (players.size() == 4) {
            rankRange = EnumSet.range(Rank.SEVEN, Rank.ACE);
        } else {
            rankRange = EnumSet.range(Rank.NINE, Rank.ACE);
        }

        for (Rank r : rankRange) {
            for (Suit s : Suit.values()) {
                deck.addCard(new Card(s, r));
            }
        }
    }


    /**
     * Moves the first player to the end and makes him the dealer of the game.
     */
    private void resetDealer() {
        if (!players.isEmpty()) {
            players.get(playersLastIndex).setDealer(false);
            players.add(players.remove(0));
            assert players.get(playersLastIndex) != null;
            players.get(playersLastIndex).setDealer(true);
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


    private void resetOwnerOfTheGame() {
        pickedTrumpSuit = null;
    }

    private void printPlayersHands() {
        HandTableGenerator.generateTable(players);
    }

    private void printCollectedCards() {
        CollectedCardsTableGenerator.generateTable(players);
    }

}
