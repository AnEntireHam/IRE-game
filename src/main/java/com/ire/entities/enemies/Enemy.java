package com.ire.entities.enemies;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;
import com.ire.world.Item;

public abstract class Enemy extends Entity {


    // Fields
    protected int tenHlh, tenAtk, tenDef, tenMag, tenSpd;
    /*protected Item rewardItem;
    protected int rewardChance;*/
    protected boolean fleeable;


    // Constructor

    public Enemy(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                 int tenHlh, int tenAtk, int tenDef, int tenMag, int tenSpd,
                 String name, String deathSound, int rewardXp, Item rewardItem, int rewardChance,
                 boolean fleeable) {

        super(level, baseHlh, baseAtk, baseDef, baseMag, baseSpd, name, deathSound, false);

        this.tenHlh = tenHlh;
        this.tenAtk = tenAtk;
        this.tenDef = tenDef;
        this.tenMag = tenMag;
        this.tenSpd = tenSpd;
        this.rewardXp = rewardXp;
        //  this.rewardItem = rewardItem;
        //  this.rewardChance = rewardChance;
        this.fleeable = fleeable;
    }


    // Level/Reward Methods

    // May want to revise into a switch-case statement.
    // TODO: This should be revised like player's levelUp()
    // TODO: Consider adding relevant level-up messages (imagine an NPC which
    @Override
    protected void levelUp(int targetLevel) {

        if (this.level == targetLevel) {
            return;
        }

        float tenTotal = tenHlh + tenAtk + tenDef + tenMag + tenSpd;

        while (this.level < targetLevel) {
            for (int j = 6; j > 0; j--) {
                double r = Math.random() * tenTotal;
                if (r < tenHlh) {
                    this.baseHlh++;
                } else if (r < tenHlh + tenAtk) {
                    this.baseAtk++;
                } else if (r < tenHlh + tenAtk + tenDef) {
                    this.baseDef++;
                } else if (r < tenHlh + tenAtk + tenDef + tenMag) {
                    this.baseMag++;
                } else {
                    this.baseSpd++;
                }
            }
            this.level++;
        }
        this.fullHeal(RemoveCondition.LEVEL_UP);
    }
}
