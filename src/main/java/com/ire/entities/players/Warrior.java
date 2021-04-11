package com.ire.entities.players;

public class Warrior extends Player {

    public Warrior() {

        // 3  14 8 5 3 5
        // 2  11 6 4 3 5
        // 1  10 4 3 2 4
        // TODO: Players with default levels shouldn't prompt levelUp
        super(1, 14, 8, 5, 3, 5,
                "Warrior", "humanDeath",
                1, 2, 1, 0, 0);
    }
}
