package IRE.Combat.Actions.AttackActions.SpellAttacks;

import IRE.Audio.AudioStream;
import IRE.Combat.Actions.AttackActions.AttackAction;
import IRE.Tools.Tools;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpellAttack extends AttackAction {

    protected final String prefixName;
    protected final String[] postfixNames;

    protected int spellLevel;
    protected int baseManaCost;

    public SpellAttack(String name, String description, AudioStream SOUND, int DURATION, int DELAY, float coefficient,
                       String[] postfixNames, int baseManaCost, int spellLevel) {
        super(name, description, SOUND, DURATION, DELAY, coefficient);

        this.prefixName = name;
        this.postfixNames = postfixNames;
        this.baseManaCost = baseManaCost;
        this.spellLevel = spellLevel;

        this.updateName();
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

    private void updateName() {
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
