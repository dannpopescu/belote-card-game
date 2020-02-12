package com.danpopescu;

import logic.Game;

import java.util.List;

public class Demo {

    public static void main(String[] args) {
        List<String> playerNames = List.of("Dan", "Igor", "Vadim", "Sandu");
        Game belote = new Game(playerNames);
        belote.startGame();

//        System.out.print('\u2664');
//        System.out.print('\u2661');
//        System.out.print('\u2667');
//        System.out.print('\u2662');
//
//        System.out.print('\u2660');
//        System.out.print('\u2665');
//        System.out.print('\u2663');
//        System.out.print('\u2666');
    }
}
