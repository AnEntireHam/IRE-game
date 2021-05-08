package com.ire.arena;

import com.ire.combat.statuseffects.generativeeffect.Bleed;
import com.ire.combat.statuseffects.generativeeffect.Regeneration;
import com.ire.combat.statuseffects.stateffects.AttackUp;
import com.ire.combat.statuseffects.stateffects.MagicUp;
import com.ire.entities.Entity;
import com.ire.tools.UserInput;

public class EntityEditor {

    public static void editEntity(Entity entity) {
        while (true) {
            System.out.println("WIP.");
            // PrintControl.sleep(1000);
            System.out.println("Select an effect to apply to this entity");
            String[] options = {"Damage", "Heal", "Man Heal", "Atk Up", "Mag Up", "Apply Bleed", "Apply Regen"};
            AttackUp au = new AttackUp(1);
            MagicUp mu = new MagicUp(1);
            Bleed bleed = new Bleed(1);
            bleed.setStrength(2);
            Regeneration regen = new Regeneration(1);
            regen.setStrength(2);

            switch (UserInput.cancelableMenu(options)) {
                case 0:
                    return;
                case 1:
                    entity.takeDamage(3, true);
                    break;
                case 2:
                    entity.regenerateHealth(3, false, true);
                    break;
                case 3:
                    entity.regenerateMana(3, false, true);
                    break;
                case 4:
                    au.apply(entity, entity);
                    break;
                case 5:
                    mu.apply(entity, entity);
                    break;
                case 6:
                    bleed.apply(entity, entity);
                    break;
                case 7:
                    regen.apply(entity, entity);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value in editEntity");
            }
        }
    }

    private static void editAttacks(Entity entity) {

    }

    private static void editDefenses(Entity entity) {

    }

    private static void editStatusEffects(Entity entity) {

    }

    private static void editFields(Entity entity) {
        
    }
}
