package ire.combat.actions.defenseactions.spelldefenses;

import ire.combat.actions.defenseactions.DefenseAction;
import ire.tools.Tools;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpellDefense extends DefenseAction {

    protected float spellBoost;
    protected float spellResist;

    public SpellDefense(String NAME, String DESCRIPTION, float spellBoost, float spellResist) {
        super(NAME, DESCRIPTION);

        this.spellBoost = spellBoost;
        this.spellResist = spellResist;
    }

    public static int menu(ArrayList<SpellDefense> wards, boolean input) {

        ArrayList<String> options = new ArrayList<>();
        int choice;

        for (SpellDefense w: wards) {
            options.add(w.getName());
        }

        if (input) {
            System.out.println("Select a ward");
            choice = Tools.cancelableMenu(options);
        } else {
            Random rand = new Random();
            choice = rand.nextInt(options.size());
        }



        return choice;
    }

    public float getSpellBoost() {
        return spellBoost;
    }

    public void setSpellBoost(float spellBoost) {
        this.spellBoost = spellBoost;
    }

    public float getSpellResist() {
        return spellResist;
    }

    public void setSpellResist(float spellResist) {
        this.spellResist = spellResist;
    }
}
