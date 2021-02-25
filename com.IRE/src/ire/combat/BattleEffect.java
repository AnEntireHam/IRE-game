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
/*

    public void takeDebuff(int damage, int strength, Entity attacker) {

        int type = attacker.getSpellType() - 3;
        double baseChance = 0;
        int severity = 0;
        int stat = 0;
        int duration;
        double rand = Math.random();
        boolean debug = e.isDebug();

        if (debug) {
            System.out.println("Random number: " + rand);
        }

        switch (type) {
            case 0 -> stat = e.getMaxHlh();
            case 1 -> stat = e.getCurAtk();
            case 2 -> stat = e.getCurDef();
            case 3 -> stat = e.getCurMag();
            case 4 -> stat = e.getCurSpd();
        }

        switch (type) {

            case 0, 1, 2, 3, 4 -> {

                duration = 5;

                switch (strength) {
                    case 1 -> baseChance = .60;
                    case 2 -> baseChance = .68;
                    case 3 -> baseChance = .75;
                }

                if (debug) {
                    System.out.println("Probability: " + (baseChance + ((float) (attacker.getCurMag() - stat) * .003333)));
                }


                if (rand < (baseChance + ((float) (attacker.getCurMag() - stat) * .003333))) {

                    // Possibly add varying severities depending on how important a stat is to the attacker.
                    switch (strength) {
                        case 1 -> severity = 85;
                        case 2 -> severity = 77;
                        case 3 -> severity = 70;
                    }

                    this.e.bEffects.buff(type, severity, duration);
                    System.out.println(e.getName() + " had their stats lowered.");

                } else {

                    System.out.println(e.getName() + " didn't have their stats lowered.");
                }

            }

            case 5 -> {

                duration = 3;

                if (debug) {
                    System.out.println("Probability: " + (.85 - ((float) damage / (float) e.getMaxHlh())));
                    System.out.println(damage + "" + e.getMaxHlh());
                }


                if (rand < (.85 - ((float) damage / (float) e.getMaxHlh()))) {
                    System.out.println(e.getName() + " started bleeding!");
                    this.e.bEffects.buff(0, damage, duration);
                } else {
                    System.out.println(e.getName() + " didn't start bleeding.");
                }
            }

            case 6 -> {

                duration = 5;

                switch (strength) {
                    case 1 -> severity = 50;
                    case 2 -> severity = 60;
                    case 3 -> severity = 70;
                }

                severity = IRE.IREModule.Tools.round(IRE.IREModule.Tools.round(e.getCurMag() / 4.0) * severity);

                if (debug) {
                    System.out.println("Probability" + .85 + ((float) (attacker.getCurMag() - stat) * .3333));
                }

                if (rand > (.85 + ((float) (attacker.getCurMag() - stat) * .3333))) {
                    this.e.bEffects.buff(6, severity, duration);
                    System.out.println(e.getName() + " is losing mana.");
                } else {
                    System.out.println(e.getName() + " didn't start losing mana.");
                }
            }
            //default -> throw new IllegalStateException("Unexpected value: " + type); Causes error?
        }
        IRE.IREModule.Tools.sleep(1000);
    }
*/

    public void buff(int stat, int strength, int duration) {

        // More advanced logic will probably be needed for balancing purposes in the future.
        this.e.setNewBuff(stat);

        if (e.getBuffDurations() == 0) {

            this.e.setBuffStacks(1);
            this.e.setBuffDurations(duration);
            this.e.setBuffStrengths(strength / e.getBuffStacks());
            this.e.setBuffStacks(e.getBuffStacks() + 1);

        } else {

            this.e.setBuffDurations(e.getBuffDurations());

            if (stat == 5 || stat == 6) {
                this.e.setBuffStrengths(e.getBuffStrengths() + strength);

            } else {

                int severity = Tools.round((float) (100 - strength) / e.getBuffStacks());

                if (strength < 100) {
                    this.e.setBuffStrengths(e.getBuffStrengths() - severity);

                } else {
                    this.e.setBuffStrengths(e.getBuffStrengths() + severity);
                }

                this.e.setBuffStacks(e.getBuffStacks() + 1);

            }

            // rounding can make bleeds look larger than they should be
            // Debacle: To round or stack?

        }

        this.e.setNewBuff(-1);
    }

    public void incrementBuffs(boolean tick) {

        // 0-4 represents stats, 5 represents bleeds, 6 represents mana bleeds

        this.e.setNewBuff(0);

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
        }
    }

    public void fullHeal() {

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
                /*switch (i) {
                    case 1 -> this.maxHlh = this.baseHlh;
                    case 2 -> this.curAtk = this.baseAtk;
                    case 3 -> this.curDef = this.baseDef;
                    case 4 -> this.curMag = this.baseMag;
                    case 5 -> this.curSpd = this.baseSpd;
                    case 6 -> this.man = this.curMag;
                }*/

            }

        }

        this.e.setHlh(e.getMaxHlh());
        this.e.setMan(e.getCurMag());

    }

    public void resetStats() {

        this.e.setMaxHlh(e.getBaseHlh());
        this.e.setCurAtk(e.getBaseAtk());
        this.e.setCurDef(e.getBaseDef());
        this.e.setCurMag(e.getBaseMag());
        this.e.setCurSpd(e.getBaseSpd());
        this.e.setMan(e.getCurMag());
    }

    // ***********************************
    // Damage Functions
    // ***********************************

    public void takeDamage(int damage, boolean message) {

        if (damage <= 0 && message) {
            System.out.println(e.getName() + " was struck, but took no damage.");
            Tools.sleep(2000);
            System.out.println(" ");

        } else {

            if (!(e.isAlive())) {
                this.e.setHlh(e.getHlh() - damage);
                if (message) {
                    System.out.println(e.getName() + " is dead, but took " + damage + " more damage.");
                    Tools.sleep(2000);
                    System.out.println(" ");
                }

            } else {
                this.e.setHlh(e.getHlh() - damage);
                if (message) {
                    System.out.println(e.getName() + " took " + damage + " damage.");
                    Tools.sleep(2000);
                    System.out.println(" ");


                    // Field/accessors unimplemented in Entity
                    /*if (e.getDefenseChoice().equals("Cure") && e.isChannelDef()) {
                        this.e.setChannelDef(false);
                        System.out.println(e.getName() + "'s channel was interrupted.");
                    }*/

                }

                if (e.getHlh() < 1) {
                    this.die();
                }
            }
        }
    }

    // This will have to change in the future if overhealing is implemented.
    public void heal(int heal, boolean message) {

        if (e.getHlh() == e.getMaxHlh()) {

            if (message) {
                System.out.println(e.getName() + " was healed, but was already at full health.");
            }

        } else if ((e.getHlh() + heal) > e.getMaxHlh()) {

            if (e.getMaxHlh() < e.getMaxHlh()) {

                if (message) {
                    System.out.println(e.getName() + " healed " + (e.getMaxHlh() - e.getHlh()) + " health.");
                }
                e.getHealSound().play();
                e.setHlh(e.getMaxHlh());

            } else {

                if (message) {
                    System.out.println(e.getName() + " was healed, but was already beyond full health.");
                }

            }
        } else {

            if (message) {
                System.out.println(e.getName() + " healed " + heal + " health.");
            }
            e.getHealSound().play();
            e.setHlh(e.getHlh() + heal);

        }

        if (message) {
            Tools.sleep(2000);
            System.out.println();
        }
    }

    public void die() {

        this.e.setAlive(false);
        //Utilities.sleep(1000);
        e.getDeathSound().play();
        System.out.println(e.getName() + " has died.");
        Tools.sleep(1500);
        System.out.println(" ");
        // add coffin gif for party wipe?
    }
}
