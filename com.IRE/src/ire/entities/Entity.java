package ire.entities;

import ire.audio.AudioStream;
import ire.combat.BattleEffect;
import ire.combat.actions.Action;
import ire.combat.actions.attackactions.physicalattacks.Lunge;
import ire.combat.actions.attackactions.physicalattacks.Stab;
import ire.combat.actions.attackactions.spellattacks.SpellAttack;
import ire.combat.actions.defenseactions.physicaldefenses.Counter;
import ire.combat.actions.defenseactions.physicaldefenses.Shield;
import ire.combat.actions.defenseactions.spelldefenses.SpellDefense;
import ire.combat.statuseffects.StatusEffect;
import ire.combat.statuseffects.stateffects.StatEffect;
import ire.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Entity {

    // ***********************************
    // Fields
    // ***********************************

    public final BattleEffect bEffects;

    protected String name;
    protected int level;
    protected int baseHlh, curHlh, hlh;
    protected int baseAtk, curAtk;
    protected int baseDef, curDef;
    protected int baseMag, curMag, man;
    protected int baseSpd, curSpd;
    protected boolean debug;
    protected boolean alive;

    // May change spelling/phrasing of counterspell (damper/stymie/stifle/smother)
    // and Technique (expertise/mastery/panache/style/ability/skill/craft).
    protected ArrayList<String> attacks = new ArrayList<>(Arrays.asList("Stab", "Lunge"));
    protected ArrayList<String> defenses = new ArrayList<>(Arrays.asList("Shield", "Counter"));

    protected Stab stab = new Stab();
    protected Lunge lunge = new Lunge();
    protected Shield shield = new Shield();
    protected Counter counter = new Counter();

    // Eventually equippedSpells/equippedX will be needed. Cross this bridge later.
    protected ArrayList<SpellAttack> spells = new ArrayList<>();
    protected ArrayList<SpellDefense> wards = new ArrayList<>();


    // Discrepancy in implementation of defenseChoice in regards to other fields.
    // I think this is because there isn't any allDefenses or allDefenseDescriptions, or any menu.
    protected int targetIndex;
    protected Action currentAction;

    // These may need to be handled differently. For now assume one true "techniques" list.

    // protected ArrayList<String> techs = new ArrayList<>();


    // the NEW implementation babey!!
    protected ArrayList<StatusEffect> statusEffects = new ArrayList<>();

    /*// ON JAH why did you do this to yourself, you heathen, you pleb, you jester, you clown, you circus!?
    protected int[] buffStrengths = {0, 0, 0, 0, 0, 0, 0, 0};
    protected int[] buffDurations = {0, 0, 0, 0, 0, 0, 0, 0};
    protected int[] buffStacks =    {1, 1, 1, 1, 1};
    protected int newBuff = 0;*/

    protected AudioStream deathSound;
    protected static AudioStream hitSound = new AudioStream("hit1");
    protected static AudioStream strikeSound = new AudioStream("strike1");
    protected static AudioStream healSound = new AudioStream("leech");


    // Unimplemented/legacy fields

    //protected boolean playable;
    //protected boolean channelDef;
    //protected String[] passSkill = {"", "", ""};


    // ***********************************
    // Constructors
    // ***********************************

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

        //
        //  USE THE WORD "SUNDER", "BAP" somewhere PLEASE
        //

        this.deathSound = new AudioStream(deathSound);

        this.hlh = this.curHlh;
        this.man = this.curMag;

        this.bEffects = new BattleEffect(this);

    }

    // ***********************************
    // Misc Functions
    // ***********************************

    protected abstract void levelUp(int targetLevel);

    public String generateStatus() {

        //  Fix health bar for dead men.
        StringBuffer output = new StringBuffer();

        //  If J8 supports this, convert later.
        /*for (int j = this.name.length(); j < 15; j++) {
            output.append(" ");
        }*/

        int quotient = (int) (Math.round(((double) this.hlh / this.curHlh) * 20));

        for (int i = 0; i < quotient; i++) {
            output.append("█");
        }

        for (int i = 0; i < 20 - quotient; i++) {
            output.append("░");
        }

        output.append("  ");

        String[] stats = {"Hlh: ", "Atk: ", "Def: ", "Mag: " + "Spd: "};
        float[] multiplier = {1, 1, 1, 1, 1};
        StringBuilder others = new StringBuilder();

        for (StatusEffect se: this.statusEffects) {

            if (se instanceof StatEffect && se.isDisplay()) {
                switch (se.getAbbreviation()) {
                    case "HLH":
                        multiplier[0] += ((StatEffect) se).getStatMultiplier();
                        break;
                    case "ATK":
                        multiplier[1] += ((StatEffect) se).getStatMultiplier();
                        break;
                    case "DEF":
                        multiplier[2] += ((StatEffect) se).getStatMultiplier();
                        break;
                    case "MAG":
                        multiplier[3] += ((StatEffect) se).getStatMultiplier();
                        break;
                    case "SPD":
                        multiplier[4] += ((StatEffect) se).getStatMultiplier();
                        break;
                }

            } else if (se.isDisplay() && !se.isPercentage()) {
                others.append(se.getAbbreviation()).append(" placeholder").append(" ");
            }
        }
        for (int i = 0; i < 4; i++) {
            multiplier[i] = Math.round(multiplier[i] * 100f);
            if (multiplier[i] != 100) {
                output.append(stats[i]).append((int) multiplier[i]).append("% ");
            }
        }
        output.append(others);

        return output.toString();
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


    // ***********************************
    // Attack and Defense Functions
    // ***********************************

    public abstract void promptAttack(ArrayList<Entity> enemies);
    public abstract void promptDefend();
    protected abstract boolean promptTargetIndex(ArrayList<Entity> enemies);

    public void incrementStatusDurations() {
        for (int i = 0; i < statusEffects.size(); i++) {
            if (statusEffects.get(i).incrementEffect(this)) {
                i--;
            }
        }
        int manaRegen = (int) Math.round((this.curMag / 4.0));
        this.man += manaRegen;
        if (this.man > this.curMag) {this.man = this.curMag;}  //  THIS IS SHOEHORNED IN AND BUG-PRONE. MOVE LATER
    }

    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    public void addAttack(String attack) {
        attacks.add(attack);
    }

    public void addDefense(String defense) {
        defenses.add(defense);
    }

    public void removeStatusEffect(StatusEffect effect) {
        this.statusEffects.remove(effect);
    }

    public void removeAttack(int index) {
        attacks.remove(index);
    }

    public void removeDefense(int index) {
        defenses.remove(index);
    }

    // ***********************************
    // curStat and Attack Calculations
    // ***********************************

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

    // ***********************************
    // Stat Accessors and Mutators
    // ***********************************

    public int getStat(int index) {

        return switch (index) {
            case 0 -> getBaseHlh();
            case 1 -> getBaseAtk();
            case 2 -> getBaseDef();
            case 3 -> getBaseMag();
            case 4 -> getBaseSpd();
            case 5 -> getCurHlh();
            case 6 -> getCurAtk();
            case 7 -> getCurDef();
            case 8 -> getCurMag();
            case 9 -> getCurSpd();
            case 10 -> getHlh();
            case 11 -> getMan();
            default -> throw new IllegalArgumentException("Unexpected value: " + index);
        };
    }

    public int getBaseStat(String prefix) {

        return switch (prefix.toLowerCase()) {
          case "hlh" -> getBaseHlh();
          case "atk" -> getBaseAtk();
          case "def" -> getBaseDef();
          case "mag" -> getBaseMag();
          case "spd" -> getBaseSpd();
          default -> throw new IllegalArgumentException("Unexpected value: " + prefix.toLowerCase());
        };
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
            this.bEffects.takeDamage(damage, false);
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
            case 0 -> setBaseHlh(strength);
            case 1 -> setBaseAtk(strength);
            case 2 -> setBaseDef(strength);
            case 3 -> setBaseMag(strength);
            case 4 -> setBaseSpd(strength);
            case 5 -> setCurHlh(strength);
            case 6 -> setCurAtk(strength);
            case 7 -> setCurDef(strength);
            case 8 -> setCurMag(strength);
            case 9 -> setCurSpd(strength);
            case 10 -> setHlh(strength);
            case 11 -> setMan(strength);
            default -> throw new IllegalArgumentException("Unexpected value: " + index);
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

    // ***********************************
    // Battle Accessors and Mutators
    // ***********************************

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

    /*public int getNewBuff() {
        return this.newBuff;
    }

    public void setNewBuff(int newBuff) {
        this.newBuff = newBuff;
    }

    public int getBuffDurations() {
        return this.buffDurations[this.newBuff];
    }

    public void setBuffDurations(int duration) {
        this.buffDurations[this.newBuff] = duration;
    }

    public int getBuffStrengths() {
        return this.buffStrengths[this.newBuff];
    }

    public void setBuffStrengths(int strengths) {
        this.buffStrengths[this.newBuff] = strengths;
    }

    public int getBuffStacks() {
        return this.buffStacks[this.newBuff];
    }

    public void setBuffStacks(int stacks) {
        this.buffStacks[this.newBuff] = stacks;
    }*/

    // ***********************************
    // Other Accessors and Mutators
    // ***********************************


    public String getName() {
        return this.name;
    }

    public boolean isAlive() {
        return this.alive;
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

    @Override
    public String toString() {
        return ("Name: " + name + "  Level: " + level + "  Health: " + hlh + "/" + curHlh +
                "  Attack: " + curAtk + "  Defense: " + curDef + "  Magic: " + curMag + "  Speed: " + curSpd);
    }

    // Unimplemented/legacy accessors

    /*public boolean isPlayable() {
        return this.playable;
    }*/

    /*public boolean isChannelDef() {
        return this.channelDef;
    }

    public void setChannelDef(boolean channelDef) {
        this.channelDef = channelDef;
    }*/
}