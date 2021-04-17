package com.ire.entities.enemies;

public class TrainingDummy extends Enemy {

    public TrainingDummy(int level) {
        //5, 7, 1, 2, 8
        super(level, 50, 5, 5, 5, 5,
                1, 1, 1, 1, 1,
                "TrainingDummy", "skeletonDeath");
    }
}
