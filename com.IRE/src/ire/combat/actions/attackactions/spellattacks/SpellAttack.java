package ire.combat.actions.attackactions.spellattacks;

import ire.audio.AudioStream;
import ire.combat.actions.attackactions.AttackAction;
import ire.entities.Entity;
import ire.tools.Tools;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;

public abstract class SpellAttack extends AttackAction {

    protected float levelDamage = 0.5f;

    protected final String prefixName;
    protected final String[] postfixNames;

    protected int baseManaCost;
    protected int spellLevel;
    protected String flavorText;

    protected Formatter parser = new Formatter();

    public SpellAttack(String name, String description, AudioStream SOUND, int DURATION, int DELAY, float coefficient,
                       String[] postfixNames, int baseManaCost, int spellLevel) {
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

        damage = Tools.round(attacker.getCurMag() * coefficient);
        damage = Tools.round(damage * ((spellLevel - 1) * levelDamage + 1));

        defender.getCurrentAction().execute(attacker, defender);

        System.out.println(parser.format(flavorText, attacker.getName(), defender.getName()));
        Tools.sleep(DELAY);
        this.SOUND.play();

        attacker.incrementMan(-baseManaCost);

        if (defender.isAlive()) {
            System.out.println(defender.getName() + " used " + defender.getCurrentAction().getName());
            Tools.sleep(DURATION - DELAY);
        }

        defender.bEffects.takeDamage(damage, true);
    }

    public static int menu(ArrayList<SpellAttack> spells, boolean input) {

        // Include mana cost n stuff in the future, make method more sophisticated

        ArrayList<String> options = new ArrayList<>();
        int choice;

        for (SpellAttack s: spells) {
            options.add(s.getName());
        }

        if (input) {
            System.out.println("Select a spell");
            choice = Tools.cancelableMenu(options);
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
