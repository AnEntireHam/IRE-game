package com.ire.entities;

import com.ire.combat.statuseffects.StatusEffect;
import com.ire.combat.statuseffects.stateffects.StatEffect;
import com.ire.tools.PrintControl;

import java.io.Serializable;
import java.util.ArrayList;

public class StatHelper implements Serializable {

    private static final long serialVersionUID = 4182021L;
    private int baseHlh, baseAtk, baseDef, baseMag, baseSpd;
    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "unused"})
    private int curHlh,  curAtk,  curDef,  curMag,  curSpd;
    private int hlh, man;

    public StatHelper(int baseHlh, int baseAtk, int baseDef, int baseMag, int baseSpd) {
        this.baseHlh = baseHlh;
        this.baseAtk = baseAtk;
        this.baseDef = baseDef;
        this.baseMag = baseMag;
        this.baseSpd = baseSpd;

        this.curHlh = baseHlh;
        this.curAtk = baseAtk;
        this.curDef = baseDef;
        this.curMag = baseMag;
        this.curSpd = baseSpd;

        this.hlh = curHlh;
        this.man = curMag;
    }

    public int getBaseStat(String prefix) {
        switch (prefix.toLowerCase()) {
            case "hlh":
                return this.baseHlh;
            case "atk":
                return this.baseAtk;
            case "def":
                return this.baseDef;
            case "mag":
                return this.baseMag;
            case "spd":
                return this.baseSpd;
            default:
                throw new IllegalArgumentException("Unexpected prefix: " + prefix.toLowerCase());
        }
    }

    public int getBaseStat(int index) {
        String prefix;
        switch (index) {
            case 0:
                prefix = "hlh";
                break;
            case 1:
                prefix = "atk";
                break;
            case 2:
                prefix = "def";
                break;
            case 3:
                prefix = "mag";
                break;
            case 4:
                prefix = "spd";
                break;
            default:
                throw new IllegalArgumentException("Unexpected prefix: " + index);
        }
        return getBaseStat(prefix);
    }

    public void incrementBaseStat(String prefix, int increment) {
        switch (prefix.toLowerCase()) {
            case "hlh":
                this.baseHlh += increment;
                break;
            case "atk":
                this.baseAtk += increment;
                break;
            case "def":
                this.baseDef += increment;
                break;
            case "mag":
                this.baseMag += increment;
                break;
            case "spd":
                this.baseSpd += increment;
                break;
            default:
                throw new IllegalArgumentException("Illegal stat prefix: " + prefix.toLowerCase());
        }
    }

    public void incrementBaseStat(String prefix) {
        incrementBaseStat(prefix, 1);
    }

    public void setBaseStat(String prefix, int stat) {
        switch (prefix.toLowerCase()) {
            case "hlh":
                this.baseHlh = stat;
                break;
            case "atk":
                this.baseAtk = stat;
                break;
            case "def":
                this.baseDef = stat;
                break;
            case "mag":
                this.baseMag = stat;
                break;
            case "spd":
                this.baseSpd = stat;
                break;
            default:
                throw new IllegalArgumentException("Unexpected prefix: " + prefix.toLowerCase());
        }
    }

    public int getCurStat(String prefix, ArrayList<StatusEffect> effects) {
        float multiplier = calculateMultiplier(prefix, effects);
        int stat;
        switch (prefix.toLowerCase()) {
            case "atk":
                stat = this.baseAtk;
                break;
            case "def":
                stat = this.baseDef;
                break;
            case "mag":
                stat = this.baseMag;
                break;
            case "spd":
                stat = this.baseSpd;
                break;
            default:
                throw new IllegalArgumentException("Unexpected prefix: " + prefix.toLowerCase());
        }
        return Math.round(stat * multiplier);
    }

    public int getCurHlh(Entity e) {
        int prevMax = this.curHlh;
        this.curHlh = Math.round(this.baseHlh * calculateMultiplier("hlh", e.getStatusEffects()));

        if (prevMax != this.curHlh && this.hlh > this.curHlh) {
            System.out.println(this.hlh + " " + this.curHlh);
            int damage = this.hlh - this.curHlh;
            e.takeDamage(damage, false);
            System.out.println(e.getName() + " took " + damage + " damage from lowered maximum health.");
            PrintControl.sleep(2000);
        }
        return this.curHlh;
    }

    private float calculateMultiplier(String prefix, ArrayList<StatusEffect> statusEffects) {
        float multiplier = 1.0f;
        for (StatusEffect se: statusEffects) {
            //  Consider StatEffects which affect multiple stats
            if (se instanceof StatEffect && se.getAbbreviation().equalsIgnoreCase(prefix)) {
                multiplier += (Math.round(((StatEffect) se).getStatMultiplier() * 100f) / 100f);
            }
        }
        return multiplier;
    }

    public int getHlh() {
        return hlh;
    }

    public int getMan() {
        return man;
    }

    public void setHlh(int hlh) {
        this.hlh = hlh;
    }

    public void setMan(int man) {
        this.man = man;
    }

    public void incrementHlh(int hlh) {
        this.hlh += hlh;
    }

    public void incrementMan(int man) {
        this.man += man;
    }

}
