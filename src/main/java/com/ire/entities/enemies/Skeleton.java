package com.ire.entities.enemies;

public class Skeleton extends Enemy {

    public Skeleton(int level) {

        // 5(10?), 6, 1, 2, 6
        // 14, 6, 1, 2, 6
        // 10, 5, 1, 2, 5
        super(level, 14, 6, 1, 2, 6,
                5, 3, 2, 1, 2,
                "Skeleton", "skeletonDeath");
    }
}
