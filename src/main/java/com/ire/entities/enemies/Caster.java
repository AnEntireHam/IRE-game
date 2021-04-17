package com.ire.entities.enemies;

public class Caster extends Enemy {

    // TODO: Move addSpells over here. Consider making them randomly lose one "normal" attack.
    public Caster(int level) {
        super(level, 8, 2, 3, 6, 4,
                5, 3, 2, 1, 2,
                "Caster", "casterDeath");
    }
}
