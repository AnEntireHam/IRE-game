package com.ire.entities;

import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import com.ire.tools.Tools;
import com.ire.world.Item;

import java.util.ArrayList;
import java.util.Random;

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

        super(level, baseHlh, baseAtk, baseDef, baseMag, baseSpd, name, deathSound);

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
    @Override
    protected void levelUp(int targetLevel) {

        float tenTotal = tenHlh + tenAtk + tenDef + tenMag + tenSpd;

        for (; this.level < targetLevel; this.level++) {
            for (int j = 5; j > 0; j--) {
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
        }
        // TODO: Re-implement fullHeal() in Entity
    }


    // Prompt Methods

    @Override
    public void promptAttack(ArrayList<Entity> targets) {

        Random rand = new Random();
        int choice;
        boolean confirmed = false;

        do {
            choice = rand.nextInt(attacks.size()) + 1;

            switch (attacks.get(choice - 1)) {
                case "Stab":
                    if (promptTargetIndex(targets)) {
                        this.setCurAction(this.stab);
                        confirmed = true;
                    }
                    break;
                case "Lunge":
                    if (promptTargetIndex(targets)) {
                        this.setCurAction(this.lunge);
                        confirmed = true;
                    }
                    break;
                case "Cast":
                    while (true) {
                        choice = SpellAttack.menu(spells, man, this.getCurMag(), false);

                        if (choice == 0) {
                            break;

                        } else if (promptTargetIndex(targets)) {
                            this.setCurAction(spells.get(choice - 1));
                            confirmed = true;
                            break;
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + attacks.get(choice));
            }
        } while (!confirmed);
    }

    @Override
    public void promptDefend() {

        Random rand = new Random();
        int choice;
        boolean confirmed = false;

        do {
            choice = rand.nextInt(defenses.size()) + 1;

            switch (defenses.get(choice - 1)) {
                case "Shield":
                    this.setCurAction(this.shield);
                    confirmed = true;
                    break;
                case "Counter":
                    this.setCurAction(this.counter);
                    confirmed = true;
                    break;
                case "Ward":
                    choice = SpellDefense.menu(this.wards, false);
                    if (choice != 0) {
                        this.setCurAction(wards.get(choice - 1));
                        confirmed = true;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + attacks.get(choice));
            }
        } while (!confirmed);
    }

    @Override
    protected boolean promptTargetIndex(ArrayList<Entity> targets) {

        ArrayList<String> options = new ArrayList<>();
        Random rand = new Random();
        int choice;

        Tools.sortEntityList(targets);

        for (Entity e: targets) {
            if (e.isAlive()) {
                options.add(e.getName());
            }
        }

        choice = rand.nextInt(options.size());

        // Hopefully this doesn't cause an error.

        this.targetIndex = choice;
        return true;
    }


    // Accessor Methods

    //  Revise these methods later, when inventories in general are better understood.
    /*public boolean calculateReward() {
        return Math.random() * 100 < this.rewardChance;
    }

    public Item getReward() {
        return this.rewardItem;
    }*/

}
