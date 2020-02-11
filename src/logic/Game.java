package logic;

import ui.CollectedCardsTableGenerator;
import ui.HandTableGenerator;

import java.util.*;

public class Game {
    private int numberOfPlayers;
    private Deque<Player> playersInDealingOrder;
    private Deck deck;
    private Player playsFirstNextHand;

    private static Scanner scanner = new Scanner(System.in);

    public Game(List<String> playerNames) {
        this.numberOfPlayers = playerNames.size();
        this.deck = new Deck();
        this.playersInDealingOrder = new LinkedList<>();

        for (String playerName : playerNames) {
            playersInDealingOrder.add(new Player(playerName));
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
        System.out.println("The dealer is " + playersInDealingOrder.peekLast());

        resetOwnerOfTheGame();

        dealCards();

        printPlayersHands();

        chooseTrumpSuit();
        dealRestOfCards();



        for (int i = 0; i < 8; i++) {
            printCollectedCards();
            printPlayersHands();
            System.out.println(getPlayingOrder());
            playHand();
        }

        calculateScore();

        for (Player player : playersInDealingOrder) {
            System.out.println(player.getScore());
        }

        for (Player player : playersInDealingOrder) {
            player.getHand().discardHand();
        }
    }


    private void playHand() {
        List<Player> playingOrder = getPlayingOrder();

        Map<Card, Player> cardsPlayed = new HashMap<>();

        System.out.println("Please choose a card to play:");
        for (Player player : playingOrder) {
            System.out.print(player.getName() + ": ");
            int cardIndex = scanner.nextInt();

            System.out.println(player.getHand().getCard(cardIndex));
            scanner.nextLine();
            cardsPlayed.put(player.getHand().removeCard(cardIndex), player);
        }

        Card strongestCard = Collections.max(cardsPlayed.keySet());

        Player wonTheHand = cardsPlayed.get(strongestCard);

        wonTheHand.addCollectedCards(cardsPlayed.keySet());

        playsFirstNextHand = wonTheHand;
    }


    /**
     * Shuffles the deck and deals every player 5 cards in
     * two rounds (3 cards and 2 cards).
     */
    private void dealCards() {
        // TODO implement cutting before dealing the cards

        deck.restoreDeck();
        deck.shuffle();

        for (Player player : playersInDealingOrder) {
            for (int i = 0; i < 3; i++) {
                player.getHand().addCard(deck.dealCard());
            }
        }

        for (Player player : playersInDealingOrder) {
            for (int i = 0; i < 2; i++) {
                player.getHand().addCard(deck.dealCard());
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
    private void chooseTrumpSuit() {

        // first round of bidding: accepting/passing the top card
        Card topCard = deck.peekCard();
        Suit topCardSuit = topCard.getSuit(); // used in the second round to make sure the player doesn't chooses it

        // handles the "abizon" case: when the JACK is the top card, the next player MUST play in it
        if (topCard.getRank() == Rank.JACK) {
            playersInDealingOrder.peekFirst().setOwnsTheGame(true);
            playsFirstNextHand = playersInDealingOrder.peekFirst();
            deck.setTrumpSuit(topCard.getSuit());
            System.out.println("ABIZON");
            return;
        }

        System.out.println("Top card: " + topCard);
        System.out.println("[P]ass or [A]ccept?");

        for (Player player : playersInDealingOrder) {
            System.out.print(player.getName() + ": ");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("a")) {
                System.out.println(player.getName() + " accepted the card. The trump suit is " + topCard.suitToString().toUpperCase());
                player.setOwnsTheGame(true);
                playsFirstNextHand = player;
                player.getHand().addCard(deck.dealCard());
                deck.setTrumpSuit(topCardSuit);
                return;
            }
        }

        // the dealer receives the top card if no one takes it in the first round of bidding
        playersInDealingOrder.peekLast().getHand().addCard(deck.dealCard());

        // second round of bidding: choosing a trump suit or passing
        System.out.println("[P]ass or select a trump suite?");
        for (Player player : playersInDealingOrder) {
            System.out.print(player.getName() + ": ");
            String suitChoiceOrInvalid = scanner.nextLine().toLowerCase();

            for (Suit suit : Suit.VALUES) {
                if (suit.getName().equals(suitChoiceOrInvalid) // if the user entered a suit name
                        && suit != topCardSuit) { // it's not the top card's suit
                    player.setOwnsTheGame(true);
                    playsFirstNextHand = player;
                    deck.setTrumpSuit(suit);
                    return;
                }
            }
        }
    }


    private void dealRestOfCards() {
        for (Player player : playersInDealingOrder) {
            // the player who took the top card will have 6 cards, while the rest only 5
            int cardsToDeal = player.getHand().getNumberOfCards() == 6 ? 2 : 3;
            for (int i = 0; i < cardsToDeal; i++) {
                player.getHand().addCard(deck.dealCard());
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
        List<Player> dealingOrder = new LinkedList<>(playersInDealingOrder);

        if (playsFirstNextHand == dealingOrder.get(0)) {
            return dealingOrder;
        } else if (playsFirstNextHand == dealingOrder.get(1)) {
            dealingOrder.add(dealingOrder.remove(0));
        } else if (playsFirstNextHand == dealingOrder.get(2)) {
            dealingOrder.add(dealingOrder.remove(0));
            dealingOrder.add(dealingOrder.remove(0));
        } else if (playsFirstNextHand == dealingOrder.get(3)) {
            dealingOrder.add(0, dealingOrder.remove(3));
        }

        return dealingOrder;
    }


    /**
     * Adds 24 cards to the deck. If there are four players, adds
     * additionally the SEVEN's and EIGHT's.
     */
    private void populateDeck() {
        if (numberOfPlayers == 4) {
            Rank.setValuesForFourPlayers();
        }

        for (Rank r : Rank.VALUES) {
            for (Suit s : Suit.VALUES) {
                deck.addCard(new Card(s, r));
            }
        }
    }


    /**
     * Moves the first player to the end and makes him the dealer of the game.
     */
    private void resetDealer() {
        if (!playersInDealingOrder.isEmpty()) {
            playersInDealingOrder.peekLast().setDealer(false);
            playersInDealingOrder.addLast(playersInDealingOrder.removeFirst());
            assert playersInDealingOrder.peekLast() != null;
            playersInDealingOrder.peekLast().setDealer(true);
        }
    }


    private void calculateScore() {
        Map<Player, Integer> playersMatchPoints = new HashMap<>();

        for (Player player : playersInDealingOrder) {
            playersMatchPoints.put(player, player.getMatchPoints());
        }

        Player ownerOfTheGame = null;
        for (Player player : playersInDealingOrder) {
            if (player.ownsTheGame()) {
                ownerOfTheGame = player;
            }
        }

        int highestMatchPoints = Collections.max(playersMatchPoints.values());
        int ownerOfTheGameMatchPoints = playersMatchPoints.get(ownerOfTheGame);
        boolean bt = ownerOfTheGameMatchPoints < highestMatchPoints;

        if (bt) {
            ownerOfTheGame.addOneBT();
            playersMatchPoints.remove(ownerOfTheGame);
            // TODO improve the way match points are distributed in case of BT
            for (Player player : playersInDealingOrder) {
                if (playersMatchPoints.get(player) == highestMatchPoints) {
                    player.addToScore(ownerOfTheGame.getMatchPoints());
                    break;
                }
            }
        }

        for (Player player : playersInDealingOrder) {
            player.addToScore(player.getMatchPoints());
        }
    }



    private void resetOwnerOfTheGame() {
        for (Player player : playersInDealingOrder) {
            player.setOwnsTheGame(false);
        }
    }

    private void printPlayersHands() {
        HandTableGenerator.generateTable(playersInDealingOrder);
    }

    private void printCollectedCards() {
        CollectedCardsTableGenerator.generateTable(playersInDealingOrder);
    }

}
