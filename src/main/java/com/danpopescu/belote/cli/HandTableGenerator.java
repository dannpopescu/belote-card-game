package com.danpopescu.belote.cli;

import com.danpopescu.belote.game.Player;

import java.util.List;

public class HandTableGenerator {

    public static void generateTable(List<Player> players) {
        generateRowDelimiter(players.size());

        for (Player player : players) {
            generateHeadCellContent(player.getName());
        }
        System.out.println("|");

        generateRowDelimiter(players.size());

        for (Player player : players) {
            generatePlayerCardsCell(player);
        }
        System.out.println("|");

        for (Player player : players) {
            generateCardsIndexesCell(player);
        }
        System.out.println("|");

        generateRowDelimiter(players.size());
    }

    private static void generateHeadCellContent(String playerName) {
        System.out.print("|");

        int numberOfWhiteSpaces = (40 - playerName.length()) / 2;

        for (int i = 0; i < numberOfWhiteSpaces; i++) {
            System.out.print(" ");
        }

        System.out.print(playerName);
        if (playerName.length() % 2 == 1) {
            System.out.print(" ");
        }

        for (int i = 0; i < numberOfWhiteSpaces; i++) {
            System.out.print(" ");
        }

    }

    private static void generateRowDelimiter(int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            System.out.print("+----------------------------------------");
        }
        System.out.print("+\n");
    }

    private static void generatePlayerCardsCell(Player player) {
        System.out.print("| ");
        String cards = player.handToStringForTable();
        String coloredCards = player.handToStringForTableColored();

        System.out.print(coloredCards);

        int numberOfTrailingWhiteSpaces = 40 - cards.length() - 1;

        for (int i = 0; i < numberOfTrailingWhiteSpaces; i++) {
            System.out.print(" ");
        }
    }

    private static void generateCardsIndexesCell(Player player) {
        System.out.print("| ");

        for (int i = 0; i < player.getHandSize(); i++) {
            System.out.print(i + "   ");
        }

        int numberOfTrailingWhiteSpaces = 40 - player.handToStringForTable().length() - 1;

        for (int i = 0; i < numberOfTrailingWhiteSpaces; i++) {
            System.out.print(" ");
        }
    }



}
