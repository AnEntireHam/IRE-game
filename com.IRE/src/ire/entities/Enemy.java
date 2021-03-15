package ire.entities;

import ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import ire.tools.Tools;
import ire.world.Item;

import java.util.ArrayList;
import java.util.Random;

public abstract class Enemy extends Entity {

    // ***********************************
    // Fields
    // ***********************************

    protected int tenHlh, tenAtk, tenDef, tenMag, tenSpd;
    protected int rewardXp;
    protected String rewardName;
    protected String rewardUnequippedDescription;
    protected String rewardEquippedDescription;
    protected Item rewardItem;
    protected int rewardChance;
    protected boolean fleeable;
    protected int allegiance;

    // ***********************************
    // Constructors
    // ***********************************

    public Enemy(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                 int tenHlh, int tenAtk, int tenDef, int tenMag, int tenSpd,
                 String name, String deathSound,
                 int rewardXp, String rewardName, String rewardUnequippedDescription, String rewardEquippedDescription,
                 int rewardChance, boolean fleeable, int allegiance) {

        super(baseHlh, baseAtk, baseDef, baseMag, baseSpd, name, deathSound);

        this.tenHlh = tenHlh;
        this.tenAtk = tenAtk;
        this.tenDef = tenDef;
        this.tenMag = tenMag;
        this.tenSpd = tenSpd;
        this.rewardXp = rewardXp;
        this.rewardName = rewardName;
        this.rewardEquippedDescription = rewardEquippedDescription;
        this.rewardUnequippedDescription = rewardUnequippedDescription;
        this.rewardChance = rewardChance;
        this.fleeable = fleeable;
        this.allegiance = allegiance;

        this.rewardItem = new Item(rewardName, rewardUnequippedDescription, rewardEquippedDescription);

        levelUp(level);
    }

    // ***********************************
    // Level/Reward Methods
    // ***********************************

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
        //  bEffects.fullHeal();  Replace with appropriate method in Entity
    }

    public boolean calculateReward() {
        return Math.random() * 100 < this.rewardChance;
    }

    public String getRewardName() {
        return this.rewardName;
    }

    public Item giveReward() {
        return this.rewardItem;
    }


    // ***********************************
    // Prompt Methods
    // ***********************************

    @Override
    public void promptAttack(ArrayList<Entity> targets) {

        Random rand = new Random();
        int choice;
        boolean confirmed = false;

        do {
            choice = rand.nextInt(attacks.size());

            switch (attacks.get(choice)) {
                case "Stab" -> {
                    if (promptTargetIndex(targets)) {
                        this.setCurrentAction(this.stab);
                        confirmed = true;
                    }
                }
                case "Lunge" -> {
                    if (promptTargetIndex(targets)) {
                        this.setCurrentAction(this.lunge);
                        confirmed = true;
                    }
                }
                case "Cast" -> {
                    while (true) {

                        choice = this.spells.get(0).menu(spells, man, false);

                        if (choice == 0) {
                            break;

                        } else if (promptTargetIndex(targets)) {
                            this.setCurrentAction(spells.get(choice - 1));
                            confirmed = true;
                        }
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + attacks.get(choice));
            }
        } while (!confirmed);
    }

    @Override
    public void promptDefend() {

        Random rand = new Random();
        int choice;
        boolean confirmed = false;

        do {
            choice = rand.nextInt(defenses.size());

            switch (defenses.get(choice)) {
                case "Shield" -> {
                    this.setCurrentAction(this.shield);
                    confirmed = true;
                }
                case "Counter" -> {
                    this.setCurrentAction(this.counter);
                    confirmed = true;
                }
                case "Ward" -> {
                    choice = SpellDefense.menu(this.wards, false);
                    if (choice != 0) {
                        this.setCurrentAction(wards.get(choice - 1));
                        confirmed = true;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + attacks.get(choice));
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

    // ***********************************
    // promptX Methods
    // ***********************************

    /*protected int promptAttackChoice() {

        Random rand = new Random();
        int choice;

        choice = rand.nextInt(4) + 1;

        return choice;
    }

    protected int promptDefenseChoice() {

        Random rand = new Random();
        int choice;

        choice = rand.nextInt(4) + 1;

        return choice;
    }

    protected int promptSpellChoice() {
        return 0;
    }

    protected int promptWardChoice() {
        return 0;
    }

    protected int promptOffTechChoice() {
        return 0;
    }

    protected int promptDefTechChoice() {
        return 0;
    }*/


    // ***********************************
    // Accessor Methods
    // ***********************************

    public double getRewardXp() {
        return this.rewardXp;
    }

    public void setName(String name) {
        this.name = name;
    }
}
