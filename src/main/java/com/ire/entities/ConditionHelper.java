package com.ire.entities;

import com.ire.audio.AudioClip;
import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.tools.PrintControl;

import java.io.Serializable;

public class ConditionHelper implements Serializable {

    protected static final AudioClip HEAL_SOUND = new AudioClip("leech");
    protected static final AudioClip REVIVE_SOUND = new AudioClip("revive");
    protected AudioClip deathSound;
    private boolean alive;

    // TODO: Change constructors of methods with sounds to Strings for shortness' sake.
    public ConditionHelper(String deathSound) {
        this.deathSound = new AudioClip(deathSound);
        this.alive = true;
    }

    public void takeDamage(Entity e, int damage, boolean showMessage) {

        if (damage <= 0) {
            printDamageMessage(e.getName() + " was struck, but took no damage.", showMessage);
            return;
        }

        if (alive) {
            e.stats.incrementHlh(-damage);
            printDamageMessage(e.getName() + " took " + damage + " damage.", showMessage);
        }
        if (!alive) {
            e.stats.incrementHlh(-damage);
            printDamageMessage(e.getName() + " is incapacitated, but took "
                    + damage + " more damage.", showMessage);
        }
        if (e.getHlh() <= -e.getCurStat("hlh")) {
            this.die(e);
            return;
        }
        if (e.getHlh() < 1 && alive) {
            this.knockOut(e);
        }
    }

    private void printDamageMessage(String message, boolean showMessage) {
        if (!showMessage) {
            return;
        }
        System.out.println(message);
        PrintControl.sleep(2000);
        System.out.println();
    }

    // TODO: Remove defensive bonuses when dead or "useless" defense.
    private void knockOut(Entity e) {
        this.deathSound.play();
        System.out.println(e.getName() + " was incapacitated.");
        PrintControl.sleep(1500);
        System.out.println();
        if (alive) {
            this.alive = false;
            e.checkRemoveStatusEffects(RemoveCondition.DEATH);
        }
        // add coffin dance gif for party wipe in defiled mode?
    }

    // TODO: Consider making an Enum for "status" (alive/incapacitated/death).
    private void die(Entity e) {
        if (alive) {
            this.deathSound.play();
            System.out.println(e.getName() + " has died!");
            PrintControl.sleep(1500);
            System.out.println();
            this.alive = false;
            e.checkRemoveStatusEffects(RemoveCondition.DEATH);
        }
    }

    private void revive(String name) {
        this.alive = true;
        REVIVE_SOUND.play();
        System.out.println(name + " was resurrected.");
        PrintControl.sleep(1500);
        System.out.println();
    }


    // Regen/bleed Methods

    public void regenerateHealth(Entity e, int regenStrength, boolean message, boolean excess) {
        if ((e.getHlh() + regenStrength) > e.getCurStat("hlh")) {
            overRegenHealth(e, regenStrength, message, excess);
            return;
        }
        if (regenStrength > 0) {
            normalRegenHealth(e, regenStrength, message);
            return;
        }
        if (message) {
            System.out.println(e.getName() + " received a useless heal.");
            // Heal error
        }
    }

    private void overRegenHealth(Entity e, int regenStrength, boolean message, boolean excess) {
        if (effectiveOverRegenHealth(e, regenStrength, message, excess)) {
            return;
        }
        if (message) {
            printGenerativeMessage(e.getName() + " was healed, but was already beyond full health.");
            // heal error
        }
    }

    private boolean effectiveOverRegenHealth(Entity e, int regenStrength, boolean message, boolean excess) {
        if (excess) {
            if (message) {
                printGenerativeMessage(e.getName() + " healed beyond the limit for " + regenStrength + " health.");
                HEAL_SOUND.play();  // Beyond-limit sfx
            }
            e.stats.incrementHlh(regenStrength);
            if (!this.isAlive()) {
                this.revive(e.getName());
            }
            return true;
        }
        if (e.getHlh() < e.getCurStat("hlh")) {
            if (message) {
                printGenerativeMessage(e.getName() + " healed for " +
                        (e.getCurStat("hlh") - e.stats.getHlh()) + " health.");
                HEAL_SOUND.play();
            }
            e.stats.setHlh(e.getCurStat("hlh"));
            if (!this.isAlive()) {
                this.revive(e.getName());
            }
            return true;
        }
        return false;
    }

    private void normalRegenHealth(Entity e, int regenStrength, boolean message) {
        if (message) {
            System.out.println(e.getName() + " healed " + regenStrength + " health.");
            HEAL_SOUND.play();
        }
        e.stats.incrementHlh(regenStrength);
        if (!this.isAlive()) {
            this.revive(e.getName());
        }
    }

    public void bleedMana(Entity e, int bleedStrength, boolean message, boolean excess) {
        if ((e.getMan() - bleedStrength) < 0) {
            overBleedMana(e, bleedStrength, message, excess);
            return;
        }
        if (bleedStrength > 0) {
            normalBleedMana(e, bleedStrength, message);
            return;
        }
        if (message) {
            System.out.println(e.getName() + " resisted losing mana.");
        }
    }

    private void overBleedMana(Entity e, int bleedStrength, boolean message, boolean excess) {
        if (effectiveOverBleedMana(e, bleedStrength, message, excess)) {
            return;
        }
        if (message) {
            System.out.println(e.getName() + " is being drained of mana, but has none left to lose.");
            // mana loss error?
        }
    }

    private boolean effectiveOverBleedMana(Entity e, int bleedStrength, boolean message, boolean excess) {
        if (excess) {
            if (message) {
                System.out.println(e.getName() + " is being drained of " + bleedStrength);
                // mana loss sound
            }
            e.incrementMan(-bleedStrength);
            return true;
        }
        if (e.getMan() < bleedStrength) {
            if (message) {
                System.out.println(e.getName() + " lost " + e.getMan() + " mana.");
                // mana loss sound?
            }
            e.stats.setMan(0);
            return true;
        }
        return false;
    }

    private void normalBleedMana(Entity e, int bleedStrength, boolean message) {
        if (message) {
            System.out.println(e.getName() + " lost " + bleedStrength + " mana.");
            // mana loss sound
        }
        e.incrementMan(-bleedStrength);
    }

    public void regenerateMana(Entity e, int regenStrength, boolean showMessage, boolean excess) {
        if ((e.getMan() + regenStrength) > e.getCurStat("mag")) {
            overRegenMana(e, regenStrength, showMessage, excess);
            return;
        }
        if (regenStrength > 0) {
            normalRegenMana(e, regenStrength, showMessage);
            return;
        }
        if (showMessage) {
            printGenerativeMessage(e.getName() + " tried to regenerate mana, but failed.");
        }
    }

    private void overRegenMana(Entity e, int regenStrength, boolean showMessage, boolean excess) {
        if (effectiveOverRegenMana(e, regenStrength, showMessage, excess)) {
            return;
        }
        if (showMessage) {
            printGenerativeMessage(e.getName() + " regenerated mana, but already had maximum mana.");
        }
    }

    private boolean effectiveOverRegenMana(Entity e, int regenStrength, boolean showMessage, boolean excess) {
        if (excess) {
            if (showMessage) {
                printGenerativeMessage(e.getName() + " over-regenerated, gaining " + regenStrength + " mana.");
            }
            e.incrementMan(regenStrength);
            return true;
        }
        if (e.getMan() < e.getCurStat("mag")) {
            if (showMessage) {
                printGenerativeMessage(e.getName() + " regenerated " + (e.getCurStat("mag") - e.getMan()) + " mana");
            }
            e.stats.setMan(e.getCurStat("mag"));
            return true;
        }
        return false;
    }

    private void normalRegenMana(Entity e, int regenStrength, boolean showMessage) {
        if (showMessage) {
            printGenerativeMessage(e.getName() + " regenerated " + regenStrength + " mana.");
        }
        e.incrementMan(regenStrength);
    }

    // TODO: Add sound as a parameter
    private void printGenerativeMessage(String message) {
        System.out.println(message);
        PrintControl.sleep(2000);
        System.out.println();
    }

    public void fullHeal(Entity e, RemoveCondition condition) {
        e.checkRemoveStatusEffects(condition);
        regenerateHealth(e, (-e.getHlh() + e.getCurStat("hlh")), false, false);
        regenerateMana(e, (-e.getMan() + e.getCurStat("mag")), false, false);
    }

    public boolean isAlive() {
        return this.alive;
    }
}
