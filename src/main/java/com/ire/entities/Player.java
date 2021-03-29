package com.ire.entities;

import com.ire.audio.AudioStream;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import com.ire.tools.Tools;
import com.ire.world.Inventory;

import java.util.ArrayList;

public class Player extends Entity {

    // ***********************************
    // Fields
    // ***********************************

    protected int hlhAllocation, atkAllocation, defAllocation, magAllocation, spdAllocation;
    protected Inventory playerInventory;

    protected static AudioStream letsGo = new AudioStream("letsGo");


    // ***********************************
    // Constructor
    // ***********************************

    public Player(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                  String name, String deathSound,
                  int hlhAllocation, int atkAllocation, int defAllocation, int magAllocation, int spdAllocation) {

        super(baseHlh, baseAtk, baseDef, baseMag, baseSpd,
                name,deathSound);

        this.level = level;
        this.hlhAllocation = hlhAllocation;
        this.atkAllocation = atkAllocation;
        this.defAllocation = defAllocation;
        this.magAllocation = magAllocation;
        this.spdAllocation = spdAllocation;
        this.playerInventory = new Inventory();

    }


    // Level Functions

    // This makes the presumption that weapons/armor won't change baseStats, or that seeing modified values is okay.
    @Override
    protected void levelUp(int targetLevel) {

        Tools.clear();

        int[] previousBaseStats = {this.baseHlh, this.baseAtk, this.baseDef, this.baseMag, this.baseSpd};

        addAllocations(targetLevel);
        int bonusPoints = calculateBonusPoints(targetLevel);

        String[] statPrefixes = {"[1] Hlh ", "[2] Atk ", "[3] Def ", "[4] Mag ", "[5] Spd "};
        String message = (this.name + "'s stats have increased!");
        letsGo.play();

        // Weird version of Tools' menu. May be able to consolidate later.
        while (bonusPoints > 0) {

            printStatChanges(previousBaseStats, statPrefixes, targetLevel, message);

            if (bonusPoints > 1) {
                System.out.println(this.name + " has " + bonusPoints +
                        " bonus points remaining. Input 1-5 to invest in a stat.");
            } else {
                System.out.println(this.name + " has " + bonusPoints +
                        " bonus point remaining. Input 1-5 to invest in a stat.");
            }

            bonusPoints--;
            message = handleAllocationChoice();
        }

        printStatChanges(previousBaseStats, statPrefixes, targetLevel, message);

        Tools.emptyPrompt();
        Tools.clear();
    }

    private void addAllocations(int targetLevel) {

        int differenceLevels = targetLevel - this.level;

        this.baseHlh += hlhAllocation * differenceLevels;
        this.baseAtk += atkAllocation * differenceLevels;
        this.baseDef += defAllocation * differenceLevels;
        this.baseMag += magAllocation * differenceLevels;
        this.baseSpd += spdAllocation * differenceLevels;
    }

    private int calculateBonusPoints(int targetLevel) {
        int bonusPoints = 0;
        while (this.level < targetLevel) {

            bonusPoints += 2;
            bonusPoints += level / 10;
            this.level++;
        }
        return bonusPoints;
    }

    private void printStatChanges(int[] previousBaseStats, String[] statPrefixes, int targetLevel, String message) {

        String[] statDisplay = new String[5];

        for (int i = 0; i < 5; i++) {

            statDisplay[i] = statPrefixes[i];
            statDisplay[i] += previousBaseStats[i];

            if (this.getStat(i) != previousBaseStats[i]) {

                previousBaseStats[i] = this.getStat(i);
                statDisplay[i] += (" -> " + this.getStat(i));
            }
        }

        System.out.println(name + " leveled up to level " + targetLevel + "!\n");

        for (String s : statDisplay) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println(message);
    }

    private String handleAllocationChoice() {
        String message;
        int choice = Tools.getUserInt(1, 5);
        String statName = "";

        switch (choice) {
            case 1:
                this.baseHlh++;
                statName = "health";
                break;
            case 2:
                this.baseAtk++;
                statName = "attack";
                break;
            case 3:
                this.baseDef++;
                statName = "defense";
                break;
            case 4:
                this.baseMag++;
                statName = "magic";
                break;
            case 5:
                this.baseSpd++;
                statName = "speed";
                break;
        }
        message = this.name + " added 1 point to " + statName + ".";
        return message;
    }

    // Prompt Functions

    @Override
    public void promptAttack(ArrayList<Entity> targets) {

        ArrayList<String> options = new ArrayList<>();
        int choice;
        boolean confirmed = false;

        do {
            Tools.clear();
            options.clear();
            options.addAll(attacks);
            System.out.println("Enemies are defending. Select an action.");
            choice = Tools.menu(options);

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
                        choice = SpellAttack.menu(spells, man, this.getCurMag(), true);

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

        ArrayList<String> options = new ArrayList<>();
        int choice;
        boolean confirmed = false;

        do {
            Tools.clear();
            options.clear();
            options.addAll(defenses);
            System.out.println("Enemies are attacking. Select an action.");
            choice = Tools.menu(options);

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
                    choice = SpellDefense.menu(this.wards, true);
                    if (choice != 0) {
                        this.setCurAction(wards.get(choice - 1));
                        confirmed = true;
                    }
                    break;
            }
        } while (!confirmed);
    }

    @Override
    protected boolean promptTargetIndex(ArrayList<Entity> targets) {

        ArrayList<String> options = new ArrayList<>();
        ArrayList<Integer> exclusions = new ArrayList<>();
        int choice;

        Tools.sortEntityList(targets);

        for (Entity t : targets) {
            if (t.isAlive()) {
                options.add(t.generateBattleStatus(true));
            }
        }

        System.out.println("Select a target.");
        choice = Tools.cancelableMenu((options), exclusions) - 1;

        if (choice != -1) {
            this.targetIndex = choice;
            return true;
        }
        return false;
    }

    /*for (int i = this.level; i < targetLevel; i++) {

            bonusPoints = 2;

            for (int j = i; j >= 10;) {
                bonusPoints++;
                j -= 10;
            }

            this.baseHlh += hlhAllocation;
            this.baseAtk += atkAllocation;
            this.baseDef += defAllocation;
            this.baseMag += magAllocation;
            this.baseSpd += spdAllocation;

            Tools.clear();
            letsGo.play();
            System.out.println(this.name + " leveled up to level " + (i + 1) + "!\n");

            if (hlhAllocation != 0 ) {
                System.out.println("[1] Hlh " + (this.baseHlh - hlhAllocation) + " -> " + (this.baseHlh));
            } else { System.out.println("[1] Hlh " + this.baseHlh); }

            if (atkAllocation != 0) {
                System.out.println("[2] Atk " + (this.baseAtk - atkAllocation) + " -> " + (this.baseAtk));
            } else { System.out.println("[2] Atk " + this.baseAtk); }

            if (defAllocation != 0) {
                System.out.println("[3] Def " + (this.baseDef - defAllocation) + " -> " + (this.baseDef));
            } else { System.out.println("[3] Def " + this.baseDef); }

            if (magAllocation != 0) {
                System.out.println("[4] Mag " + (this.baseMag - magAllocation) + " -> " + (this.baseMag));
            } else { System.out.println("[4] Mag " + this.baseMag); }

            if (spdAllocation != 0) {
                System.out.println("[5] Spd " + (this.baseSpd - spdAllocation) + " -> " + (this.baseSpd) + "\n");
            } else { System.out.println("[5] Spd " + this.baseSpd + "\n"); }

            System.out.println(this.name + "'s stats have increased!");

            for (int j = bonusPoints; j > 0; j--) {

                System.out.println(this.name + " has " + j + " bonus point(s) remaining. Input 1-5 to invest in a stat.");
                int k = Tools.getUserInt(1, 5);
                int[] statChoice = {0, 0, 0, 0, 0, 0};
                statChoice[k - 1] = 1;
                Tools.clear();

                System.out.println(this.name + " leveled up to level " + (i + 1) + "!\n");

                for (int l = 0; l <= 5; l++) {

                    switch (l) {
                        case 0:
                            System.out.print("[1] Hlh " + this.baseHlh);
                            break;
                        case 1:
                            System.out.print("[2] Atk " + this.baseAtk);
                            break;
                        case 2:
                            System.out.print("[3] Def " + this.baseDef);
                            break;
                        case 3:
                            System.out.print("[4] Mag " + this.baseMag);
                            break;
                        case 4:
                            System.out.print("[5] Spd " + this.baseSpd);
                            break;
                    }

                    if (statChoice[l] != 0) {
                        System.out.print(" -> ");
                        switch (l) {
                            case 0:
                                System.out.println(this.baseHlh + statChoice[l]);
                                break;
                            case 1:
                                System.out.println(this.baseAtk + statChoice[l]);
                                break;
                            case 2:
                                System.out.println(this.baseDef + statChoice[l]);
                                break;
                            case 3:
                                System.out.println(this.baseMag + statChoice[l]);
                                break;
                            case 4:
                                System.out.println(this.baseSpd + statChoice[l]);
                                break;
                        }
                    } else {
                        System.out.println();
                    }
                }

                switch (k) {
                    case 1:
                        this.baseHlh += 1;
                        System.out.println(this.name + " added 1 point to health.");
                        statChoice[0] = 1;
                        break;
                    case 2:
                        this.baseAtk += 1;
                        System.out.println(this.name + " added 1 point to attack.");
                        statChoice[1] = 1;
                        break;
                    case 3:
                        this.baseDef += 1;
                        System.out.println(this.name + " added 1 point to defense.");
                        statChoice[2] = 1;
                        break;
                    case 4:
                        this.baseMag += 1;
                        System.out.println(this.name + " added 1 point to magic.");
                        statChoice[3] = 1;
                        break;
                    case 5:
                        this.baseSpd += 1;
                        System.out.println(this.name + " added 1 point to speed.");
                        statChoice[4] = 1;
                        break;
                }
            }

            this.level += 1;
            Tools.emptyPrompt();
            Tools.clear();
            // bEffects.fullHeal();  Replace with appropriate method in Entity
        }
        Tools.clear();*/
}
