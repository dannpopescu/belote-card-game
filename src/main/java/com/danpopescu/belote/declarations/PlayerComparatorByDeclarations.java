package com.danpopescu.belote.declarations;

import com.danpopescu.belote.game.Player;

import java.util.Comparator;

public class PlayerComparatorByDeclarations implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getDeclarations() == null && p2.getDeclarations() == null) {
            return 0;
        } else if (p1.getDeclarations() == null && p2.getDeclarations() != null) {
            return -1;
        } else if (p1.getDeclarations() != null & p2.getDeclarations() == null) {
            return 1;
        } else {
            return p1.getHighestDeclaration().compareTo(p2.getHighestDeclaration());
        }
    }
}
