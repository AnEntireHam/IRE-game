package ire.combat.statuseffects;

public class StatEffect extends StatusEffect {

    public StatEffect(String name, String abbreviation, String description, boolean display, boolean percentage,
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
