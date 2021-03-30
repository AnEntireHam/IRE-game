package com.ire.entities.enemies;

import com.ire.entities.Enemy;
import com.ire.world.Item;

public class Caster extends Enemy {

    // TODO: Move addSpells over here. Consider making them randomly lose one "normal" attack.
    public Caster(int level) {
        super(level, 8, 2, 3, 6, 3,
                5, 3, 2, 1, 2,
                "Caster", "casterDeath",
                10, new Item("Sparkly Dust", "It's incredibly smooth.",
                        "There's a pile of shimmering dust."), 40, true);
    }
}
