package com.danpopescu.belote.game;

import com.danpopescu.belote.deck.Deck;

import java.util.LinkedList;
import java.util.List;


public class Game {

    /**
     * Deck of cards used in the game
     */
    private Deck deck = new Deck();

    /**
     * List of players playing the current game
     */
    private List<Player> players = new LinkedList<>();

    /**
     * Creates a new instance of the game
     * @param playerNames a list of String representing the names of players
     */
    public Game(List<String> playerNames) {
        if (!(playerNames.size() >= 2 && playerNames.size() <= 4)) {
            throw new IllegalArgumentException("Number of players cannot be less than 2 or greater than 4.");
        }

        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }

        deck.populateDeck(players.size());
    }


    public void playGame() {
        GameRound gr = new GameRound(players, deck);
        gr.playRound();
        gr.playRound();
        gr.playRound();
    }









}
