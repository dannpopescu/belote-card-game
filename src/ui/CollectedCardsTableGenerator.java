package ui;

import logic.Player;

import java.util.List;

public class CollectedCardsTableGenerator {

    public static void generateTable(List<Player> players) {
        System.out.println("+--------------------------------------------------------------------------------+");

        for (Player player : players) {
            System.out.print("|");
            String rowContent = " " + player.getName() + ": " + player.getWonTricks();
            System.out.print(rowContent);

            int whiteTrailingSpaces = 80 - rowContent.length();

            for (int i = 0; i < whiteTrailingSpaces; i++) {
                System.out.print(" ");
            }

            System.out.println("|");
        }

        System.out.println("+--------------------------------------------------------------------------------+");
    }
}
