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
import com.ire.combat.statuseffects.stateffects.StatEffect;
import com.ire.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public abstract class Entity {

    // Fields
    // TODO: This class is big. There are probably some fields and methods that can be extracted out.

    protected String name;
    protected int level;
    protected int baseHlh, curHlh, hlh;
    protected int baseAtk, curAtk;
    protected int baseDef, curDef;
    protected int baseMag, curMag, man;
    protected int baseSpd, curSpd;
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

    // protected String[] passSkill = {"", "", ""};


    // Constructor

    public Entity(int level, int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                  String name, String deathSound, boolean controllable) {

        this.level = 1;
        this.baseHlh = baseHlh;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseMag = baseMag;
        this.baseSpd = baseSpd;
        this.name = name;
        this.alive = true;
        this.controllable = controllable;

        this.curHlh = baseHlh;
        this.curAtk = baseAtk;
        this.curDef = baseDef;
        this.curMag = baseMag;
        this.curSpd = baseSpd;

        this.hlh = this.getCurHlh();
        this.man = this.getCurMag();
        //  USE THE WORD "SUNDER", "BAP" somewhere PLEASE

        this.deathSound = new AudioClip(deathSound);

        this.totalXp = calculateNextXp(this.level);
        this.nextXp = calculateNextXp(this.level + 1);
        this.rewardXp = 0;

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
        if (!Tools.isBotClient()) {
            Attribute[] colors = new Attribute[]{TEXT_COLOR(100, 165, 55), TEXT_COLOR(230, 175, 20)};
            output.append(Tools.createColoredBar(this.getHlh(), this.getCurHlh(), 20, colors))
                    .append("  ");
        } else {
            output.append(Tools.createBar(this.getHlh(), this.getCurHlh(), 20)).append("  ");
        }

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

        output.append("Hlh: ").append(getHlh()).append("/").append(getCurHlh())
                .append("  Atk: ").append(getCurAtk())
                .append("  Def: ").append(getCurDef());

        if (!spells.isEmpty()) {
            output.append("  Mag: ").append(getMan()).append("/").append(getCurMag());
        } else {
            output.append(" Mag: ").append(getCurMag());
        }

        output.append("  Spd: ").append(getCurSpd());

        return output.toString();
    }


    // Healing and Damage Methods


    public void fullHeal(RemoveCondition condition) {

        if (condition != RemoveCondition.LEVEL_UP) {
            System.out.println(name + " fully healed!");
        }
        this.checkRemoveStatusEffects(condition);
        recalculateCurStats();
        resetPointStats();
    }

    public void recalculateCurStats() {

        getCurHlh();
        getCurAtk();
        getCurDef();
        getCurMag();
        getCurSpd();
    }

    public void resetPointStats() {

        regenerateHealth(this.getCurHlh(), false, false);
        regenerateMana(this.getCurMag(), false, false);

        this.hlh = this.getCurHlh();
        this.man = this.getCurMag();

        if (!this.isAlive()) {
            this.revive(true);
        }
    }

    public void regenerateHealth(int regenStrength, boolean message, boolean surplus) {

        if ((this.hlh + regenStrength) > this.getCurHlh()) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " healed beyond the limit for " + regenStrength + " health.");
                    HEAL_SOUND.play();  // Beyond-limit sfx
                }

                this.hlh += regenStrength;
                if (!this.isAlive()) {
                    this.revive(true);
                }

            } else if (this.hlh < this.getCurHlh()) {
                if (message) {
                    System.out.println(name + " healed for " + (this.getCurHlh() - this.hlh) + " health.");
                    HEAL_SOUND.play();
                }
                this.hlh = this.getCurHlh();
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
            this.hlh += regenStrength;
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
            Tools.sleep(2000);
            System.out.println();
        }
    }

    public void takeDamage(int damage, boolean showMessage) {

        if (damage <= 0 && showMessage) {

            printDamageMessage(name + " was struck, but took no damage.");
            return;
        }

        if (alive) {

            this.hlh -= damage;
            if (showMessage) {
                printDamageMessage(name + " took " + damage + " damage.");
            }
            if (hlh < 1 && alive) {
                this.die(true);
            }
            return;
        }

        this.hlh -= damage;
        if (showMessage) {
            printDamageMessage(name + " is dead, but took " + damage + " more damage.");
        }
    }

    private void printDamageMessage(String message) {

        System.out.println(message);
        Tools.sleep(2000);
        System.out.println();
    }

    public void bleedMana(int bleedStrength, boolean message, boolean surplus) {

        if ((man - bleedStrength) < 0) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " is being drained of " + bleedStrength);
                    // mana loss sound
                }
                this.man -= bleedStrength;

            } else if (man < bleedStrength) {
                if (message) {
                    System.out.println(name + " lost " + man + " mana.");
                    // mana loss sound?
                }
                this.man = 0;

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
            this.man -= bleedStrength;

        } else {

            if (message) {
                System.out.println(name + " resisted losing mana.");
            }
        }

        if (message) {
            Tools.sleep(2000);
            System.out.println();
        }
    }

    public void regenerateMana(int regenStrength, boolean showMessage, boolean excess) {

        if ((man + regenStrength) > getCurMag()) {
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
            this.man += regenStrength;
            return;
        }

        if (man < getCurMag()) {
            if (showMessage) {
                printRegenMessage(name + " regenerated " + (getCurMag() - man) + " mana");
            }
            man = getCurMag();
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
        this.man += regenStrength;
    }

    // TODO: Add sound as a parameter
    private void printRegenMessage(String message) {

        System.out.println(message);
        Tools.sleep(2000);
        System.out.println();
    }

    // TODO: Remove defensive bonuses when dead or "useless" defense.
    public void die(boolean message) {

        this.alive = false;

        if (message) {
            this.deathSound.play();
            System.out.println(name + " has died.");
            Tools.sleep(1500);
            System.out.println();
        }

        this.checkRemoveStatusEffects(RemoveCondition.DEATH);
        // add coffin dance gif for party wipe in defiled mode?
    }

    public void revive(boolean message) {

        this.alive = true;

        if (message) {
            // TODO: Add great heal or revive sfx
            HEAL_SOUND.play();
            System.out.println(name + " was resurrected.");
            Tools.sleep(1500);
            System.out.println();
        }
    }


    // Attack and Defense Methods

    public void promptAttack(ArrayList<Entity> targets) {

        int choice;
        boolean confirmed = false;

        while (!confirmed) {

            choice = generateActionChoice(attacks, "attacking");

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
                        choice = SpellAttack.menu(spells, man, this.getCurMag(), this.controllable);

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

            choice = generateActionChoice(defenses, "defending");

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
            Tools.clear();
            System.out.println("Enemies are " + verb + ". Select an action.");
            System.out.println("\n" + this.generateBattleStatus(true) + "\n");
            return Tools.menu(options);
        }
        Random rand = new Random();
        return rand.nextInt(options.size()) + 1;
    }

    protected boolean promptTargetIndex(ArrayList<Entity> targets) {

        ArrayList<String> options = new ArrayList<>();
        int choice;

        Tools.sortEntityList(targets);

        for (Entity t : targets) {
            if (t.isAlive()) {
                options.add(t.generateBattleStatus(true));
            }
        }

        if (this.controllable) {
            System.out.println("Select a target.");
            choice = Tools.cancelableMenu(options) - 1;
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

        int manaRegen = (int) Math.round((this.curMag / 4.0));
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

    protected float calculateMultiplier(String prefix) {

        float multiplier = 1.0f;

        for (StatusEffect se: this.statusEffects) {
            //  Consider StatEffects which affect multiple stats
            if (se instanceof StatEffect && se.getAbbreviation().equals(prefix)) {
                multiplier += (Math.round(((StatEffect) se).getStatMultiplier() * 100f) / 100f);
            }
        }

        return multiplier;
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

    protected int getStat(int index) {

        switch (index) {
            case 0:
                return getBaseHlh();
            case 1:
                return getBaseAtk();
            case 2:
                return getBaseDef();
            case 3:
                return getBaseMag();
            case 4:
                return getBaseSpd();
            case 5:
                return getCurHlh();
            case 6:
                return getCurAtk();
            case 7:
                return getCurDef();
            case 8:
                return getCurMag();
            case 9:
                return getCurSpd();
            case 10:
                return getHlh();
            case 11:
                return getMan();
            default:
                throw new IllegalArgumentException("Unexpected value: " + index);
        }
    }

    public int getBaseStat(String prefix) {

        switch (prefix.toLowerCase()) {
            case "hlh":
                return getBaseHlh();
            case "atk":
                return getBaseAtk();
            case "def":
                return getBaseDef();
            case "mag":
                return getBaseMag();
            case "spd":
                return getBaseSpd();
            default:
                throw new IllegalArgumentException("Unexpected value: " + prefix.toLowerCase());
        }
    }

    public int getBaseHlh() {
        return this.baseHlh;
    }

    public int getBaseAtk() {
        return this.baseAtk;
    }

    public int getBaseDef() {
        return this.baseDef;
    }

    public int getBaseMag() {
        return baseMag;
    }

    public int getBaseSpd() {
        return baseSpd;
    }

    public int getCurHlh() {

        int prevMax = this.curHlh;
        this.curHlh = Math.round(this.baseHlh * calculateMultiplier("HLH"));

        if (prevMax != this.curHlh && this.hlh > this.curHlh) {
            System.out.println(this.hlh + " " + this.curHlh);
            int damage = this.hlh - this.curHlh;
            this.takeDamage(damage, false);
            System.out.println(this.name + " took " + damage + " damage from lowered maximum health.");
            Tools.sleep(2000);
        }

        return this.curHlh;
    }

    public int getCurAtk() {
        this.curAtk = Math.round(this.baseAtk * calculateMultiplier("ATK"));
        return this.curAtk;
    }

    public int getCurDef() {
        this.curDef = Math.round(this.baseDef * calculateMultiplier("DEF"));
        return this.curDef;
    }

    public int getCurMag() {
        this.curMag = Math.round(this.baseMag * calculateMultiplier("MAG"));
        return this.curMag;
    }

    public int getCurSpd() {
        this.curSpd = Math.round(this.baseSpd * calculateMultiplier("SPD"));
        return this.curSpd;
    }

    public int getHlh() {
        return this.hlh;
    }

    public int getMan() {
        return this.man;
    }


    public void setHlh(int hlh) {
        this.hlh = hlh;
    }

    public void setMan(int man) {
        this.man = man;
    }

    public void incrementMan(int man) {
        this.man += man;
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

    public AudioClip getDeathSound() {
        return deathSound;
    }

    public StatusEffect getStatusEffect(int index) {
        return this.statusEffects.get(index);
    }

    public void setCurAction(Action curAction) {
        this.curAction = curAction;
    }

    public int getRewardXp() {
        return this.rewardXp;
    }


    // Other Accessors and Mutators

    // TODO: Add "getPossessiveName" method, add where appropriate.
    public String getName() {
        return this.name;
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

    /*@Override
    public String toString() {
        return ("Entity: " + "Name: " + name + "  Level: " + level + "  Health: " + hlh + "/" + curHlh +
                "  Attack: " + curAtk + "  Defense: " + curDef + "  Magic: " + curMag + "  Speed: " + curSpd);
    }*/

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", hlh=" + hlh +
                ", debug=" + debug +
                ", currentAction=" + curAction +
                ", statusEffects=" + statusEffects +
                '}';
    }
}