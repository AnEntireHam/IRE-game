package com.ire.combat.actions.attackactions.spellattacks;

import com.diogonunes.jcolor.Attribute;
import com.ire.audio.AudioClip;
import com.ire.combat.actions.attackactions.AttackAction;
import com.ire.combat.actions.defenseactions.spelldefenses.Mirror;
import com.ire.entities.Entity;
import com.ire.tools.Tools;

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

    public SpellAttack(String name, String description, AudioClip SOUND, int DURATION, int DELAY, float coefficient,
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

        attacker.incrementMan(-baseManaCost);

        if (defender.getCurAction() instanceof Mirror) {
            ((Mirror) defender.getCurAction()).reflect(attacker, defender);
            attacker.takeDamage(damage, true);
            return;
        }
        defender.takeDamage(damage, true);
    }

    @Override
    protected void calculateDamage(Entity attacker, Entity defender) {

        damage = Math.round(attacker.getCurMag() * coefficient * ((spellLevel - 1) * levelDamage + 1));
        defender.getCurAction().execute(attacker, defender);
    }

    protected void narrateEvents(Entity attacker, Entity defender) {

        Formatter parser = new Formatter();
        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));

        Tools.sleep(DELAY);
        this.SOUND.play();

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurAction().getName());
            Tools.sleep(DURATION - DELAY);
        }
    }

    // TODO: Split into separate methods. Perhaps it should use Tools' menu().
    public static int menu(ArrayList<SpellAttack> spells, int mana, int curMag, boolean input) {

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
            System.out.println("Select a spell");
            String bar = "";

            // TODO: Entity.getUseColor() is a bodge. Replace this.
            if (Entity.getUseColor()) {
                Attribute[] colors = new Attribute[]{TEXT_COLOR(0, 100, 255), TEXT_COLOR(150, 50, 255)};
                bar = Tools.createColoredBar(mana, curMag, 20, colors);

            } else {
                bar = Tools.createBar(mana, curMag, 20);
            }

            System.out.println("Mana  " + bar + " " + mana + "/" + curMag);
            return Tools.cancelableMenu(options, exclusions);
        }

        Random rand = new Random();
        return choice = rand.nextInt(options.size() + 1);
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

    @Override
    public String toString() {
        return "SpellAttack{" +
                "baseManaCost=" + baseManaCost +
                ", spellLevel=" + spellLevel +
                "} " + super.toString();
    }
}
