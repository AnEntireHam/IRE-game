package com.ire.entities.enemies;

import com.ire.world.Item;

public class Skeleton extends Enemy {

    public Skeleton(int level) {

        // 5(10?), 6, 1, 2, 6
        // 14, 6, 1, 2, 6
        // 10, 5, 1, 2, 5
        super(level, 14, 6, 1, 2, 6,
                5, 3, 2, 1, 2,
                "Skeleton", "skeletonDeath",
                10, new Item("Mysterious Bone", "It's a strange bone",
                "There's a strange bone laying here"), 40, true);
    }
}
