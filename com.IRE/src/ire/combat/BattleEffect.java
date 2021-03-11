package ire.combat;

import ire.entities.Entity;
import ire.tools.Tools;

public class BattleEffect {


    // ***********************************
    // Fields and Constructor
    // ***********************************

    private final Entity e;

    public BattleEffect(Entity e) {

        this.e = e;
    }

    // ***********************************
    // Status Functions
    // ***********************************

    public void incrementStatusEffects(boolean tick) {

        // 0-4 represents stats, 5 represents bleeds, 6 represents mana bleeds

        /*this.e.setNewBuff(0);

        if (e.getBuffDurations() != 0) {

            int prevMax = e.getMaxHlh();

            this.e.setMaxHlh(Tools.round(e.getBaseHlh() * ((double) e.getBuffStrengths() / 100)));

            if (prevMax != e.getMaxHlh() && e.getMaxHlh() > e.getMaxHlh()) {
                int damage = e.getHlh() - e.getMaxHlh();
                this.takeDamage(damage, false);
                System.out.println(e.getName() + " took " + damage + " damage from lowered maximum health.");
                Tools.sleep(2000);
            }
        } else {
            this.e.setMaxHlh(e.getBaseHlh());
        }

        for (int i = 1; i < 5; i++) {

            this.e.setNewBuff(e.getNewBuff() + 1);

            if (e.getBuffDurations() != 0) {
                int stat = Tools.round( e.getStat(i) * ((double) e.getBuffStrengths() / 100));
                this.e.setStat(i + 5, stat);
            } else {
                this.e.setStat(i + 5, e.getStat(i));
            }
        }

        this.e.setNewBuff(5);

        if (e.getBuffDurations() != 0 && tick) {

            if (e.getBuffStrengths() > 0) {
                if (e.isAlive()) {
                    System.out.println(e.getName() + " bled for " + e.getBuffStrengths() + " damage.");
                    Tools.sleep(1000);
                }

                this.takeDamage(e.getBuffStrengths(), false);

            } else {

                this.heal(e.getBuffStrengths() * -1, true);
            }
        }


        this.e.setNewBuff(6);
        int manRegen = Tools.round(e.getCurMag() / 4.0);

        if (e.getBuffDurations() != 0 && tick) {

            if (e.getBuffStrengths() > 0) {

                if (e.getMan() - e.getBuffStrengths() < 0) {
                    System.out.println(e.getName() + " lost " + e.getBuffStrengths() + " mana.");
                    this.e.setMan(e.getBuffStrengths());

                } else {
                    System.out.println(e.getName() + " lost " + e.getMan() + " mana.");
                    this.e.setMan(0);
                }
            } else {

                if (e.getMan() + e.getBuffStrengths() < e.getCurMag()) {
                    System.out.println(e.getName() + " got an additional " + e.getBuffStrengths() + " mana.");
                    this.e.setMan(e.getMan() + e.getBuffStrengths());
                } else {
                    System.out.println(e.getName() + " got an additional " + (e.getCurMag() - e.getMan()) + " mana.");
                    this.e.setMan(e.getCurMag());
                }
            }
        }

        if (e.getMan() + manRegen < e.getCurMag() && tick) {
            this.e.setMan(e.getMan() + manRegen);
        } else {
            this.e.setMan(e.getCurMag());
        }

        this.e.setNewBuff(0);

        if (tick) {
            for (int i = 0; i < 7; i++) {

                if (e.getBuffDurations() > 0) {
                    this.e.setBuffDurations(e.getBuffDurations() - 1);
                }
                this.e.setNewBuff(e.getNewBuff() + 1);
            }
        }

        if (tick && e.isDebug()) {
            System.out.println(e.getName() + "'s POST BUFF STATS:");
            for (int i = 0; i < 12; i++) {
                System.out.println(e.getStat(i));
            }
        }*/
    }

    /*public void fullHeal() {

        this.e.setNewBuff(0);

        if (e.getBuffStrengths() < 0) {
            this.e.setBuffDurations(0);
        }

        for (int i = 1; i < 7; i++) {

            this.e.setNewBuff(e.getNewBuff() + 1);

            if (e.getBuffStrengths() <= 100) {

                this.e.setBuffDurations(0);
                this.e.setBuffStrengths(100);

                if (i < 5) {
                    this.e.setBuffStacks(0);
                }


                if (i < 6) {
                    this.e.setStat(i + 4, e.getStat(i - 1));
                }
                *//*switch (i) {
                    case 1 -> this.maxHlh = this.baseHlh;
                    case 2 -> this.curAtk = this.baseAtk;
                    case 3 -> this.curDef = this.baseDef;
                    case 4 -> this.curMag = this.baseMag;
                    case 5 -> this.curSpd = this.baseSpd;
                    case 6 -> this.man = this.curMag;
                }*//*

            }

        }

        this.e.setHlh(e.getMaxHlh());
        this.e.setMan(e.getCurMag());

    }*/

    // ***********************************
    // Damage Functions
    // ***********************************

    public void takeDamage(int damage, boolean message) {

        if (damage <= 0 && message) {
            System.out.println(e.getName() + " was struck, but took no damage.");
            Tools.sleep(2000);
            System.out.println(" ");

        } else {

             if (e.isAlive()) {

                 this.e.setHlh(e.getHlh() - damage);
                 if (message) {
                     System.out.println(e.getName() + " took " + damage + " damage.");
                     Tools.sleep(2000);
                     System.out.println(" ");
                 }

                 if (e.getHlh() < 1) {
                     this.die(message);
                 }

            } else {

                this.e.setHlh(e.getHlh() - damage);
                if (message) {
                    System.out.println(e.getName() + " is dead, but took " + damage + " more damage.");
                    Tools.sleep(2000);
                    System.out.println(" ");
                }
            }
        }
    }

    // This will have to change in the future if overhealing is implemented.
    public void regenerateHealth(int regenStrength, boolean message, boolean surplus) {

        if ((e.getHlh() + regenStrength) > e.getCurHlh()) {

            if (surplus) {
                if (message) {
                    System.out.println(e.getName() + " healed beyond the limit for " + regenStrength + " health.");
                    e.getHealSound().play();  //  Beyond-limit sfx
                }
                e.setHlh(e.getHlh() + regenStrength);

            } else if (e.getHlh() < e.getCurHlh()) {
                if (message) {
                    System.out.println(e.getName() + " healed for " + (e.getCurHlh() - e.getHlh()) + " health.");
                    e.getHealSound().play();
                }
                e.setHlh(e.getCurHlh());

            } else {
                if (message) {
                    System.out.println(e.getName() + " was healed, but was already beyond full health.");
                    //  heal error
                }
            }

        } else if (regenStrength > 0) {

            if (message) {
                System.out.println(e.getName() + " healed " + regenStrength + " health.");
                e.getHealSound().play();
            }
            e.setHlh(e.getHlh() + regenStrength);

        } else {

            if (message) {
                System.out.println(e.getName() + " received a useless heal.");
                //  Heal error
            }
        }

        if (message) {
            Tools.sleep(2000);
            System.out.println();
        }
    }

    public void die(boolean message) {

        this.e.setAlive(false);
        if (message) {
            e.getDeathSound().play();
            System.out.println(e.getName() + " has died.");
            Tools.sleep(1500);
            System.out.println(" ");
        }
        //  add coffin dance gif for party wipe?
    }
}
