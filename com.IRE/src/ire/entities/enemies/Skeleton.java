package ire.entities.enemies;

import ire.entities.Enemy;

public class Skeleton extends Enemy {

    public Skeleton(int level) {

        //5, 7, 1, 2, 8
        super(level, 10, 7, 1, 2, 1,
                5, 3, 2, 1, 2,
                "Skeleton", "skeletonDeath",
                10, "Mysterious Bone", "It's a strange bone",
                "There's a strange bone laying here", 40, true, 0);
    }
}
