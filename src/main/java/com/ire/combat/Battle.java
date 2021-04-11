package com.ire.combat;

import com.ire.audio.AudioStream;
import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.combat.statuseffects.stateffects.Surprise;
import com.ire.entities.Entity;
import com.ire.entities.players.Player;
import com.ire.tools.Tools;

import java.util.ArrayList;

public class Battle {

    private final static Surprise SURPRISE = new Surprise();
    private final static AudioStream WIN = new AudioStream("win");

    public ArrayList<Entity> team1 = new ArrayList<>();
    public ArrayList<Entity> team2 = new ArrayList<>();


    // Constructor

    public Battle(ArrayList<Entity> team1, ArrayList<Entity> team2) {

        this.team1.addAll(team1);
        this.team2.addAll(team2);
    }

    // Pre-battle Methods

    public void addTeam1(ArrayList<Entity> team1) {
        this.team1.addAll(team1);
    }

    public void addTeam2(ArrayList<Entity> team2) {
        this.team2.addAll(team2);
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


    // Mid-battle Methods

    // returned boolean indicates winner. True for t1, false for t2
    public boolean runBattle(int surprise) {

        determineSurprise(surprise);

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

        return endBattle();
    }

    private boolean endBattle() {

        for (Entity e : team1) {
            if (e.isAlive()) {
                e.checkRemoveStatusEffects(RemoveCondition.END_BATTLE);
            }
        }

        for (Entity e : team2) {
            if (e.isAlive()) {
                e.checkRemoveStatusEffects(RemoveCondition.END_BATTLE);
            }
        }

        if (checkDead() == 1) {
            Tools.clear();
            giveRewards(team1, team2);
            return true;
        } else {
            Tools.clear();
            giveRewards(team2, team1);
            return false;
        }
    }

    private void determineSurprise(int surprise) {

        if (surprise == 1) {
            for (Entity p: team1) {
                SURPRISE.apply(p, p);
            }
            System.out.println("You got the surprise on the enemy!");
            Tools.sleep(1000);
            return;
        }

        if (surprise == 2) {
            for (Entity e : team2) {
                SURPRISE.apply(e, e);
                System.out.println("You got surprised!");
            }
            Tools.sleep(1000);
        }
    }

    private void runTurn(ArrayList<Entity> attackers, ArrayList<Entity> defenders) {

        defend(defenders);
        attack(attackers, defenders);
        for (Entity a: attackers) {
            a.incrementStatusDurations();
        }
        System.out.println();
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
                a.getCurAction().execute(a, defenders.get(a.getTargetIndex()));
            }
        }
    }

    //  0 = battle continues, 1 = team1 wins, 2 = team2 wins
    //  0, 1, 2 assignment kind of weird, but works in context of favoring team 1.
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


    // Post-battle Functions

    private void giveRewards(ArrayList<Entity> winners, ArrayList<Entity> losers) {
        // 1. Tally and calculate rewards from losers. All entities should have a "getRewardXp" method.
        // 2. Count number of entities eligible to gain xp, then distribute evenly.
        // TODO: 3. Calculate items from losers. Open prompt to distribute items within party and discard.

        double xpGained = 0;
        boolean playersWon = false;

        for (Entity e : winners) {
            if (e instanceof Player) {
                playersWon = true;
                break;
            }
        }

        for (Entity e: losers) {
            xpGained += e.getRewardXp();
        }

        xpGained /= winners.size();
        xpGained = Math.round(xpGained);

        if (playersWon) {
            WIN.play();
            System.out.println("Everyone got " + (int) xpGained + " xp.");
            // Method to show XP bars
            Tools.emptyPrompt();
        }

        for (Entity p : winners) {
            p.addXp((int) xpGained);
        }

        // For now, assume
        /*for (Enemy e: losers) {
            if (e.calculateReward())  {
                winners.get(0).playerInventory.addItem(e.giveReward());
                System.out.println(players.get(0).getName() + " received " + e.getRewardName());
                rewardGained = true;
            }
        }*/
    }
}
