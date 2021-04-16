package com.ire.combat.statuseffects.generativeeffect;

import com.ire.combat.statuseffects.RemoveCondition;
import com.ire.combat.statuseffects.StatusEffect;
import com.ire.entities.Entity;
import com.ire.tools.PrintControl;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

public abstract class GenerativeEffect extends StatusEffect {

    protected int strength;
    protected int effectLevel;
    protected float baseProbability;
    protected float levelProbability;

    protected float strengthCoefficient = 3;
    protected float statProbability = 0;
    protected String expirationMessage;
    // TODO: Handle this with manager later.
    protected boolean original = true;

    //  It might make more sense to change the strengthCoefficient instead of the levelProbability.
    public GenerativeEffect(String name, String abbreviation, String description,
                            int stacks, int duration, RemoveCondition[] removeConditions, int effectLevel,
                            float baseProbability, float levelProbability, String expirationMessage) {

        super(name, abbreviation, description, stacks, duration, removeConditions);

        this.effectLevel = effectLevel;
        this.baseProbability = baseProbability;
        this.levelProbability = levelProbability;
        this.expirationMessage = expirationMessage;
    }

    @Override
    public void apply(Entity attacker, Entity defender) {

        boolean success = false;

        if (calculateProbability()) {

            defender.addGenerative(this);
            success = true;
        }

        displayResult(defender.getName(), success, original);
        if (attacker.isDebug()) {
            System.out.println("Strength " + strength);
        }
    }

    protected boolean calculateProbability() {

        double rand = Math.random();
        float effectProbability = (baseProbability + levelProbability * (effectLevel - 1));

        return rand <= (effectProbability + statProbability);
    }

    @Override
    protected void printRemoveMessage(RemoveCondition condition, Entity target) {

        switch (condition) {

            default:
            case EXPIRATION:
                Formatter parser = new Formatter();
                System.out.println(parser.format(expirationMessage, target.getName()));
                PrintControl.sleep(1250);
                break;

            case DEATH:
                System.out.println(target.getPossessiveName() + " " + name + " faded.");
                PrintControl.sleep(1250);
                break;

            case END_BATTLE:
                if (target.isAlive()) {
                    System.out.println(target.getName() + " removed \"" + name + "\" from themself.");
                    PrintControl.sleep(1250);
                }
                break;

            case LEVEL_UP:
            case TAKE_DAMAGE:
                break;
        }
    }

    @Override
    public String generateDisplay() {

        // TODO: temporary format. probably include a shorter/longer version, settable by global options.
        return (name + ": " + Math.abs(strength) + ", " + duration + " t, " + stacks + " s.  ");
    }

    public static void calculateCombinedTotal(Entity target, ArrayList<GenerativeEffect> generativeEffects) {
        
        ArrayList<String> finishedAbbreviations = new ArrayList<>();
        HashMap<String, Integer> sums;
        
        sums = calculateSums(generativeEffects);

        for (GenerativeEffect ge: generativeEffects) {

            if (!finishedAbbreviations.contains(ge.getAbbreviation())) {
                finishedAbbreviations.add(
                        ge.getAbbreviation());
                ge.execute(
                        target, sums.get(ge.getAbbreviation()));
            }
        }
    }
    
    private static HashMap<String, Integer> calculateSums(ArrayList<GenerativeEffect> generativeEffects) {
        
        HashMap<String, Integer> sums = new HashMap<>();
        
        for (GenerativeEffect ge: generativeEffects) {

            String abbreviation = ge.getAbbreviation();

            if (!sums.containsKey(abbreviation)) {
                sums.put(abbreviation, ge.getStrength());
                continue;
            }
            
            int temp = sums.get(abbreviation) + ge.getStrength();
            sums.put(abbreviation, temp);
        }
        return sums;
    }

    public abstract void execute(Entity target, int total);

    protected abstract void displayResult(String defender, boolean success, boolean original);

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void incrementStrength(int strength) {

        this.strength += strength;
        /*if (this.strength < 1) {
            remove(target);
            //  Possibly include text indicated that GenerativeEffect has stopped due to < 1 strength.
        }*/
    }

    @Override
    public String toString() {
        return "GenerativeEffect{" +
                "strength=" + strength +
                ", effectLevel=" + effectLevel +
                "} " + super.toString();
    }
}
