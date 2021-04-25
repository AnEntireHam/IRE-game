package com.ire.entities.players;

import com.ire.audio.AudioStream;
import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;
import com.ire.tools.UserInput;

public abstract class Player extends Entity {


    // Fields

    protected int hlhAllocation, atkAllocation, defAllocation, magAllocation, spdAllocation;
    protected final static AudioStream LETS_GO = new AudioStream("letsGo");


    // Constructor

    public Player(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                  String name, String deathSound,
                  int hlhAllocation, int atkAllocation, int defAllocation, int magAllocation, int spdAllocation) {
        super(1, baseHlh, baseAtk, baseDef, baseMag, baseSpd,
                name,deathSound, true);

        this.hlhAllocation = hlhAllocation;
        this.atkAllocation = atkAllocation;
        this.defAllocation = defAllocation;
        this.magAllocation = magAllocation;
        this.spdAllocation = spdAllocation;
        this.levelUp(level);
    }


    // Level Functions

    // This makes the presumption that weapons/armor won't change baseStats, or that seeing modified values is okay.
    @Override
    protected void levelUp(int targetLevel) {
        if (this.level == targetLevel) {
            return;
        }

        PrintControl.clear();

        int[] previousBaseStats = {this.getBaseStat("hlh"), this.getBaseStat("atk"),
                this.getBaseStat("def"), this.getBaseStat("mag"), this.getBaseStat("spd")};

        addAllocations(targetLevel);

        String[] statPrefixes = {"[1] Hlh ", "[2] Atk ", "[3] Def ", "[4] Mag ", "[5] Spd "};
        String message = (this.name + "'s stats have increased!");
        LETS_GO.play();

        allocateBonusPoints(targetLevel, previousBaseStats, statPrefixes, message);

        this.fullHeal(RemoveCondition.LEVEL_UP);
    }

    // Weird version of PrintControl' menu. May be able to consolidate later.
    private void allocateBonusPoints(int targetLevel, int[] previousBaseStats, String[] statPrefixes, String message) {
        int bonusPoints = calculateBonusPoints(targetLevel);
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

        PrintControl.emptyPrompt();
        PrintControl.clear();
    }

    private void addAllocations(int targetLevel) {
        int differenceLevels = targetLevel - this.level;

        this.stats.incrementBaseStat("hlh", hlhAllocation * differenceLevels);
        this.stats.incrementBaseStat("atk", atkAllocation * differenceLevels);
        this.stats.incrementBaseStat("def", defAllocation * differenceLevels);
        this.stats.incrementBaseStat("mag", magAllocation * differenceLevels);
        this.stats.incrementBaseStat("spd", spdAllocation * differenceLevels);

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

            if (this.stats.getBaseStat(i) != previousBaseStats[i]) {

                previousBaseStats[i] = this.stats.getBaseStat(i);
                statDisplay[i] += (" -> " + this.stats.getBaseStat(i));
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
        int choice = UserInput.getUserInt(1, 5);
        String statName = "";

        switch (choice) {
            case 1:
                this.stats.incrementBaseStat("hlh");
                statName = "health";
                break;
            case 2:
                this.stats.incrementBaseStat("atk");
                statName = "attack";
                break;
            case 3:
                this.stats.incrementBaseStat("def");
                statName = "defense";
                break;
            case 4:
                this.stats.incrementBaseStat("mag");
                statName = "magic";
                break;
            case 5:
                this.stats.incrementBaseStat("spd");
                statName = "speed";
                break;
        }
        message = this.name + " added 1 point to " + statName + ".";
        return message;
    }
}
