package ire.combat;

import ire.combat.statuseffects.stateffects.Surprise;
import ire.entities.Enemy;
import ire.entities.Entity;
import ire.entities.Player;
import ire.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;


public class Battle {

    public ArrayList<Player> players = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    private final Surprise surprise = new Surprise();

    // ***********************************
    // Constructor & Pre-battle Functions
    // ***********************************

    public Battle(Player... players) {

        this.players.addAll(Arrays.asList(players));
    }

    public void addEnemy(Enemy... enemies) {

        this.enemies.addAll(Arrays.asList(enemies));
    }

    public boolean getAverageSpd() {

        float playersSpd;
        float enemiesSpd;
        float s = 0;
        int i = 0;

        for (Entity p: players) {
            s += p.getCurSpd();
            i++;
        }

        // Integer division moment??
        playersSpd = s / i;
        s = 0;
        i = 0;
        for (Entity e: enemies) {
            s += e.getCurSpd();
            i++;
        }
        enemiesSpd = s / i;

        return playersSpd >= enemiesSpd;
    }


    // ***********************************
    // Mid-battle Functions
    // ***********************************

    // returned boolean indicates winner
    public boolean runBattle(int surprise) {

        if (surprise != 0) {
            if (surprise == 1) {
                for (Entity p: players) {
                    this.surprise.apply(p, p);
                }
            } else {
                for (Entity e: enemies) {
                    this.surprise.apply(e, e);
                }
            }
        }

        boolean playerTurn = getAverageSpd();

        while (checkDead() == 0) {
            if (playerTurn) {
                for (Entity p: players) {
                    p.bEffects.incrementStatusEffects(true);
                }
                defend(enemies);
                attack(players, enemies);

            } else {

                for (Entity e: enemies) {
                    e.bEffects.incrementStatusEffects(true);
                }

                if (checkDead() == 1) {
                    continue;
                }
                defend(players);
                attack(enemies, players);
            }

            playerTurn = !(playerTurn);
        }

        if (checkDead() == 1) {
            Tools.clear();
            giveRewards(enemies, players);
            return true;
        } else {
            return false;
        }
    }

    private void defend(ArrayList<? extends Entity> defenders) {

        for (Entity d: defenders) {
            if (d.isAlive()) {
                d.promptDefend();
            }
        }
    }

    private void attack(ArrayList<? extends Entity> attackers, ArrayList<? extends Entity> defenders) {

        for (Entity a: attackers) {
            if (a.isAlive()) {
                a.promptAttack((ArrayList<Entity>) defenders);
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
    public int checkDead() {

        int deadCount = 0;

        for (Enemy enemy : enemies) {
            if (!(enemy.isAlive())) {
                deadCount++;
            }
        }

        if (deadCount == enemies.size()) {
            return 1;
        }


        deadCount = 0;

        for (Player player : players) {
            if (!(player.isAlive())) {
                deadCount++;
            }
        }

        if (deadCount == players.size()) {
            return 2;
        }


        return 0;
    }

    // ***********************************
    // Post-battle Functions
    // ***********************************


    private void giveRewards(ArrayList<Enemy> enemies, ArrayList<Player> players) {

        double xpGained = 0;
        //boolean rewardGained = false;

        players.get(0).playWin();

        for (Enemy e: enemies) {
            xpGained += e.getRewardXp();
        }

        xpGained /= players.size();
        xpGained = Math.round(xpGained);

        System.out.println("Everyone got " + (int) xpGained + " xp.");
        Tools.emptyPrompt();
        for (Player p: players) {
            p.addXp((int) xpGained);
        }

        // Possibly include inventory limit
        // Outsource to party inventory
        /*for (Enemy e: enemies) {
            if (e.calculateReward())  {
                players.get(0).playerInventory.addItem(e.giveReward());
                System.out.println(players.get(0).getName() + " received " + e.getRewardName());
                rewardGained = true;
            }
        }

        if (rewardGained) {
            IRE.IREModule.Tools.emptyPrompt();
        }*/

    }
}
