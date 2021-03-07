package ire.combat.statuseffects.stateffects;

public class Surprise extends StatEffect {

    //  Fix "x had this Surprise! increased text". Probably add Formatter or @Override or boolean doDisplay.
    public Surprise() {
        super("Surprise!", "SPD", "Increases the maximum health of the afflicted target.",
                true, true, 1, 1, 1, 1.0f, 0.0f,
                2.0f, 0.00f);
    }

    @Override
    protected void displayResult(String defender, String statName, boolean debuff, boolean success) {

        /*if (success) {
            System.out.println(defender + " surprised the enemy!");
        } else {
            System.out.println(defender + " couldn't surprise the enemy.");
        }*/
        //  Probably just print "xTeam could(n't) surprise the enemy!" in battle.
    }
}
