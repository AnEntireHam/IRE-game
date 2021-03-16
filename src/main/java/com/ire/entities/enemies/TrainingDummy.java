package com.ire.entities.enemies;

import com.ire.entities.Enemy;

public class TrainingDummy extends Enemy {

    public TrainingDummy(int level) {

        //5, 7, 1, 2, 8
        super(level, 50, 5, 5, 5, 5,
                1, 1, 1, 1, 1,
                "TrainingDummy", "skeletonDeath",
                10, "Mysterious Bone", "It's a strange bone",
                "There's a strange bone laying here", 40, true, 0);
    }
}
