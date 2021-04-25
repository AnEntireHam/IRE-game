package com.ire.entities.players;

public class Mage extends Player {

    public Mage(int level) {

        // 3 10 4 5 10 6
        // 2  9 3 4 8 5
        // 1  8 3 4 5 3
        super(level, 9, 4, 4, 8, 5,
                "Mage", "humanDeath",
                1, 0, 0, 2, 1);
    }
}
