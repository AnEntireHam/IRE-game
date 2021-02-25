package ire.combat.statuseffects;

public abstract class GenerativeEffect extends StatusEffect {

    public GenerativeEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
                            int stacks, int duration, int removalType, int resilience) {
        super(name, abbreviation, description, display, percentage, stacks, duration, removalType, resilience);
    }

    @Override
    protected boolean inflict() {
        return false;
    }

    @Override
    protected void incrementEffect() {
        System.out.println("Method under construction");
    }
}
