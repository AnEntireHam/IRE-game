package ire.combat.statuseffects;

public abstract class GenerativeEffect extends StatusEffect {


    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage) {
        super(name, abbreviation, description, display, percentage);
    }

    @Override
    protected boolean inflict() {
        return false;
    }
}
