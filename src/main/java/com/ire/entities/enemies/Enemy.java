package com.ire.entities.enemies;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;

public abstract class Enemy extends Entity {


    // Fields
    protected int tenHlh, tenAtk, tenDef, tenMag, tenSpd;

    // Constructor

    public Enemy(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                 int hlhTendency, int atkTendency, int defTendency, int magTendency, int spdTendency,
                 String name, String deathSound) {
        super(1, baseHlh, baseAtk, baseDef, baseMag, baseSpd, name, deathSound, false);

        this.tenHlh = hlhTendency;
        this.tenAtk = atkTendency;
        this.tenDef = defTendency;
        this.tenMag = magTendency;
        this.tenSpd = spdTendency;
        this.levelUp(level);
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
                    this.stats.incrementBaseStat("hlh");
                } else if (r < tenHlh + tenAtk) {
                    this.stats.incrementBaseStat("atk");
                } else if (r < tenHlh + tenAtk + tenDef) {
                    this.stats.incrementBaseStat("def");
                } else if (r < tenHlh + tenAtk + tenDef + tenMag) {
                    this.stats.incrementBaseStat("mag");
                } else {
                    this.stats.incrementBaseStat("spd");
                }
            }
            this.level++;
        }
        this.fullHeal(RemoveCondition.LEVEL_UP);
    }
}
