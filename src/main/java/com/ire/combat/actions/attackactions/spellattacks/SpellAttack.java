package com.ire.combat.actions.attackactions.spellattacks;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioStream;
import com.ire.tools.Tools;
import com.ire.combat.actions.attackactions.AttackAction;
import com.ire.entities.Entity;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;

import static com.diogonunes.jcolor.Attribute.TEXT_COLOR;

public abstract class SpellAttack extends AttackAction {

    protected static final int nameLength = 18;

    protected float levelDamage = 0.5f;

    protected final String prefixName;
    protected final String[] postfixNames;

    protected int baseManaCost;
    protected int spellLevel;
    protected String flavorText;

    public SpellAttack(String name, String description, AudioStream SOUND, int DURATION, int DELAY, float coefficient,
                       String[] postfixNames, int baseManaCost, int spellLevel, String flavorText) {
        super(name, description, SOUND, DURATION, DELAY, coefficient);

        this.prefixName = name;
        this.postfixNames = postfixNames;
        this.baseManaCost = baseManaCost;
        this.spellLevel = spellLevel;
        this.flavorText = flavorText;

        this.updateName();
    }

    @Override
    public void execute(Entity attacker, Entity defender) {

        calculateDamage(attacker, defender);

        narrateEvents(attacker, defender);

        //  Should this use Entity's mana methods instead?
        attacker.incrementMan(-baseManaCost);
        defender.takeDamage(damage, true);
    }

    @Override
    protected void calculateDamage(Entity attacker, Entity defender) {

        damage = Math.round(attacker.getCurMag() * coefficient * ((spellLevel - 1) * levelDamage + 1));
        defender.getCurrentAction().execute(attacker, defender);
    }

    protected void narrateEvents(Entity attacker, Entity defender) {

        Formatter parser = new Formatter();
        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }
    }

    public int menu(ArrayList<SpellAttack> spells, int mana, int curMag, boolean input) {

        ArrayList<String> options = new ArrayList<>();
        ArrayList<Integer> exclusions = new ArrayList<>();
        int choice;

        for (int i = 0; i < spells.size(); i++) {

            SpellAttack s = spells.get(i);
            StringBuilder output = new StringBuilder();

            if (input) {
                output.append(s.getName());

                for (int j = 0; j < (nameLength - s.getName().length()); j++) {
                    output.append(" ");
                }
                //    Add clause for spells that cost health
                output.append(s.getBaseManaCost()).append(" mana").append("    ").append(s.getDescription());
            }

            options.add(output.toString());
            if (mana < s.getBaseManaCost()) {
                exclusions.add(i + 1);
            }
        }

        if (input) {
            Attribute[] colors = new Attribute[] {TEXT_COLOR(0, 100, 255), TEXT_COLOR(150, 50, 255)};
            System.out.println("Select a spell");
            System.out.println("Mana  " + Tools.createColoredBar(mana, curMag, 20, colors)
                    + " " + mana + "/" + curMag);
            choice = Tools.cancelableMenu(options, exclusions);
        } else {
            Random rand = new Random();
            choice = rand.nextInt(options.size());
        }

        return choice;
    }

    protected void updateName() {
        this.name = (this.prefixName + " " + this.postfixNames[this.spellLevel - 1]);
    }

    public int getSpellLevel() {
        return spellLevel;
    }

    public void setSpellLevel(int spellLevel) {

        if (spellLevel > 0 && spellLevel <= this.postfixNames.length) {
            this.spellLevel = spellLevel;
            this.updateName();

        } else {
            throw new IllegalArgumentException("Unexpected value: " + spellLevel);
        }
    }

    public int getBaseManaCost() {
        return baseManaCost;
    }

    public void setBaseManaCost(int baseManaCost) {
        this.baseManaCost = baseManaCost;
    }
}
