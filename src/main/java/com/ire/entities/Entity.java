package com.ire.entities;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioStream;
import com.ire.combat.actions.Action;
import com.ire.combat.actions.attackactions.physicalattacks.Lunge;
import com.ire.combat.actions.attackactions.physicalattacks.Stab;
import com.ire.combat.actions.attackactions.spellattacks.SpellAttack;
import com.ire.combat.actions.defenseactions.physicaldefenses.Counter;
import com.ire.combat.actions.defenseactions.physicaldefenses.Shield;
import com.ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import com.ire.combat.statuseffects.StatusEffect;
import com.ire.combat.statuseffects.generativeeffect.GenerativeEffect;
import com.ire.combat.statuseffects.stateffects.StatEffect;
import com.ire.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public abstract class Entity {


    // Fields
    // TODO: This class is way too big. There are definitely numerous fields and methods that can be extracted out.

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

    // TODO: Consider having separate classes for these lists, offload add/remove methods.
    // TODO: These almost certainly shouldn't be string objects.
    protected ArrayList<String> attacks = new ArrayList<>(Arrays.asList("Stab", "Lunge"));
    protected ArrayList<String> defenses = new ArrayList<>(Arrays.asList("Shield", "Counter"));
    // Eventually equippedSpells/equippedX will be needed. Cross this bridge later.
    protected ArrayList<SpellAttack> spells = new ArrayList<>();
    protected ArrayList<SpellDefense> wards = new ArrayList<>();
    // These may need to be handled differently. For now assume one true "techniques" list.
    // protected ArrayList<Technique> techs = new ArrayList<>();

    protected ArrayList<StatusEffect> statusEffects = new ArrayList<>();

    protected Stab stab = new Stab();
    protected Lunge lunge = new Lunge();
    protected Shield shield = new Shield();
    protected Counter counter = new Counter();


    protected int targetIndex;

    // Naming convention discrepancy between "curStat" and "currentAction".
    protected Action currentAction;


    protected AudioStream deathSound;
    protected static AudioStream hitSound = new AudioStream("hit1");
    protected static AudioStream strikeSound = new AudioStream("strike1");
    protected static AudioStream healSound = new AudioStream("leech");

    // protected String[] passSkill = {"", "", ""};


    // Constructor

    public Entity(int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd,
                  String name, String deathSound) {

        this.level = 1;
        this.baseHlh = baseHlh;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseMag = baseMag;
        this.baseSpd = baseSpd;
        this.name = name;
        this.alive = true;

        this.curHlh = baseHlh;
        this.curAtk = baseAtk;
        this.curDef = baseDef;
        this.curMag = baseMag;
        this.curSpd = baseSpd;

        //  USE THE WORD "SUNDER", "BAP" somewhere PLEASE

        this.deathSound = new AudioStream(deathSound);

        this.hlh = this.curHlh;
        this.man = this.curMag;

        this.totalXp = calculateNextXp(this.level);
        this.nextXp = calculateNextXp(this.level + 1);
        this.rewardXp = 0;
    }


    // Level and Xp Methods

    protected abstract void levelUp(int targetLevel);

    public void addXp(int xp) {

        if (xp < 0) {
            throw new IllegalArgumentException("Xp must be a positive number");
        }

        this.totalXp += xp;

        while (this.totalXp >= this.nextXp) {
            this.levelUp(this.level + 1);
            this.nextXp = calculateNextXp(this.level + 1);
        }
    }

    protected int calculateNextXp(int targetLevel) {
        return (int) Math.pow(targetLevel, 3);
    }


    // generateX methods

    // Should be a part of battleHud(), but it doesn't necessarily have to be the entire part of enemy's display.
    public String generateBattleStatus(boolean detailed) {

        StringBuilder output = new StringBuilder();

        //  I'm unhappy with this consistent spacing, but it might be preferable in the future.
        output.append(name);
        output.append("  Lv. ").append(level).append("  ");
        for (int j = output.length(); j < 16; j++) {
            output.append(" ");
        }

        /*
         * Consider adding a "shields/armor" color rather than just "yellow".
         * Should a secondary bar be added for mana or other relevant info?
         * Length should probably be longer depending on the enemy/character... which ones, and when?
         */
        Attribute[] colors = new Attribute[] {TEXT_COLOR(100, 165, 55), TEXT_COLOR(230, 175, 20)};
        output.append(Tools.createColoredBar(this.getHlh(), this.getCurHlh(), 20, colors));

        for (StatusEffect se: statusEffects) {
            output.append(se.generateDisplay());
        }

        if (detailed) {
            output.append("  ").append(generateStats());
        }

        return output.toString();
    }

    public String generateStats() {

        StringBuilder output = new StringBuilder();

        output.append("Hlh: ").append(getHlh()).append("/").append(getCurHlh())
                .append("  Atk: ").append(getCurAtk())
                .append("  Def: ").append(getCurDef())
                .append("  Mag: ").append(getMan()).append("/").append(getCurMag())
                .append("  Spd: ").append(getCurSpd());

        if (!spells.isEmpty()) {
            output.append(" Man: ").append(getMan());
        }

        return output.toString();
    }


    // Healing and Damage Methods

    public void regenerateHealth(int regenStrength, boolean message, boolean surplus) {

        if ((this.hlh + regenStrength) > this.getCurHlh()) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " healed beyond the limit for " + regenStrength + " health.");
                    healSound.play();  // Beyond-limit sfx
                }

                this.hlh += regenStrength;

            } else if (this.hlh < this.getCurHlh()) {
                if (message) {
                    System.out.println(name + " healed for " + (this.getCurHlh() - this.hlh) + " health.");
                    healSound.play();
                }
                this.hlh = this.getCurHlh();

            } else {
                if (message) {
                    System.out.println(name + " was healed, but was already beyond full health.");
                    // heal error
                }
            }

        } else if (regenStrength > 0) {

            if (message) {
                System.out.println(name + " healed " + regenStrength + " health.");
                healSound.play();
            }
            this.hlh += regenStrength;

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

    public void takeDamage(int damage, boolean message) {

        if (damage <= 0 && message) {
            System.out.println(name + " was struck, but took no damage.");
            Tools.sleep(2000);
            System.out.println(" ");

        } else {

            if (alive) {

                this.hlh -= damage;
                if (message) {
                    System.out.println(name + " took " + damage + " damage.");
                    Tools.sleep(2000);
                    System.out.println(" ");
                }

                if (hlh < 1) {
                    this.die(true);
                }

            } else {

                this.hlh -= damage;
                if (message) {
                    System.out.println(name + " is dead, but took " + damage + " more damage.");
                    Tools.sleep(2000);
                    System.out.println(" ");
                }
            }
        }
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

    public void regenerateMana(int regenStrength, boolean message, boolean surplus) {

        if ((man + regenStrength) > getCurMag()) {

            if (surplus) {
                if (message) {
                    System.out.println(name + " over-regenerated, gaining " + regenStrength + " mana.");
                }
                this.man += regenStrength;

            } else if (man < getCurMag()) {
                if (message) {
                    System.out.println(name + " regenerated " + (getCurMag() - man) + " mana");
                    // mana regen sound
                }
                man = getCurMag();

            } else {
                if (message) {
                    System.out.println(name + " regenerated mana, but already had maximum mana.");
                    // mana regen error
                }
            }

        } else if (regenStrength > 0) {

            if (message) {
                System.out.println(name + " regenerated " + regenStrength + " mana.");
                // mana regen sound
            }
            this.man += regenStrength;

        } else {

            if (message) {
                System.out.println(name + " tried to regenerate mana, but failed.");
                // mana regen error
            }
        }

        if (message) {
            Tools.sleep(2000);
            System.out.println();
        }
    }

    public void die(boolean message) {

        this.alive = false;
        if (message) {
            this.deathSound.play();
            System.out.println(name + " has died.");
            Tools.sleep(1500);
            System.out.println(" ");
        }
        // add coffin dance gif for party wipe in defiled mode?
    }


    // Attack and Defense Methods

    public abstract void promptAttack(ArrayList<Entity> enemies);
    public abstract void promptDefend();
    protected abstract boolean promptTargetIndex(ArrayList<Entity> enemies);

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
        HashMap<String, Integer> sums = new HashMap<>();

        int manaRegen = (int) Math.round((this.curMag / 4.0));
        this.regenerateMana(manaRegen, false, false);

        for (int i = 0; i < statusEffects.size(); i++) {

            if (statusEffects.get(i).incrementEffect(this)) {
                i--;
            } else if (statusEffects.get(i) instanceof GenerativeEffect) {
                generativeEffects.add((GenerativeEffect) statusEffects.get(i));
            }
        }

        if (debug) {
            System.out.println("GEs: " + generativeEffects);
        }

        for (GenerativeEffect ge: generativeEffects) {
            String abbrev = ge.getAbbreviation().toLowerCase();

            if (!sums.containsKey(abbrev)) {
                sums.put(abbrev, ge.getStrength());

            } else {
                int temp = sums.get(abbrev) + ge.getStrength();
                sums.put(abbrev, temp);
            }
        }

        if (debug) {
            System.out.println("Sums: " + sums);
        }

        ArrayList<String> processedAbbrev = new ArrayList<>();

        for (GenerativeEffect ge: generativeEffects) {

            if (!processedAbbrev.contains(ge.getAbbreviation())) {
                ge.combineEffects(this, sums.get(ge.getAbbreviation().toLowerCase()));
                processedAbbrev.add(ge.getAbbreviation());
            }
        }
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

    public void recalculateCurStats() {

        getCurHlh();
        getCurAtk();
        getCurDef();
        getCurMag();
        getCurSpd();
    }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    public void removeStatusEffect(StatusEffect effect) {
        this.statusEffects.remove(effect);
    }

    // Stat Accessors and Mutators

    public int getCurStat(int index) {

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

    public void setStat(int index, int strength) {

        switch (index) {
            case 0:
                setBaseHlh(strength);
                break;
            case 1:
                setBaseAtk(strength);
                break;
            case 2:
                setBaseDef(strength);
                break;
            case 3:
                setBaseMag(strength);
                break;
            case 4:
                setBaseSpd(strength);
                break;
            case 5:
                setCurHlh(strength);
                break;
            case 6:
                setCurAtk(strength);
                break;
            case 7:
                setCurDef(strength);
                break;
            case 8:
                setCurMag(strength);
                break;
            case 9:
                setCurSpd(strength);
                break;
            case 10:
                setHlh(strength);
                break;
            case 11:
                setMan(strength);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + index);
        }
    }

    public void setBaseHlh(int baseHlh) {
        this.baseHlh = baseHlh;
    }

    public void setBaseAtk(int baseAtk) {
        this.baseAtk = baseAtk;
    }

    public void setBaseDef(int baseDef) {
        this.baseDef = baseDef;
    }

    public void setBaseMag(int baseMag) {
        this.baseMag = baseMag;
    }

    public void setBaseSpd(int baseSpd) {
        this.baseSpd = baseSpd;
    }

    public void setCurHlh(int curHlh) {
        this.curHlh = curHlh;
    }

    public void setCurAtk(int curAtk) {
        this.curAtk = curAtk;
    }

    public void setCurDef(int curDef) {
        this.curDef = curDef;
    }

    public void setCurMag(int curMag) {
        this.curMag = curMag;
    }

    public void setCurSpd(int curSpd) {
        this.curSpd = curSpd;
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

    public Action getCurrentAction() {
        return currentAction;
    }

    public ArrayList<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    // Probably move this elsewhere, or attack/strike SFX back here.
    public AudioStream getHealSound() {
        return healSound;
    }

    public AudioStream getDeathSound() {
        return deathSound;
    }

    public StatusEffect getStatusEffect(int index) {
        return this.statusEffects.get(index);
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
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

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean bool) {
        this.debug = bool;
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
                ", currentAction=" + currentAction +
                ", statusEffects=" + statusEffects +
                '}';
    }
}