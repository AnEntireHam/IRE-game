package ire.combat.statuseffects.stateffects;

public class Surprise extends StatEffect {

    //  Fix "x had this Surprise! increased text". Probably add Formatter or @Override or boolean doDisplay.
    public Surprise() {
        super("Surprise!", "SPD", "Increases the maximum health of the afflicted target.",
                true, true, 1, 1, 1, 1.0f, 0.0f,
                2.0f, 0.00f);
    }
}
