package com.danpopescu.belote.game;

import com.danpopescu.belote.game.Game;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        List<String> playerNames = List.of("Dan", "Igor", "Vadim", "Sandu");
        Game belote = new Game(playerNames);
        belote.playGame();
    }
}
