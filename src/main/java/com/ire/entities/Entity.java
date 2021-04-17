package com.ire.entities;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioClip;
import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.physicalattacks.Lunge;
import com.ire.combat.actions.attackactions.physicalattacks.Stab;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.combat.actions.defenseactions.physicaldefenses.Counter;
import com.ire.combat.actions.defenseactions.physicaldefenses.Shield;
import com.ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.combat.statuseffects.StatusEffect;
import com.ire.combat.statuseffects.generativeeffect.*;
import com.ire.tools.Bar;
import com.ire.tools.PrintControl;
import com.ire.tools.UserInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public abstract class Entity implements Serializable {

    // Fields
    // TODO: This class is big. There are probably some fields and methods that can be extracted out.

    protected String name;
    protected int level;
    protected StatHelper stats;
    // TODO: Xp fields might be weird to have in Entity. This is a bodge for now.
    // TODO: Add a bar for Xp, which displays post-battle or in party menu.
    protected int totalXp;
    protected int nextXp;
    protected int rewardXp;
    protected boolean debug;
    protected boolean alive;
    protected boolean controllable;

    // TODO: Consider having separate classes for these lists, offload add/remove methods.
    // TODO: Maybe attacks/defenses shouldn't be composed of string objects.
    protected ArrayList<String> attacks = new ArrayList<>(Arrays.asList("Stab", "Lunge"));
    protected ArrayList<String> defenses = new ArrayList<>(Arrays.asList("Shield", "Counter"));
    // Eventually equippedSpells/equippedX will be needed. Cross this bridge later.
    protected ArrayList<SpellAttack> spells = new ArrayList<>();
    protected ArrayList<SpellDefense> wards = new ArrayList<>();
    // These may need to be handled differently. For now assume one true "techniques" list.
    // protected ArrayList<Technique> techs = new ArrayList<>();

    protected ArrayList<StatusEffect> statusEffects = new ArrayList<>();
    protected ArrayList<GenerativeManager> generatives = new ArrayList<>();

    protected Stab stab = new Stab();
    protected Lunge lunge = new Lunge();
    protected Shield shield = new Shield();
    protected Counter counter = new Counter();


    protected int targetIndex;
    protected Action curAction;


    protected AudioClip deathSound;
    protected static final AudioClip HEAL_SOUND = new AudioClip("leech");
    protected static final AudioClip REVIVE_SOUND = new AudioClip("revive");

    // protected String[] passSkill = {"", "", ""};


    // Constructor

    public Entity(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                  String name, String deathSound, boolean controllable) {

        this.level = 1;
        this.stats = new StatHelper(baseHlh, baseAtk, baseDef, baseMag, baseSpd);
        this.name = name;
        this.alive = true;
        this.controllable = controllable;
        this.deathSound = new AudioClip(deathSound);

        //  USE THE WORD "SUNDER", "BAP" somewhere PLEASE
        this.totalXp = calculateNextXp(this.level);
        this.nextXp = calculateNextXp(this.level + 1);
        // This scale is temporary.
        this.rewardXp = (int) ((nextXp - totalXp) / 4.0f) + 1;

        this.levelUp(level);

        HealthGenerativeManager healthManager = new HealthGenerativeManager();
        ManaGenerativeManager manaManager = new ManaGenerativeManager();

        this.generatives.add(healthManager);
        this.generatives.add(manaManager);
    }


    // Level and Xp Methods

    protected abstract void levelUp(int targetLevel);

    public void addXp(int xp) {

        if (xp < 0) {
            throw new IllegalArgumentException("Xp must be a positive integer");
        }

        this.totalXp += xp;
        int levelsGained = 0;

        while (this.totalXp >= this.nextXp) {
            levelsGained++;
            this.nextXp = calculateNextXp(this.level + levelsGained + 1);
        }
        this.levelUp(this.level + levelsGained);
    }

    protected int calculateNextXp(int targetLevel) {
        return (int) Math.pow(targetLevel, 3);
    }


    // generateX methods

    // Should be a part of battleHud(), but it doesn't necessarily have to be the entire part of enemy's display.
    public String generateBattleStatus(boolean detailed) {

        StringBuilder output = new StringBuilder();

        //  I'm unhappy with this consistent spacing, but it might be preferable in the future.
        // Perhaps the spacing should be based off of the LONGEST name in the target list.
        output.append(name);

        for (int j = output.length(); j < 10; j++) {
            output.append(" ");
        }

        output.append("  Lv. ").append(level).append("  ");

        for (int j = output.length(); j < 8; j++) {
            output.append(" ");
        }


        /*
         * Consider adding a "shields/armor" color rather than just "yellow".
         * Length should probably be longer depending on the enemy/character... which ones, and when?
         */
        Attribute[] colors = new Attribute[]{TEXT_COLOR(100, 165, 55), TEXT_COLOR(230, 175, 20)};
        output.append(Bar.createBar(this.getHlh(), this.getCurStat("hlh"), 20, colors)).append("  ");

        for (StatusEffect se: statusEffects) {
            output.append(se.generateDisplay());
        }

        for (GenerativeManager gm: generatives) {
            output.append(gm.generateDisplay());
        }

        if (detailed) {
            output.append(generateStats());
        }

        return output.toString();
    }

    public String generateStats() {

        StringBuilder output = new StringBuilder();

        output.append("Hlh: ").append(getHlh()).append("/").append(getCurStat("hlh"))
                .append("  Atk: ").append(getCurStat("atk"))
                .append("  Def: ").append(getCurStat("def"))
                .append(" Mag: ");

        if (!spells.isEmpty()) {
            output.append(getMan()).append("/");
        }
        output.append(getCurStat("mag"));
        output.append("  Spd: ").append(getCurStat("spd"));

        return output.toString();
    }


    // Healing and Damage Methods


    public void fullHeal(RemoveCondition condition) {

        this.checkRemoveStatusEffects(condition);
        recalculateCurStats();
        resetPointStats();
    }

    public void recalculateCurStats() {
        this.getCurStat("hlh");
        this.getCurStat("atk");
        this.getCurStat("def");
        this.getCurStat("mag");
        this.getCurStat("spd");
    }

    public void resetPointStats() {

        // TODO: This is a bad way to implement this.
        regenerateHealth(this.getCurStat("hlh"), false, false);
        regenerateMana(this.getCurStat("mag"), false, false);

        this.stats.setHlh(this.getCurStat("hlh"));
        this.stats.setMan(this.getCurStat("mag"));

        if (!this.isAlive()) {
            this.revive(true);
        }
    }

    public void regenerateHealth(int regenStrength, boolean message, boolean surplus) {

        if ((this.stats.getHlh() + regenStrength) > this.getCurStat("hlh")) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " healed beyond the limit for " + regenStrength + " health.");
                    HEAL_SOUND.play();  // Beyond-limit sfx
                }

                this.stats.incrementHlh(regenStrength);
                if (!this.isAlive()) {
                    this.revive(true);
                }

            } else if (this.stats.getHlh() < this.getCurStat("hlh")) {
                if (message) {
                    System.out.println(name + " healed for " + (this.getCurStat("hlh") - this.stats.getHlh()) + " health.");
                    HEAL_SOUND.play();
                }
                this.stats.setHlh(this.getCurStat("hlh"));
                if (!this.isAlive()) {
                    this.revive(true);
                }

            } else {
                if (message) {
                    System.out.println(name + " was healed, but was already beyond full health.");
                    // heal error
                }
            }

        } else if (regenStrength > 0) {

            if (message) {
                System.out.println(name + " healed " + regenStrength + " health.");
                HEAL_SOUND.play();
            }
            this.stats.incrementHlh(regenStrength);
            if (!this.isAlive()) {
                this.revive(true);
            }

        } else {

            if (message) {
                System.out.println(name + " received a useless heal.");
                // Heal error
            }
        }

        if (message) {
            PrintControl.sleep(2000);
            System.out.println();
        }
    }

    public void takeDamage(int damage, boolean showMessage) {

        if (damage <= 0) {
            printDamageMessage(name + " was struck, but took no damage.", showMessage);
            return;
        }

        if (alive) {
            this.stats.incrementHlh(-damage);
            printDamageMessage(name + " took " + damage + " damage.", showMessage);
        }
        if (!alive) {
            this.stats.incrementHlh(-damage);
            printDamageMessage(name + " is incapacitated, but took " + damage + " more damage.", showMessage);
        }
        if (this.stats.getHlh() <= -this.getCurStat("hlh")) {
            this.die(true);
            return;
        }
        if (this.stats.getHlh() < 1 && alive) {
            this.knockOut(true);
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

    public void bleedMana(int bleedStrength, boolean message, boolean surplus) {

        if ((this.stats.getMan() - bleedStrength) < 0) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " is being drained of " + bleedStrength);
                    // mana loss sound
                }
                this.stats.incrementMan(-bleedStrength);

            } else if (this.stats.getMan() < bleedStrength) {
                if (message) {
                    System.out.println(name + " lost " + this.stats.getMan() + " mana.");
                    // mana loss sound?
                }
                this.stats.setMan(0);

            } else {
                if (message) {
                    System.out.println(name + " is being drained of mana, but has none left to lose.");
                    // mana loss error?
                }
            }

        } else if (bleedStrength > 0) {

            if (message) {
                System.out.println(name + " lost " + bleedStrength + " mana.");
                // mana loss sound
            }
            this.stats.incrementMan(-bleedStrength);

        } else {

            if (message) {
                System.out.println(name + " resisted losing mana.");
            }
        }

        if (message) {
            PrintControl.sleep(2000);
            System.out.println();
        }
    }

    public void regenerateMana(int regenStrength, boolean showMessage, boolean excess) {

        if ((this.stats.getMan() + regenStrength) > getCurStat("mag")) {
            handleOverRegen(regenStrength, showMessage, excess);
            return;
        }

        if (regenStrength > 0) {
            handleNormalRegen(regenStrength, showMessage);
            return;
        }

        if (showMessage) {
            printRegenMessage(name + " tried to regenerate mana, but failed.");
        }
    }

    private void handleOverRegen(int regenStrength, boolean showMessage, boolean excess) {

        if (excess) {
            if (showMessage) {
                printRegenMessage(name + " over-regenerated, gaining " + regenStrength + " mana.");
            }
            this.stats.incrementMan(regenStrength);
            return;
        }

        if (this.stats.getMan() < getCurStat("mag")) {
            if (showMessage) {
                printRegenMessage(name + " regenerated " + (getCurStat("mag") - this.stats.getMan()) + " mana");
            }
            this.stats.setMan(getCurStat("mag"));
            return;
        }

        if (showMessage) {
            printRegenMessage(name + " regenerated mana, but already had maximum mana.");
        }
    }

    private void handleNormalRegen(int regenStrength, boolean showMessage) {

        if (showMessage) {
            printRegenMessage(name + " regenerated " + regenStrength + " mana.");
        }
        this.stats.incrementMan(regenStrength);
    }

    // TODO: Add sound as a parameter
    private void printRegenMessage(String message) {

        System.out.println(message);
        PrintControl.sleep(2000);
        System.out.println();
    }

    // TODO: Remove defensive bonuses when dead or "useless" defense.
    private void knockOut(boolean message) {
        if (message) {
            this.deathSound.play();
            System.out.println(name + " was incapacitated.");
            PrintControl.sleep(1500);
            System.out.println();
        }
        if (alive) {
            this.alive = false;
            this.checkRemoveStatusEffects(RemoveCondition.DEATH);
        }
        // add coffin dance gif for party wipe in defiled mode?
    }

    // TODO: Consider making an Enum for "status" (alive/incapacitated/death).
    private void die(boolean message) {
        if (message) {
            if (alive) {
                this.deathSound.play();
            }
            System.out.println(name + " has died!");
            PrintControl.sleep(1500);
            System.out.println();
        }
        if (alive) {
            this.alive = false;
            this.checkRemoveStatusEffects(RemoveCondition.DEATH);
        }
    }

    public void revive(boolean message) {

        this.alive = true;

        if (message) {
            REVIVE_SOUND.play();
            System.out.println(name + " was resurrected.");
            PrintControl.sleep(1500);
            System.out.println();
        }
    }


    // Attack and Defense Methods

    public void promptAttack(ArrayList<Entity> targets) {

        int choice;
        boolean confirmed = false;

        while (!confirmed) {

            choice = generateActionChoice(attacks, "defending");

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
                        choice = SpellAttack.menu(
                                spells, this.stats.getMan(), this.getCurStat("mag"), this.controllable);

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
        }
    }

    public void promptDefend() {

        int choice;
        boolean confirmed = false;

        while (!confirmed) {

            choice = generateActionChoice(defenses, "attacking");

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
                    choice = SpellDefense.menu(this.wards, this.controllable);
                    if (choice != 0) {
                        this.setCurAction(wards.get(choice - 1));
                        confirmed = true;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + defenses.get(choice));
            }
        }
    }

    private int generateActionChoice(ArrayList<String> options, String verb) {
        if (this.controllable) {
            PrintControl.clear();
            System.out.println("Enemies are " + verb + ". Select an action.");
            System.out.println("\n" + this.generateBattleStatus(true) + "\n");
            return UserInput.menu(options);
        }
        Random rand = new Random();
        return rand.nextInt(options.size()) + 1;
    }

    protected boolean promptTargetIndex(ArrayList<Entity> targets) {

        ArrayList<String> options = new ArrayList<>();
        int choice;

        PrintControl.sortEntityList(targets);

        for (Entity t : targets) {
            if (t.isAlive()) {
                options.add(t.generateBattleStatus(true));
            }
        }

        if (this.controllable) {
            System.out.println("Select a target.");
            choice = UserInput.cancelableMenu(options) - 1;
            if (choice != -1) {
                this.targetIndex = choice;
                return true;
            }
            return false;
        }

        Random rand = new Random();
        choice = rand.nextInt(options.size());
        this.targetIndex = choice;
        return true;
    }

    public void addAttack(String attack) {
        attacks.add(attack);
    }

    public void addDefense(String defense) {
        defenses.add(defense);
    }

    public void addSpell(SpellAttack spell) {

        if (!attacks.contains("Cast")) {
            attacks.add(attacks.indexOf("Lunge") + 1, "Cast");
        }

        spells.add(spell);
    }

    public void addWard(SpellDefense ward) {

        if (!defenses.contains("Ward")) {
            defenses.add(defenses.indexOf("Counter") + 1, "Ward");
        }

        wards.add(ward);
    }

    public void removeAttack(int index) {
        attacks.remove(index);
    }

    public void removeDefense(int index) {
        defenses.remove(index);
    }

    public void removeSpell(int i) {

        spells.remove(i);

        if (spells.size() == 0) {
            attacks.remove("Cast");
        }
    }

    public void removeWard(int i) {

        wards.remove(i);

        if (wards.size() == 0) {
            defenses.remove("Ward");
        }
    }


    // StatusEffect methods

    public void incrementStatusDurations() {

        ArrayList<GenerativeEffect> generativeEffects = new ArrayList<>();

        int manaRegen = (int) Math.round((this.getCurStat("mag") / 4.0));
        this.regenerateMana(manaRegen, false, false);

        for (int i = 0; i < statusEffects.size(); i++) {

            if (statusEffects.get(i).incrementEffect(this)) {
                i--;
                continue;
            }
            if (statusEffects.get(i) instanceof GenerativeEffect) {
                generativeEffects.add((GenerativeEffect) statusEffects.get(i));
            }
        }

        GenerativeEffect.calculateCombinedTotal(this, generativeEffects);
    }

    public void checkRemoveStatusEffects(RemoveCondition condition) {

        statusEffects.removeIf(se -> se.checkRemove(condition, this));
    }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    public void addGenerative(GenerativeEffect effect) {

        if (effect instanceof HealthGenerative) {
            generatives.get(0).add(effect);
        } else if (effect instanceof ManaGenerative) {
            generatives.get(1).add(effect);
        }
    }

    public void removeStatusEffect(StatusEffect effect) {
        this.statusEffects.remove(effect);
    }

    public void removeGenerative(GenerativeEffect effect) {

    }

    // Stat Accessors and Mutators

    public int getBaseStat(String prefix) {
        return this.stats.getBaseStat(prefix);
    }

    public int getCurStat(String prefix) {
        if (prefix.equalsIgnoreCase("hlh")) {
            return stats.getCurHlh(this);
        }
        return stats.getCurStat(prefix, this.statusEffects);
    }

    public int getHlh() {
        return this.stats.getHlh();
    }

    public int getMan() {
        return this.stats.getMan();
    }

    public void setHlh(int hlh) {
        this.stats.setHlh(hlh);
    }

    public void setMan(int man) {
        this.stats.setMan(man);
    }

    public void incrementHlh(int hlh) {
        this.stats.incrementHlh(hlh);
    }

    public void incrementMan(int man) {
        this.stats.incrementMan(man);
    }

    // Battle Accessors and Mutators

    public int getTargetIndex() {
        return this.targetIndex;
    }

    public Action getCurAction() {
        return curAction;
    }

    public ArrayList<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    public void setCurAction(Action curAction) {
        this.curAction = curAction;
    }

    public int getRewardXp() {
        return this.rewardXp;
    }


    // Other Accessors and Mutators

    public String getName() {
        return this.name;
    }

    // One day, a getPossessivePronoun() and similar will be needed
    public String getPossessiveName() {
        if (this.name.endsWith("s")) {
            return this.name + "'";
        }
        return this.name + "'s";
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setControllable(boolean controllable) {
        this.controllable = controllable;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hlh=" + this.stats.getHlh() +
                ", debug=" + debug +
                ", currentAction=" + curAction +
                ", statusEffects=" + statusEffects +
                '}';
    }
}