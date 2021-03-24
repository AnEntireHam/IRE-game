package com.ire.combat.actions.defenseactions.spelldefenses;

import com.ire.combat.actions.defenseactions.DefenseAction;
import com.ire.tools.Tools;

import java.util.ArrayList;
import java.util.Random;

public abstract class SpellDefense extends DefenseAction {

    public SpellDefense(String NAME, String DESCRIPTION, float physBoost, float physResist,
                        float spellBoost, float spellResist) {
        super(NAME, DESCRIPTION, physBoost, physResist, spellBoost, spellResist);

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
            // TODO: Consider replacing with Math.random. Figure out if Math.random is generally better.
            Random rand = new Random();
            choice = rand.nextInt(options.size() + 1);
        }

        return choice;
    }

}
