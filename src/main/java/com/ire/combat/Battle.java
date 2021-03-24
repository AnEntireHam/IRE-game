package com.ire.combat;

import com.ire.combat.statuseffects.stateffects.Surprise;
import com.ire.entities.Enemy;
import com.ire.entities.Entity;
import com.ire.entities.Player;
import com.ire.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;


public class Battle {

    public ArrayList<Entity> team1 = new ArrayList<>();
    public ArrayList<Entity> team2 = new ArrayList<>();
    private final Surprise surprise = new Surprise();

    // ***********************************
    // Constructor & Pre-battle Methods
    // ***********************************

    //  Adding teams like this might be dubious.
    public Battle(Player... team1) {

        this.team1.addAll(Arrays.asList(team1));
    }

    public void addEnemy(Enemy... enemies) {

        this.team2.addAll(Arrays.asList(enemies));
    }

    public boolean getAverageSpd() {

        int team1Spd;
        int team2Spd;
        float s = 0;
        int i = 0;

        for (Entity e: team1) {
            s += e.getCurSpd();
            i++;
        }
        team1Spd = Math.round(s / i);

        s = 0;
        i = 0;
        for (Entity e: team2) {
            s += e.getCurSpd();
            i++;
        }
        team2Spd = Math.round(s / i);

        return team1Spd >= team2Spd;
    }


    // ***********************************
    // Mid-battle Methods
    // ***********************************

    // returned boolean indicates winner
    public boolean runBattle(int surprise) {

        if (surprise != 0) {
            if (surprise == 1) {
                for (Entity p: team1) {
                    this.surprise.apply(p, p);
                    System.out.println("You got the surprise on the enemy!");
                }
            } else {
                for (Entity e: team2) {
                    this.surprise.apply(e, e);
                    System.out.println("You got surprised!");
                }
            }
            Tools.sleep(1000);
        }

        boolean turn = getAverageSpd();

        while (checkDead() == 0) {

            if (turn) {
                runTurn(team1, team2);
                turn = false;
                continue;
            }

            runTurn(team2, team1);
            turn = true;
        }

        if (checkDead() == 1) {
            Tools.clear();
            giveRewards(team2, team1);
            return true;
        } else {
            return false;
        }
    }

    private void runTurn(ArrayList<Entity> attackers, ArrayList<Entity> defenders) {

        defend(defenders);
        attack(attackers, defenders);
        for (Entity a: attackers) {
            a.incrementStatusDurations();
        }
    }

    private void defend(ArrayList<Entity> defenders) {

        for (Entity d: defenders) {
            if (d.isAlive()) {
                d.promptDefend();
            }
        }
    }

    private void attack(ArrayList<Entity> attackers, ArrayList<Entity> defenders) {

        for (Entity a: attackers) {
            if (a.isAlive()) {
                a.promptAttack(defenders);
            }
        }

        for (Entity a: attackers) {
            if (a.isAlive()) {
                a.getCurrentAction().execute(a, defenders.get(a.getTargetIndex()));
            }
        }
    }

    /*private void execute(Entity attacker, Entity defender, int damage) {

    }*/

    // 0 battle continues 1, enemies dead, 2 players dead
    private int checkDead() {

        int deadCount = 0;

        for (Entity enemy : team2) {
            if (!(enemy.isAlive())) {
                deadCount++;
            }
        }
        if (deadCount == team2.size()) {
            return 1;
        }

        deadCount = 0;

        for (Entity player : team1) {
            if (!(player.isAlive())) {
                deadCount++;
            }
        }
        if (deadCount == team1.size()) {
            return 2;
        }

        return 0;
    }

    // ***********************************
    // Post-battle Functions
    // ***********************************


    private void giveRewards(ArrayList<Entity> winners, ArrayList<Entity> losers) {

        // 1. Tally and calculate rewards from losers. All entities should have a "getRewardXp" method.
        // 2. Count number of entities eligible to gain xp, then distribute evenly.
        // 3. Calculate items from losers. Open prompt to distribute items within party and discard.
        double xpGained = 0;
        //boolean rewardGained = false;

        // fix later
        // players.get(0).playWin();

        for (Entity e: losers) {
            // xpGained += e.getRewardXp();
        }

        xpGained /= winners.size();
        xpGained = Math.round(xpGained);

        System.out.println("Everyone got " + (int) xpGained + " xp.");
        Tools.emptyPrompt();
        /*for (Entity p: winners) {
            p.addXp((int) xpGained);
        }*/



        // Possibly include inventory limit
        // Outsource to party inventory
        /*for (Enemy e: losers) {
            if (e.calculateReward())  {
                winners.get(0).playerInventory.addItem(e.giveReward());
                System.out.println(players.get(0).getName() + " received " + e.getRewardName());
                rewardGained = true;
            }
        }

        if (rewardGained) {
            IRE.IREModule.Tools.emptyPrompt();
        }*/

    }
}
