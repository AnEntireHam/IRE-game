package ire.combat.statuseffects.stateffects;

public class AttackDown extends StatEffect {

    public AttackDown(int strength) {
        super("Attack Down", "ATK", "Lowers the attack stat of the afflicted target.",
                true, true, 0, 5, strength);
    }

    //  Not sure if stacks and duration should be in constructor in the first place.
    //  Consider moving class to "typicalStatEffect" package?

    //  Calculates to see if a status effect or stack should be added.
    //  When applying a status effect, first loop through the target's status effects. If there's a SE with a matching
    //  abbreviation and display/percentage, consider that in the final display. If there's a SE with a matching name,
    //  if application is successful, only add stronger SE.
    @Override
    protected boolean apply() {
        return false;
    }

    //  Reduces duration by 1 and executes effects if either are applicable.
    @Override
    protected void incrementEffect() {
        System.out.println("Method under construction");
    }

    //  Removes the status effect from its host. Probably also does math to figure out if it should.
    @Override
    protected void remove() {
        System.out.println("Method under construction");
    }

    @Override
    public void setStrength(int strength) {

        if (strength < 1) {
            System.err.println("Strength must be greater than 0");
        }  else if (strength > 3) {
            System.err.print("Strength of " + this.name + " shouldn't exceed 3");
        } else {
            this.strength = strength;
        }
    }

}
