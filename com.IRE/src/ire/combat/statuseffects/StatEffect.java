package ire.combat.statuseffects;

public class StatEffect extends StatusEffect {


    public StatEffect(String name, String abbreviation, String description, boolean display, boolean percentage) {
        super(name, abbreviation, description, display, percentage);
    }

    @Override
    protected boolean inflict() {
        return false;
    }
}
