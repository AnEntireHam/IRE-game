package com.ire.combat.statuseffects.generativeeffect;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GenerativeManager implements Serializable {

    ArrayList<GenerativeEffect> generativeEffects;
    ArrayList<GenerativeEffect> regenEffects;
    ArrayList<GenerativeEffect> bleedEffects;

    /*
    shortest/longest

    S "3|3|1"
    D "2|4|5"

    [bl1, bl2, bl3]
    */

    public GenerativeManager() {

        generativeEffects = new ArrayList<>();
        regenEffects = new ArrayList<>();
        bleedEffects = new ArrayList<>();
    }

    public abstract void execute();
    public abstract void add(GenerativeEffect ge);
    public abstract void remove(GenerativeEffect ge);

    public String generateDisplay() {
        return (generateEffectDisplay(regenEffects) + " " + generateEffectDisplay(bleedEffects));
    }

    public String generateEffectDisplay(ArrayList<GenerativeEffect> effects) {

        if (effects.size() == 0) { return ""; }

        StringBuilder strengths = new StringBuilder();
        StringBuilder durations = new StringBuilder();
        String type = effects.get(0).getName();
        int stacks = 0;

        for (int i = 0; i < effects.size(); i++) {

            GenerativeEffect e = effects.get(i);

            strengths.append(Math.abs(Math.round(
                    (float) e.getStrength() / (i + 1))));
            stacks++;
            durations.append(e.getDuration());

            if (i < effects.size() - 1) {
                strengths.append("|");
                durations.append("|");
            }
        }

        return (type + ": (" + strengths +
                "), (" + durations + ") t, " + stacks + " s.  ");
    }

    protected int calculateCombinedTotal() {
        return (calculateEffectTotal(regenEffects) + calculateEffectTotal(bleedEffects));
    }

    protected int calculateEffectTotal(ArrayList<GenerativeEffect> effects) {

        int total = 0;
        int count = 0;

        for (GenerativeEffect ge: effects) {
            count++;
            total += Math.round((float) ge.getStrength() / count);
        }

        return total;
    }
}
